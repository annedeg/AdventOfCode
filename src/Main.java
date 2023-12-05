import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Main {
    private static final int day = 5;
    private static final int puzzle = 2;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Path path = Path.of("./src/");
        ArrayList<String> files;
        try {
            files = Files.walk(path)
                    .map(e -> e.getFileName().toString())
                    .filter((e) -> e.endsWith(".java") && e.startsWith("Day"))
                    .map(e -> e.substring(0, e.length() - 5))
                    .sorted(Comparator.comparingInt(a -> Integer.parseInt(a.split("Day")[1])))
                    .collect(Collectors.toCollection(ArrayList::new));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            CodeDay dayObject = (CodeDay) Class.forName(files.get(day - 1)).newInstance();
            dayObject.run(puzzle);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("Day " + day + " does not have a class");
        }
    }
}