package ru.otus.lo17.atm;

import java.util.List;

public interface Cell {

    Bills getBillsType();

    void addBill(Bills bill);

    void addBills(List<Bills> bills);

    List<Bills> getBills();

    void removeBill(Bills bill);

}
