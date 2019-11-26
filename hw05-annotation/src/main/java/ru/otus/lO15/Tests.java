package ru.otus.lO15;

import ru.otus.lO15.annotation.After;
import ru.otus.lO15.annotation.Before;
import ru.otus.lO15.annotation.Test;

public class Tests {

    @Before
    public void before(){
        System.out.println("before");
    }

    @Test
    public void testOne() {
        System.out.println("testOne");
    }

    @Test
    public void testTwo() {
        System.out.println("testTwo");
    }

    @Test
    public void testError() throws Exception {
        throw new Exception("Ошибка!!");
    }

    @Test
    public void testError2() {
        Integer i = null;
        int b = 1;
        int c = i + b;
    }

    @After
    public void after() {
        System.out.println("after");
    }

}
