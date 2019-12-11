package ru.otus.l016;

import org.junit.Test;

public class ATMTest {

    @Test
    public void test() throws Exception {
        ATM atm = new ATM();
        Cell cell = atm.getCell();
        cell.addMoney(Bills.HUNDRED, Bills.THOUSAND, Bills.HUNDRED, Bills.FIFTY, Bills.HUNDRED);
        cell.getMoney(1150);
        cell.printBalance();
    }

}