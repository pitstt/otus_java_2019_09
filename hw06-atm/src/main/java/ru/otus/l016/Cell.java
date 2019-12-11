package ru.otus.l016;

import java.util.*;
import java.util.stream.Collectors;

public class Cell {

    //key:nominal cost ; value: count
    private final Map<Bills, Integer> balance = new HashMap<>();

    public Cell() {
    }

    public void addMoney(Bills... money) {
        for (Bills m : money) {
            balance.put(m, balance.getOrDefault(m, 0) + 1);
        }
    }

    public void getMoney(Integer sum) throws Exception {
        if (sum > getBalance()) {
            throw new Exception("В банкомате недостаточно денег!");
        }
        List<Integer> moneyList = new ArrayList<>();
        while (sum != 0) {
            Bills nominal = getMaximumNominal(sum);
            if (nominal != null) {
                sum = sum - nominal.getValue();
                moneyList.add(nominal.getValue());
            } else {
                throw new Exception("В банкомате нет подходящих банкнот!");
            }
        }
        System.out.println("Выданы банкноты: " + moneyList.toString());
    }

    public void printBalance() {
        System.out.println("Balance: " + getBalance());
    }

    private Bills getMaximumNominal(Integer sum) {
        for (Bills nominal : balance.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())) {
            if (sum >= nominal.getValue()) {
                int newCount = balance.getOrDefault(nominal, 0) - 1;
                if (newCount == 0) {
                    balance.remove(nominal);
                } else {
                    balance.put(nominal, newCount);
                }
                return nominal;
            }
        }
        return null;
    }

    private Integer getBalance() {
        return balance.entrySet().parallelStream()
                .mapToInt(e -> (e.getKey().getValue() * e.getValue())).sum();
    }

}
