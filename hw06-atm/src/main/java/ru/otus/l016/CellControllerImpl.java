package ru.otus.l016;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CellControllerImpl implements CellController {

    List<Cell> cells = new ArrayList<>();

    public CellControllerImpl() {
    }

    @Override
    public void addCell(Cell cell) {
        cells.add(cell);
    }

    @Override
    public void addMoney(List<Bills> money) throws Exception {
        for (Bills m : money) {
            if (cells.stream().noneMatch(e -> e.getBillsType().equals(m))) {
                throw new Exception("Данная купюра не обрабатывается");
            }
            cells.forEach(cell -> {
                if (cell.getBillsType().equals(m)) {
                    cell.addBill(m);
                }
            });
        }
    }

    @Override
    public List<Bills> getMoney(Integer sum) throws Exception {
        List<Bills> result = new ArrayList<>();
        if (sum > getBalance()) {
            throw new Exception("В банкомате недостаточно денег!");
        }
        List<Integer> moneyList = new ArrayList<>();
        while (sum != 0) {
            Bills nominal = getMaximumNominal(sum);
            if (nominal != null) {
                sum = sum - nominal.getValue();
                moneyList.add(nominal.getValue());
                cells.stream().filter(e -> e.getBillsType().equals(nominal)).forEach(e -> e.getBills().remove(nominal));
                result.add(nominal);
            } else {
                throw new Exception("В банкомате нет подходящих банкнот!");
            }
        }
        System.out.println("Выданы банкноты: " + moneyList.toString());
        return result;
    }

    @Override
    public void printBalance() {
        System.out.println("Balance: " + getBalance());
    }


    private Integer getBalance() {
        return cells.stream().mapToInt(value -> value.getBillsType().getValue() * value.getBills().size()).sum();
    }

    private Bills getMaximumNominal(Integer sum) {
        for (Bills nominal : cells.stream().filter(cell -> cell.getBills().size() > 0).map(Cell::getBillsType).sorted(Comparator.reverseOrder()).collect(Collectors.toList())) {
            if (sum >= nominal.getValue()) {
                return nominal;
            }
        }
        return null;
    }

}
