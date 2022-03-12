package org.example;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.util.UUID;

import static org.example.Files.*;

public class PrimaryController {

    @FXML
    private TableColumn<org.example.Runner, Integer> Id;

    @FXML
    private TableColumn<org.example.Runner, String> Runner;

    @FXML
    private ComboBox<Integer> addRunner;

    @FXML
    private Button btnAddRunner, btnPause, btnRestart, btnSaveRunner, btnStart, btnUseTrack;

    @FXML
    private ColorPicker colorRunner;

    @FXML
    private TableColumn<org.example.Runner, Integer> distance;

    @FXML
    private TextField distanceTrack, idRunner;

    @FXML
    private TableView<org.example.Runner> infoRun;

    @FXML
    private Label labelNameT, largeTrack;

    @FXML
    private TextField nameRunner, nameTrack;

    @FXML
    private ProgressBar runProgress;

    @FXML
    private ComboBox<String> typeCar;

    int distanceT;

    public void initialize() {
        loadRunners();
        chooserTypeCar();
        chooseRunnerAction();
    }

    public void useTrackAction() {
        String name = nameTrack.getText();
        int distance = Integer.parseInt(distanceTrack.getText());
        labelNameT.setText(name);
        largeTrack.setText(String.valueOf(distance));
        distanceT = distance;
    }

    public void chooserTypeCar() {
        ObservableList<String> list = typeCar.getItems();
        list.add("McQueen");
        list.add("Convertible");
        list.add("Nascar");

        typeCar.setItems(list);
    }

    private static String toHexString(Color color) {
        int r = ((int) Math.round(color.getRed() * 255)) << 24;
        int g = ((int) Math.round(color.getGreen() * 255)) << 16;
        int b = ((int) Math.round(color.getBlue() * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));
        return String.format("#%08X", (r + g + b + a));
    }

    public void saveRunnerAction() {
        int id = Integer.parseInt(idRunner.getText());
        int distance = 0;
        String name = nameRunner.getText();
        String type = typeCar.getValue();
        String color = toHexString(colorRunner.getValue());
        addRunner(id, distance, name, color, type);
        chooseRunnerAction();
    }

    public void chooseRunnerAction() {
        ObservableList<Integer> list = addRunner.getItems();
        for (Runner runner : runners) {
            list.add(runner.getId());
        }
        addRunner.setItems(list);
    }


    public void addRunnerAction() {
        try {
            int id = addRunner.getValue();

            for (Runner runner : runners) {
                if (runner.getId() == id) {
                    ObservableList<Runner> list = infoRun.getItems();
                    list.add(runner);
                    infoRun.setItems(list);

                    Id.setCellValueFactory(new PropertyValueFactory<>("id"));
                    Runner.setCellValueFactory(new PropertyValueFactory<>("nameR"));
                    distance.setCellValueFactory(new PropertyValueFactory<>("distance"));
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
        }
    }

    // Cambiar la posicion de los runners en la tabla por la distancia recorrida
    public void changePosition(int id, int distance) {
        for (Runner runner : runners) {
            if (runner.getId() == id && runner.getDistance() < distance) {
                runner.setDistance(distance);
                ObservableList<Runner> list = infoRun.getItems();
                list.remove(runner);
                list.add(runner);
                infoRun.setItems(list);
            } else if (runner.getId() == id && runner.getDistance() > distance) {
                runner.setDistance(distance);
                ObservableList<Runner> list = infoRun.getItems();
                list.remove(runner);
                list.add(runner);
                infoRun.setItems(list);
                break;
            }
        }
    }

    Task tarea = new Task() {
        @Override
        protected Object call() throws Exception {
            do {
                for (Runner runner : runners) {
                    switch (runner.getCar()) {
                        case "McQueen":
                            runner.setDistance(runner.getDistance() + (int) (Math.random() * (180 - 30)));
                            break;
                        case "Convertible":
                            runner.setDistance(runner.getDistance() + (int) (Math.random() * (200 - 20)));
                            break;
                        case "Nascar":
                            runner.setDistance(runner.getDistance() + (int) (Math.random() * (180 - 40)));
                            break;
                    }
                    double progress = (runner.getDistance() * 100.0 / distanceT);
                    progress = progress / 100;
                    updateProgress(progress, 1);
                    System.out.println(progress);
                    for (Runner runner1 : runners) {
                        runProgress.setStyle("-fx-accent: " + runner1.getColor() + ";");
                    }

                    if (progress >= 1) {
                        updateMessage("Finished");
                        updateProgress(1, 1);
                        updateMessage("The winner is: " + runner.getNameR());
                        tarea.cancel();
                    }
                    infoRun.refresh();
                    Thread.sleep(1000);


                    changePosition(runner.getId(), runner.getDistance());
                    infoRun.refresh();
                }

                if (isCancelled()) {
                    break;
                }
            } while (runProgress.getProgress() < 1);
            return null;
        }

        public void updateProgress(double workDone, double max) {
            Platform.runLater(() -> {
                runProgress.setProgress(workDone / max);
            });
        }

        public void stopRun() {
            tarea.cancel();
        }

    };


    public void startRun() {
        new Thread(tarea).start();
    }

    public void pauseRun() {
        if (tarea.isRunning()) {
            tarea.cancel();
        } else {
            tarea.run();
        }
    }

    public void resetRun() {
        tarea.cancel();
        runProgress.setProgress(0);
        for (Runner runner : runners) {
            runner.setDistance(0);
        }
        infoRun.refresh();
    }


}


