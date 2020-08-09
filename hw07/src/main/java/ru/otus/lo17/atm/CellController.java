package ru.otus.lo17.atm;

import java.util.List;

public interface CellController {

    void addCell(Cell cell);

    List<Cell> getCells();

    void addMoney(List<Bills> money) throws Exception ;

    List<Bills> getMoney(Integer sum) throws Exception;

    int getBalance();

    void printBalance();
}
