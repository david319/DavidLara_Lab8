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
        int r = ((int) Math.round(color.getRed()     * 255)) << 24;
        int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
        int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
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

    public void startAction() {
        Run run = new Run();
        run.start();
    }

    public void pauseAction() {
        Run run = new Run();
        //run.pause();
    }

    public void restartAction() {
        Run run = new Run();
        //run.restartRun();
    }

    public void resetAction() {
        Run run = new Run();
        //run.resetRun();
    }

    class Run extends Thread {

        public void start() {
            try {
                for (Runner runner : runners) {
                    runner.setDistance(runner.getDistance() + 1);
                    if (runner.getDistance() == distanceT) {
                        runner.setDistance(0);
                    } else {
                        runner.setDistance(runner.getDistance() + 1);
                    }
                }
                Platform.runLater(() -> {
                    runProgress.setStyle("-fx-accent: " + runners.get(0).getColor());
                    runProgress.setProgress(runProgress.getProgress() + 0.01);
                    infoRun.refresh();
                });
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }


}

