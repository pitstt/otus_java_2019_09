package ru.otus.l019.api.model;

public class Account {

    @Id
    private long no;

    private final String type;

    private final long rest;

    public Account(long no, String type, long rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public Account(String type, long rest) {
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public long getRest() {
        return rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest='" + rest + '\'' +
                '}';
    }
}
