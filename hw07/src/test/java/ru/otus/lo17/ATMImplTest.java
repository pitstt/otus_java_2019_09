package ru.otus.lo17;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.lo17.atm.*;

import java.util.Arrays;

public class ATMImplTest {

    @Test
    public void test() throws Exception {

        Cell cellFifty1 = new CellImpl(Bills.FIFTY);
        Cell cellHundred1 = new CellImpl(Bills.HUNDRED);
        Cell cellThousand1 = new CellImpl(Bills.THOUSAND);

        CellController cellController1 = new CellControllerImpl();
        cellController1.addCell(cellFifty1);
        cellController1.addCell(cellHundred1);
        cellController1.addCell(cellThousand1);

        ATMImpl atm1 = new ATMImpl(cellController1);
        cellController1.addMoney(Arrays.asList(Bills.HUNDRED, Bills.THOUSAND, Bills.HUNDRED, Bills.FIFTY, Bills.HUNDRED));

        CellController cellController2 = new CellControllerImpl();
        ATMImpl atm2 = new ATMImpl(cellController2);
        Cell cellFifty2 = new CellImpl(Bills.FIFTY);
        Cell cellHundred2 = new CellImpl(Bills.HUNDRED);
        Cell cellThousand2 = new CellImpl(Bills.THOUSAND);
        cellController2.addCell(cellFifty2);
        cellController2.addCell(cellHundred2);
        cellController2.addCell(cellThousand2);
        cellController2.addMoney(Arrays.asList(Bills.HUNDRED, Bills.THOUSAND, Bills.HUNDRED, Bills.FIFTY, Bills.HUNDRED));

        DepartmentAtm departmentAtm = new DepartmentAtm();
        departmentAtm.addAtm(atm1);
        departmentAtm.addAtm(atm2);

        Assert.assertEquals(1350, atm1.getBalance());
        Assert.assertEquals(1350, atm2.getBalance());
        Assert.assertEquals(2700, departmentAtm.getBalance());

        State state = new State(departmentAtm);
        departmentAtm.saveState(state);

        cellController1.getMoney(1150);
        cellController1.printBalance();

        Assert.assertEquals(1550, state.getCompositeAtm().getBalance());

        //возвращаю к исходному состоянию
        state = departmentAtm.restoreState();
        Assert.assertEquals(2700, state.getCompositeAtm().getBalance());

    }

}