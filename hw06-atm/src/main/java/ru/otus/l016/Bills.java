package ru.otus.l016;

public enum Bills {
    FIFTY(50),
    HUNDRED(100),
    THOUSAND(1000);

    private final int value;

    Bills(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
