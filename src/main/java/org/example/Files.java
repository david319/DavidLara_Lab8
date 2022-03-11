package org.example;

import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Files {

    static ArrayList<Runner> runners = new ArrayList<>();

    public static void addRunner(int id, int distance, String nameR, Color color, String car) {
        runners.add(new Runner(id, distance, nameR, color, car));
        saveRunners();
    }

    public static void saveRunners() {
        try {
            RandomAccessFile file = new RandomAccessFile("runners.txt", "rw");
            for (Runner runner : runners) {
                file.writeInt(runner.getId());
                file.writeInt(runner.getDistance());
                file.writeUTF(runner.getNameR());
                file.writeDouble(runner.getColor().getRed());
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
            while (true) {
                int id = file.readInt();
                int distance = file.readInt();
                String nameR = file.readUTF();
                double color = file.readDouble();
                String car = file.readUTF();
                if (id == -1) {
                    break;
                }
                addRunner(id, distance, nameR, Color.gray(color), car);
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

}
