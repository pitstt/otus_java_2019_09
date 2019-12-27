package ru.otus.l018;

import java.util.Arrays;
import java.util.List;

public class TestClass {

    public Integer integer;

    public String string;

    public Double aDouble;

    public Boolean aBoolean;

    public String[] strings;

    public List<String> stringsList;

    public TestClass(Integer integer, String string, Double aDouble, Boolean aBoolean, String[] strings, List<String> stringsList) {
        this.integer = integer;
        this.string = string;
        this.aDouble = aDouble;
        this.aBoolean = aBoolean;
        this.strings = strings;
        this.stringsList = stringsList;
    }

    public Integer getInteger() {
        return integer;
    }

    public String getString() {
        return string;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public String[] getStrings() {
        return strings;
    }

    public List<String> getStringsList() {
        return stringsList;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "integer=" + integer +
                ", string='" + string + '\'' +
                ", aDouble=" + aDouble +
                ", aBoolean=" + aBoolean +
                ", strings=" + Arrays.toString(strings) +
                ", stringsList=" + stringsList +
                '}';
    }
}
