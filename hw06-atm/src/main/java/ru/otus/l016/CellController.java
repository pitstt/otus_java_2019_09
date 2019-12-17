package ru.otus.l016;

import java.util.List;

public interface CellController {

    void addCell(Cell cell);

    void addMoney(List<Bills> money) throws Exception ;

    List<Bills> getMoney(Integer sum) throws Exception;

    void printBalance();
}
