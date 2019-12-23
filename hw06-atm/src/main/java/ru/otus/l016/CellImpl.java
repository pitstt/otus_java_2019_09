package ru.otus.l016;

import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell {

    private Bills bill;

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

    @Override
    public List<Bills> getBills() {
        return money;
    }

    @Override
    public void removeBill(Bills bill) {
        money.remove(bill);
    }
}
