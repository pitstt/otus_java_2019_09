package ru.otus.lo17.atm;

public class ATMImpl implements ATM {
    private final CellController cellController;

    public ATMImpl(CellController cellController) {
        this.cellController = cellController;
    }

    public CellController getCellController() {
        return cellController;
    }

    @Override
    public int getBalance() {
        return cellController.getBalance();
    }
}
