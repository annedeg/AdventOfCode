package helpers;

public class File implements Addable {
    final int size;
    String name;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }
    @Override
    public void addFile(Addable file) {
        return;
    }

    @Override
    public int getSize() {
        return size;
    }
}
