package ru.otus;

public class Demo {


    public static void main(String[] args) throws InterruptedException {

        Monitor monitor = new Monitor();

        MyThread t1 = new MyThread("t1", monitor);
        MyThread t2 = new MyThread("t2", monitor);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}
