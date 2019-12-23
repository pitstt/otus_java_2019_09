package ru.otus.l016;

import java.util.List;

public interface Cell {

    Bills getBillsType();

    void addBill(Bills bill);

    List<Bills> getBills();

    void removeBill(Bills bill);

}
