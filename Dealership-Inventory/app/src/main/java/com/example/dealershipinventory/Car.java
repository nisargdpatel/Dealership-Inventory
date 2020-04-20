package com.example.dealershipinventory;

//Holds all the data for a single Note
public class Car {
    private String price;
    private String condition;
    private String year;
    private String make;
    private String color;
    private String model;
    private String mileage;

    public Car(){}

    public Car(String price, String condition, String year, String make, String color, String model, String mileage) {
        this.price = price;
        this.condition = condition;
        this.year = year;
        this.make = make;
        this.color = color;
        this.model = model;
        this.mileage = mileage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setNew(String condition) {
        this.condition = condition;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
}
