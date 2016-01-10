package com.djs.thames.parser;

public enum Channel {
    Email(1),
    Faceboob(2),
    Twitter(3),
    SMS(4);

    private int value;

    Channel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
