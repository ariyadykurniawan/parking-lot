package com.gojek.model;

public class Vehicle  {
    private String color;
    private String registrationNumber;

    public Vehicle(){}

    public Vehicle(String registrationNumber, String color) {
        this.color = color;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString(){
        return "[registrationNumber : "+ registrationNumber +"," +
                "color : "+ color +"]";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
