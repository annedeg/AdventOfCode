package year_2022.main;

import helpers.CodeDay;
import helpers.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Day11 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToString("src/input/day11");
        var monkeys = new ArrayList<Monkey>();
        for (var monkey : input.split("\n\n")) {
            monkeys.add(new Monkey(monkey));
        }

        for (int round = 0; round < 20; round++) {
            for (var monkey : monkeys) {
                for (var item : monkey.items) {
                    var newItem = monkey.doBored(monkey.doOperation(item));
                    var location = monkey.test(newItem);
                    monkeys.get(location).addItem(newItem);
                    monkey.items.remove(item);
                }
            }
        }

        monkeys.sort((a,b) -> b.totalInspected - a.totalInspected);
        System.out.println(monkeys.get(0).totalInspected * monkeys.get(1).totalInspected);
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToString("src/input/day11");
        var monkeys = new ArrayList<Monkey>();
        var superModule = 1;
        for (var monkey : input.split("\n\n")) {
            var newMonkey = new Monkey(monkey);
            monkeys.add(newMonkey);
            superModule *= newMonkey.test;
        }


        for (int round = 0; round < 10000; round++) {
            for (var monkey : monkeys) {
                for (var item : monkey.items) {
                    var newItem = monkey.doOperation(item);
                    var location = monkey.test(newItem);
                    monkeys.get(location).addItem(newItem.mod(new BigInteger(String.valueOf(superModule))));
                    monkey.items.remove(item);
                }
            }
        }

        monkeys.sort((a,b) -> b.totalInspected - a.totalInspected);
        System.out.println(new BigInteger(String.valueOf(monkeys.get(0).totalInspected)).multiply(new BigInteger(String.valueOf(monkeys.get(1).totalInspected))));
    }
}

class Monkey {
    String operation;
    int test;
    int toTrue;
    int toFalse;
    CopyOnWriteArrayList<BigInteger> items;
    int totalInspected = 0;

    Monkey(String inputPart) {
        var rows = inputPart.split("\n");
        var items = rows[1].split("Starting items: ")[1].split(", ");
        this.items = new CopyOnWriteArrayList<BigInteger>();
        for (var item : items) {
            this.items.add(new BigInteger(item));
        }
        this.operation = rows[2].split("Operation: ")[1];
        this.test = Integer.parseInt(rows[3].split(" by ")[1]);
        this.toTrue = Integer.parseInt(rows[4].split("If true: throw to monkey ")[1]);
        this.toFalse = Integer.parseInt(rows[5].split("If false: throw to monkey ")[1]);
    }

    public String items() {
        var sb = new StringBuilder("year_2022.main.Monkey: ");
        for (var item : this.items) {
            sb.append(item);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "year_2022.main.Monkey inspected items " + this.totalInspected + " times.";
    }

    public void addItem(BigInteger item) {
        this.items.add(item);
    }

    public BigInteger doOperation(BigInteger item) {
        this.totalInspected += 1;
        var splitter = this.operation.split("new = ")[1].split(" ");;

        BigInteger var1;
        BigInteger var2;

        var1 = (splitter[0].contains("old")) ? item : new BigInteger(splitter[0]);
        var2 = (splitter[2].contains("old")) ? item : new BigInteger(splitter[2]);
        BigInteger total = new BigInteger("0");
        if (splitter[1].contains("+"))
            total = var1.add(var2);
        else if (splitter[1].contains("*"))
            total = var1.multiply(var2);

        return total;
    }

    public BigInteger doBored(BigInteger item) {
        return (item.divide(new BigInteger("3")));
    }

    public int test(BigInteger item) {
        return item.mod(new BigInteger(String.valueOf(this.test))).equals(new BigInteger("0")) ? toTrue : toFalse;
    }
}
