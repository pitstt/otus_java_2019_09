package ru.otus.lO14;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = IoC.createMyClass();
        testLogging.summator(20);
    }
}
