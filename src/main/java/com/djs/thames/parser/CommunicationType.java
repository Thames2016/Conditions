package com.djs.thames.parser;

public enum CommunicationType {
    ConditionChange(1);

    private int value;

    CommunicationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
