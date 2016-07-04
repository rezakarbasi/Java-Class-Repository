/**
 * <h>Find the best way program</h>
 * The HelloWorld program implements an application that simply displays "Hello
 * World!" to the standard output.
 *
 * @author Reza karbasi
 * @version 1.0
 * @since 2016-03-31
 */
package finalproj;

import com.sun.scenario.effect.impl.Renderer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Phaser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Reza Karbasi
 */
public class FinalProj extends Application {

    private int phase, nd;
    private static String[] Args;
    private Way bestway;
    private boolean bestWayFulled = false;

    /**
     * This method is called when FX program is going to start.
     *
     * @param primaryStage indicates the stage that program is going to show.
     * @throws Exception exception of null pointer exception is handled.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        ///////////////////////////////// configure
        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 800);
        primaryStage.setTitle("Final Proj");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        ArrayList<Dot> Input_Dots = new ArrayList<Dot>();
        ArrayList<Way> out_thread = new ArrayList<Way>();
        Timer timer_show = new Timer();
        Timer timer_thread = new Timer();

        Button startbutton = new Button("Enter 2 Start");
        startbutton.setLayoutX(60);
        startbutton.setLayoutY(5);
        Button SaveButton = new Button("Enter 2 Save");
        SaveButton.setLayoutX(150);
        SaveButton.setLayoutY(5);
        root.getChildren().add(new Line(0, 80, 650, 80));
        root.getChildren().add(new Line(0, 90, 650, 90));
        root.getChildren().add(startbutton);

        /**
         * This timer task is to run some threads parallel to other sides of
         * program.
         */
        TimerTask TT_thread = new TimerTask() {
            int h = 0;

            @Override
            public void run() {
                for (int i = 0; i < 6; i++) {
                    out_thread.add(new Way(Input_Dots));
                    try {
                        if (out_thread.size() != 0) {
                            (new FindWay(out_thread.get(out_thread.size() - 1), Input_Dots)).start();
                        }
                    } catch (IndexOutOfBoundsException e) {
                        if (out_thread.size() != 0) {
                            if (out_thread.size() != 0) {
                                out_thread.remove(out_thread.size() - 1);
                            }
                            System.out.println("error refrence was wrong");
                        } else {
                            System.out.println("error refrence was cleared!");
                        }

                    } catch (EnterInputsException ex) {
                    }
                }
            }
        };

        /**
         * This timer task is to show result of attempts finding the best way.
         */
        TimerTask TT_show = new TimerTask() {
            @Override
            public void run() {
                int number = 0;
                for (int i = 0; i < out_thread.size(); i++) {
                    if (out_thread.get(i) == null || !out_thread.get(i).isEnterd()) {
                        number = i;
                        break;
                    } else if (i == (out_thread.size() - 1)) {
                        number = i + 1;
                    }
                }
                Way[] middlevariable = new Way[number];
                for (int i = 0; i < number; i++) {
                    middlevariable[i] = (Way) (out_thread.get(0));
                    out_thread.remove(0);
                }
                if (bestWayFulled) {
                    bestway.Delete(root);
                }
                try {
                    FindBestWays(middlevariable);
                } catch (EnterInputsException ex) {
                }

                if (bestWayFulled) {
                    bestway.Show(root);
                }
            }
        };

