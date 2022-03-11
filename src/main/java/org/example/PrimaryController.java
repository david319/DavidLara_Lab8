package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

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
    private Label labelNameT,largeTrack;

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

    public void useTrackAction(){
        String name = nameTrack.getText();
        int distance = Integer.parseInt(distanceTrack.getText());
        labelNameT.setText(name);
        largeTrack.setText(String.valueOf(distance));
        distanceT = distance;
    }

    public void chooserTypeCar(){
        ObservableList<String> list = typeCar.getItems();
        list.add("McQueen");
        list.add("Convertible");
        list.add("Nascar");

        typeCar.setItems(list);
    }

    public void saveRunnerAction(){
        int id = Integer.parseInt(idRunner.getText());
        int distance = 0;
        String name = nameRunner.getText();
        String type = typeCar.getValue();
        Color color = colorRunner.getValue();
        addRunner(id, distance, name, color , type);
    }

    public void chooseRunnerAction(){
        ObservableList<Integer> list = addRunner.getItems();
        for (Runner runner : runners) {
            list.add(runner.getId());
        }
        addRunner.setItems(list);
    }


    public void addRunnerAction(){
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
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
        }
    }


}

