package ru.otus.l016;

import java.util.*;
import java.util.stream.Collectors;

public class ATM {

    //key:nominal cost ; value: count
    private final Map<Integer, Integer> balance = new HashMap<>();

    public void addMoney(Integer... money) {
        for (Integer m : money) {
            balance.put(m, balance.getOrDefault(m, 0) + 1);
        }
    }

    public void getMoney(Integer sum) throws Exception {
        if (sum > getBalance()) {
            throw new Exception("В банкомате недостаточно денег!");
        }
        List<Integer> moneyList = new ArrayList<>();
        while (sum != 0) {
            Integer nominal = getMaximumNominal(sum);
            if (nominal != null) {
                sum = sum - nominal;
                moneyList.add(nominal);
            } else {
                throw new Exception("В банкомате нет подходящих банкнот!");
            }
        }
        System.out.println("Выданы банкноты: " + moneyList.toString());
    }

    public void printBalance() {
        System.out.println("Balance: " + getBalance());
    }

    private Integer getMaximumNominal(Integer sum) {
        for (Integer nominal : balance.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())) {
            if (sum >= nominal) {
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
                .mapToInt(e -> (e.getKey() * e.getValue())).sum();
    }


}
