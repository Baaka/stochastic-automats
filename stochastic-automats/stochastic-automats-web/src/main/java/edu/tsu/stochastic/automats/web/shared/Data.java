package edu.tsu.stochastic.automats.web.shared;

public class Data {
    private String name;
    private double data;

    public Data() {
    }

    public Data(String name, double data) {
        super();
        this.name = name;
        this.data = data;
    }

    public double getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public void setData(double data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }
}
