package org.example;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class Runner{

    // Atributos
    private int id;
    private int distance;
    private String nameR;
    private String color;
    private String car;

    // Constructor
    public Runner(int id, int distance, String nameR, String color, String car) {
        this.id = id;
        this.distance = distance;
        this.nameR = nameR;
        this.color = color;
        this.car = car;
    }

    // Métodos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getNameR() {
        return nameR;
    }

    public void setNameR(String nameR) {
        this.nameR = nameR;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public void move(int distance) {
        this.distance += distance;
    }

    public void reset() {
        distance = 0;
    }


}
