package ru.otus.l016;

import org.junit.Test;

import java.util.Arrays;

public class ATMTest {

    @Test
    public void test() throws Exception {
        Cell cellFifty = new CellImpl(Bills.FIFTY);
        Cell cellHundred = new CellImpl(Bills.HUNDRED);
        Cell cellThousand = new CellImpl(Bills.THOUSAND);

        CellController cellController = new CellControllerImpl();
        cellController.addCell(cellFifty);
        cellController.addCell(cellHundred);
        cellController.addCell(cellThousand);

        ATM atm = new ATM(cellController);
        CellController atmCellController = atm.getCellController();
        atmCellController.addMoney(Arrays.asList(Bills.HUNDRED, Bills.THOUSAND, Bills.HUNDRED, Bills.FIFTY, Bills.HUNDRED));
        atmCellController.getMoney(1150);
        atmCellController.printBalance();
    }

}