        /**
         * This event handler is a mouse event for saving the best way in a
         * file.
         */
        EventHandler<MouseEvent> SaveInFile = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    FileWriter fileWriter = new FileWriter("Out.txt");
                    fileWriter.write(bestway.toString());
                    fileWriter.flush();
                    fileWriter.close();
                    System.out.println("saving");
                } catch (IOException ex) {
                    System.out.println("Error in Saving File !");
                }
            }
        };
        /**
         * This event handler is a mouse event for getting the dots from the
         * user.
         */
        EventHandler<MouseEvent> get_dot = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (phase == 1 && event.getY() > 90) {
                    Circle c = new Circle(event.getX(), event.getY(), 10);
                    root.getChildren().add(c);
                    nd++;
                    Input_Dots.add(new Dot(event.getX(), event.getY()));
                }
            }
        };
        /**
         * This event handler is a mouse event for starting finding best way.
         */
        EventHandler<MouseEvent> SB = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (phase == 1) {
                    if (nd == 0) {

                        String line = "";
                        try {
                            FileReader fileReader
                                    = new FileReader("Data.txt");
                            BufferedReader bufferedReader
                                    = new BufferedReader(fileReader);

                            while ((line = bufferedReader.readLine()) != null) {
                                double x = Double.parseDouble(line.substring(0, line.indexOf(' ')));
                                double y = Double.parseDouble(line.substring(line.indexOf(' ') + 1, line.length()));
                                y = y + 90;
                                Input_Dots.add(new Dot(x, y));

                                root.getChildren().add(new Circle(x, y, 10));
                                nd++;
                            }
                            bufferedReader.close();
                            scene.removeEventHandler(MouseEvent.MOUSE_CLICKED, get_dot);
                            startbutton.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                            root.getChildren().remove(startbutton);
                            phase++;
                        } catch (Exception ex) {
                            System.out.println(
                                    "Unable to open file '"
                                    + "Data.txt" + "'");
                        }
                    } else {
                        scene.removeEventHandler(MouseEvent.MOUSE_CLICKED, get_dot);
                        startbutton.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                        root.getChildren().remove(startbutton);
                        phase++;
                    }
                    SetPrimaryPriority(Input_Dots);
                    timer_thread.scheduleAtFixedRate(TT_thread, 0, 200);
                    timer_show.scheduleAtFixedRate(TT_show, 0, 1000);
                    root.getChildren().add(SaveButton);
                    SaveButton.setOnMouseClicked(SaveInFile);
                }
            }
        };
        phase = 1;
        nd = 0;

        scene.setOnMouseClicked(get_dot);
        startbutton.setOnMouseClicked(SB);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    /**
     * This method compare best way within threads have run.
     *
     * @param in indicates the ways that threads went up to now.
     */
    private void FindBestWays(Way[] in) throws EnterInputsException {
        if (bestWayFulled && in.length != 0) {
            for (int i = 0; i < in.length; i++) {
                for (int j = i; j < in.length; j++) {
                    if (in[i].getDistance() > in[j].getDistance()) {
                        Way a = in[i];
                        in[i] = in[j];
                        in[j] = a;
                    }
                }
            }

            int[][] k = new int[bestway.getQueue().length][bestway.getQueue().length];
            for (int i = 0; i < k.length; i++) {
                for (int j = 0; j < k.length; j++) {
                    k[i][j] = 0;
                }
            }

            double middle = 1.00 * (in[0].getDistance() + in[in.length - 1].getDistance()) / 2;
            for (int i = 0; i < in.length; i++) {
                for (int j = 1; j < in[i].getQueue().length; j++) {
                    int a = in[i].getQueue()[j];
                    int b = in[i].getQueue()[j - 1];
                    k[b][a] += (int) ((middle - in[i].getDistance()) / 2);
                }
                k[in[i].getQueue()[in[i].getQueue().length - 1]][in[i].getQueue()[0]] += (int) ((middle - in[i].getDistance()) / 2);
            }

            for (int i = 0; i < k.length; i++) {
                for (int j = 0; j < k.length; j++) {
                    k[i][j] = (int) ((k[i][j] + k[j][i]) / 3);
                }
            }

            FindWay.Refresh_priority(k);
            if (in[0].getDistance() < bestway.getDistance()) {
                bestway = in[0];
            }
        } else if (in.length != 0) {
            bestway = new Way(in[0].getQueue(), in[0].getPoints());
            bestWayFulled = true;
        }
    }

    /**
     * The main nethod
     *
     * @param args
     * @throws InterruptedException this exception is handles in the functions
     * that called.
     */
    public static void main(String[] args) throws InterruptedException {
        Args = args;
        launch(Args);

    }

    /**
     * This method find priority of paths within the dots that appeared in
     * screen.
     *
     * @param InDot The dots that user showed us.
     */
    public void SetPrimaryPriority(ArrayList<Dot> InDot) {
        int[][] k = new int[InDot.size()][InDot.size()];
        int max = 0;
        for (int i = 0; i < k.length; i++) {
            for (int j = 0; j < k.length; j++) {
                k[i][j] = (int) InDot.get(i).distance(InDot.get(j));
                if (max < k[i][j]) {
                    max = k[i][j];
                }
            }
        }
        max += 50;
        for (int i = 0; i < k.length; i++) {
            for (int j = 0; j < k.length; j++) {
                if (i != j) {
                    k[i][j] = (int) ((max - k[i][j] + 10) / 4.5);
                }
                System.out.print(k[i][j] + "   ");
            }
            System.out.println("");
        }
        FindWay.setPriotity(k);
    }
}
