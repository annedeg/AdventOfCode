package year_2024.main;

import helpers.Helper;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day17 {
    public void puzzleOne() {
        String s = Helper.readToString(2024, 17);
        String[] input = s.split("\n\n");
        String[] registers = input[0].split("\n");
        ArrayList<BigInteger> bigIntegers = Arrays.stream(registers).map(str -> new BigInteger(str.split(": ")[1])).collect(Collectors.toCollection(ArrayList::new));

        String instruction = input[1].split(": ")[1].replaceAll("\n", "");

        ThreeBitComputer threeBitComputer = new ThreeBitComputer(instruction, bigIntegers.get(0), bigIntegers.get(1), bigIntegers.get(2));
        String output = threeBitComputer.calculateAll();
        System.out.println(output);
    }

    class ThreeBitComputer {
        BigInteger a;
        BigInteger b;
        BigInteger c;

        StringJoiner output = new StringJoiner(",");
        int pointer = 0;
        ArrayList<Integer> instructions;

        public ThreeBitComputer(String instruction, BigInteger a, BigInteger b, BigInteger c) {
            instructions = Arrays.stream(instruction.split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public String calculateAll() {
            while (pointer < instructions.size()-1) {
                doOp(getOperation(), getOperand());
            }

            return output.toString();
        }

        public int getOperation() {
            return instructions.get(pointer);
        }

        public int getOperand() {
            return instructions.get(pointer+1);
        }

        public void doOp(int opcode, int operand) {
            boolean jumped = false;

            switch (opcode) {
                case 0 -> a = dv(operand);
                case 1 -> b = b.xor(BigInteger.valueOf(operand));
                case 2 -> b = getCombo(operand).mod(BigInteger.valueOf(8));
                case 3 -> {
                    if (a.compareTo(BigInteger.ZERO) == 0) {
                        break;
                    }

                    jumped = true;
                    pointer = operand;
                }
                case 4 -> b = c.xor(b);
                case 5 -> {
                    output.add(String.valueOf((getCombo(operand).mod(BigInteger.valueOf(8)))));
                }
                case 6 -> b = dv(operand);
                case 7 -> c = dv(operand);
                default -> throw new IllegalStateException("Unexpected value: " + opcode);
            }

            if (!jumped) {
                pointer+=2;
            }
        }

        private BigInteger dv(int operand) {
            return a.divide(BigInteger.TWO.pow(getCombo(operand).intValue()));
        }

        public BigInteger getCombo(int op) {
            if (op >= 0 && op <= 3) {
                return BigInteger.valueOf(op);
            }

            if (op == 4) {
                return a;
            }

            if (op == 5) {
                return b;
            }

            if (op == 6) {
                return c;
            }

            if (op == 7) {
                throw new RuntimeException("Not valid program");
            }

            return null;
        }

        public void printRegisters() {
            System.out.println("A:" + a.toString());
            System.out.println("B:" + b.toString());
            System.out.println("C:" + c.toString());
        }
    }




    public void puzzleTwo() {
        String s = Helper.readToString(2024, 17);
        String[] input = s.split("\n\n");
        String[] registers = input[0].split("\n");
        ArrayList<BigInteger> bigIntegers = Arrays.stream(registers).map(str -> new BigInteger(str.split(": ")[1])).collect(Collectors.toCollection(ArrayList::new));

        String instruction = input[1].split(": ")[1].replaceAll("\n", "");

        String s1 = instruction.replaceAll(",", "");

        Optional<BigInteger> search = search(BigInteger.ZERO, instruction.split(",").length-1, instruction);

        System.out.println(search.get());

    }

    public Optional<BigInteger> search(BigInteger a, int depth, String instruction) {
        if (depth < 0) {
            return Optional.empty();
        }

        for (int i = 0; i < 8; i++) {
            BigInteger bigInteger = BigInteger.valueOf(i).shiftLeft((3 * depth));
            BigInteger newA = a.or(bigInteger);
            ThreeBitComputer threeBitComputer = new ThreeBitComputer(instruction, newA, BigInteger.ZERO, BigInteger.ZERO);
            String result = threeBitComputer.calculateAll();

            String[] replacedResult = result.split(",");
            String[] replacedInstruction = instruction.split(",");
            if (replacedResult.length == replacedInstruction.length) {
                if (result.equals(instruction)) {
                    return Optional.of(newA);
                }


                if (Objects.equals(replacedResult[depth], replacedInstruction[depth])) {
                    Optional<BigInteger> search = search(newA, depth - 1, instruction);
                    if (search.isPresent()) {
                        return search;
                    }
                }
            }
        }

        return Optional.empty();
    }

//    public String toBin(int val) {
//
//    }

    public static void main(String[] args) {
        Day17 day = new Day17();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
