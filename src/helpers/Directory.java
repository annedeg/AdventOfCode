package helpers;


import java.util.ArrayList;

public class Directory implements Addable {
    public Directory parent = null;
    public ArrayList<Addable> files = new ArrayList<>();
    public String name= null;


    public Directory(String name) {

        this.name = name;
    }

    @Override
    public void addFile(Addable file) {
        files.add(file);
    }

    @Override
    public int getSize() {
        return files.stream().mapToInt(Addable::getSize).sum();
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }


    public Directory openDirectory(String name) {
        for (var file : files) {
            if (file instanceof Directory directory && directory.name.equals(name)) {
                return directory;
            }
        }
        return null;
    }

    public ArrayList<Directory> getAllDict() {
        ArrayList<Directory> dicts = new ArrayList<>();
        for (var file : files) {
            if (file instanceof Directory directory) {
                dicts.add(directory);
            }
        }
        return dicts;
    }

    @Override
    public String toString() {
        if (this.getSize() <= 4965705)
            return "";
        return "Directory{" +
                "" + this.name + "" +
                ", totalSize='" + this.getSize() + '\'' +
                ", dicts=" + this.getAllDict() +
                "}\n";
    }
}
//"44965705"
//"5883165"
//"6483228"
//"44965705"
//"11915402"
