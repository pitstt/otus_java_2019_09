package ru.otus.l018;

import java.util.List;

public class TestClass {

    public int integer;

    public String string;

    public Double aDouble;

    public Boolean aBoolean;

    public int[] ints;

    public List<String> stringsList;

    public TestClass(Integer integer, String string, Double aDouble, Boolean aBoolean, int[] ints, List<String> stringsList) {
        this.integer = integer;
        this.string = string;
        this.aDouble = aDouble;
        this.aBoolean = aBoolean;
        this.ints = ints;
        this.stringsList = stringsList;
    }
}
