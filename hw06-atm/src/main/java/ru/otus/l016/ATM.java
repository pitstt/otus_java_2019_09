package ru.otus.l016;


public class ATM {

    private CellController cellController;

    public ATM(CellController cellController) {
        this.cellController = cellController;
    }

    public CellController getCellController() {
        return cellController;
    }
}
