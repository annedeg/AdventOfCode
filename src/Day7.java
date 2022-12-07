import helpers.Directory;
import helpers.File;
import helpers.Helper;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Day7 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/dayseven.txt");

        Directory main = new Directory("/");
        Directory currentDic = main;
        for (var inputRow : input) {
            System.out.println(inputRow);
            if (inputRow.contains("$ ls")) {
                continue;
            }
            if (inputRow.contains("dir")) {
                var newDic = new Directory(inputRow.split("dir ")[1]);
                newDic.setParent(currentDic);
                currentDic.addFile(newDic);
                continue;
            }

            if (inputRow.contains("$ cd")) {
                String toWhere = inputRow.split("cd ")[1];
                if (toWhere.equals("..")) {
                    System.out.println("Current: " + currentDic.name);
                    currentDic = currentDic.parent;
                    System.out.println("Current: " + currentDic.name);
                } else if(toWhere.equals("/")) {
                    currentDic = main;
                } else {
                    currentDic = currentDic.openDirectory(toWhere);
                }
                continue;
            }

            if (Integer.parseInt(inputRow.split(" ")[0]) > 0) {
                currentDic.addFile(new File(inputRow.split(" ")[1], Integer.parseInt(inputRow.split(" ")[0])));
            }
        }

        int total = getSize(main);
        System.out.println(total);

    }

    public int getSize(Directory directory) {
        int total = 0;
        for (var file : directory.files) {
            if (file instanceof Directory dir) {
                if (dir.getSize() <= 100000) {
                    total += dir.getSize();
                    for (var dict : dir.getAllDict()) {
                        total += dict.getSize();
                    }
                } else {
                    total += getSize(dir);
                }
            }
        }
        return total;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/dayseven.txt");

        Directory main = new Directory("/");
        Directory currentDic = main;
        for (var inputRow : input) {
//            System.out.println(inputRow);
            if (inputRow.contains("$ ls")) {
                continue;
            }
            if (inputRow.contains("dir")) {
                var newDic = new Directory(inputRow.split("dir ")[1]);
                newDic.setParent(currentDic);
                currentDic.addFile(newDic);
                continue;
            }

            if (inputRow.contains("$ cd")) {
                String toWhere = inputRow.split("cd ")[1];
                if (toWhere.equals("..")) {
//                    System.out.println("Current: " + currentDic.name);
                    currentDic = currentDic.parent;
//                    System.out.println("Current: " + currentDic.name);
                } else if(toWhere.equals("/")) {
                    currentDic = main;
                } else {
                    currentDic = currentDic.openDirectory(toWhere);
                }
                continue;
            }

            if (Integer.parseInt(inputRow.split(" ")[0]) > 0) {
                currentDic.addFile(new File(inputRow.split(" ")[1], Integer.parseInt(inputRow.split(" ")[0])));
            }
        }

        int target = -(70000000 - 30000000 - main.getSize());
        System.out.println(target);
        System.out.println(main.toString());
    }

    public int findLowest(Directory main, int target, int currentLowest) {
        for (var file : main.files) {
            if (file instanceof Directory directory) {
                if (directory.getSize() > target && directory.getSize() < currentLowest) {
                    currentLowest = directory.getSize();
                } else {
                    for (var newD : directory.getAllDict()) {
                        int s = findLowest(newD, target, currentLowest);
                        if (s < currentLowest) {
                            currentLowest = s;
                        }
                    }
                }
            }
        }
        return currentLowest;
    }
}
