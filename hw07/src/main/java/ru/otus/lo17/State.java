package ru.otus.lo17;

import ru.otus.lo17.atm.*;

import java.util.List;


public class State {

    private final DepartmentAtm departmentAtm;

    public State(DepartmentAtm departmentAtm) {
        this.departmentAtm = departmentAtm;
    }

    public State(State state) {
        DepartmentAtm newDepartmentAtm = new DepartmentAtm();
        for (ATM atm : state.getCompositeAtm().getAtmList()) {
            CellController cellController = new CellControllerImpl();
            ((ATMImpl) atm).getCellController().getCells().forEach(oldCell -> {
                Cell cell = new CellImpl(oldCell.getBillsType());
                cell.addBills(List.copyOf(oldCell.getBills()));
                cellController.addCell(cell);
            });
            newDepartmentAtm.addAtm(new ATMImpl(cellController));
        }
        this.departmentAtm = newDepartmentAtm;
    }

    public DepartmentAtm getCompositeAtm() {
        return departmentAtm;
    }
}
