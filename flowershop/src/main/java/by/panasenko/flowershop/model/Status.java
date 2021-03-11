package by.panasenko.flowershop.model;

public enum Status {
    APPROVED("Одобрено"),
    INPROCESS("В обработке"),
    REJECTED("Отклонен");

    String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
