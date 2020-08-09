package ru.otus.lo17.atm;

import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell{

    private final Bills bill;

    List<Bills> money = new ArrayList<>();

    public CellImpl(Bills bill) {
        this.bill = bill;
    }

    @Override
    public Bills getBillsType() {
        return bill;
    }

    @Override
    public void addBill(Bills bill) {
        money.add(bill);
    }

    public void addBills(List<Bills> bills) {
        money.addAll(bills);
    }

    @Override
    public List<Bills> getBills() {
        return money;
    }

    @Override
    public void removeBill(Bills bill) {
        money.remove(bill);
    }
}
