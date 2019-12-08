package ru.otus.l016;

import org.junit.Test;

public class ATMTest {

    @Test
    public void test() throws Exception {
        ATM atm = new ATM();
        atm.addMoney(100,1000,100, 50, 100);
        atm.getMoney(1150);
        atm.printBalance();
    }

}