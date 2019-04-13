package com.example;

public class Item {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
