package org.example;

import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Files {

    static ArrayList<Runner> runners = new ArrayList<>();

    public static boolean verifyId(int id) {
        for (Runner runner : runners) {
            if (runner.getId() == id) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Runner with this id already exists");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    public static void addRunner(int id, int distance, String nameR, String color, String car) {
        if (verifyId(id)) {
            runners.add(new Runner(id, distance, nameR, color, car));
            saveRunners();
        }
    }

    public static void saveRunners() {
        try {
            RandomAccessFile file = new RandomAccessFile("runners.txt", "rw");
            for (Runner runner : runners) {
                file.writeInt(runner.getId());
                file.writeInt(runner.getDistance());
                file.writeUTF(runner.getNameR());
                file.writeUTF(runner.getColor());
                file.writeUTF(runner.getCar());
            }
            file.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public static void loadRunners() {
        try {
            RandomAccessFile file = new RandomAccessFile("runners.txt", "r");
            if (file.length() > 0) {
                while (true) {
                    int id = file.readInt();
                    int distance = file.readInt();
                    String nameR = file.readUTF();
                    String color = file.readUTF();
                    String car = file.readUTF();
                    if (id == -1) {
                        break;
                    }
                    addRunner(id, distance, nameR, color, car);
                }
                file.close();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
        }
    }

}
