package year_2023;

public abstract class CodeDay {
    public abstract void puzzleOne();
    public abstract void puzzleTwo();

    public void run(int run) {
        if (run == 1) {
            this.puzzleOne();
            return;
        }
        this.puzzleTwo();
    }
}
