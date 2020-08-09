package ru.otus.lo17.atm;

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
