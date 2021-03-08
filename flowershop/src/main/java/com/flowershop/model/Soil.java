package com.flowershop.model;

public enum  Soil {
    PODZOLIC("Подзолистая"),
    SODPODZOLIC("Дерново-подзолистая"),
    UNPAVED("Грунтовая");

    String value;

    Soil(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
