package ru.otus.lo17;

import ru.otus.lo17.atm.ATM;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DepartmentAtm implements ATM {

    private final Deque<Memento> stack = new ArrayDeque<>();

    private final List<ATM> atmList = new ArrayList<>();

    public List<ATM> getAtmList() {
        return atmList;
    }

    public void addAtm(ATM atm) {
        atmList.add(atm);
    }

    public void removeAtm(ATM atm) {
        atmList.remove(atm);
    }

    @Override
    public int getBalance() {
        return atmList.stream().mapToInt(ATM::getBalance).sum();
    }

    void saveState(State state) throws Exception {
        stack.push(new Memento(state));
    }

    State restoreState() {
        return stack.pop().getState();
    }
}