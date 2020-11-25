package com.creativeitem.academy.Models;

public class Rating {
    private int id;
    private int value;
    private String displayedValue;

    public Rating(int id, int value, String displayedValue) {
        this.id = id;
        this.value = value;
        this.displayedValue = displayedValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDisplayedValue() {
        return displayedValue;
    }

    public void setDisplayedValue(String displayedValue) {
        this.displayedValue = displayedValue;
    }

    @Override
    public String toString() {
        return getDisplayedValue();
    }
}
