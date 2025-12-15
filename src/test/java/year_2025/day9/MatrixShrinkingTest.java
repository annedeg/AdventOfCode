package year_2025.day9;

import helpers.MatrixLocation;
import org.junit.jupiter.api.Test;
import year_2025.Edge;
import year_2025.SmallMatrix;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixShrinkingTest {

    @Test
    public void testSimpleCanShrink() {
        String input = """
                0,0
                4,0
                4,4
                0,4
                """;

        SmallMatrix smallMatrix = new SmallMatrix(Arrays.stream(input.split("\n")).toList());

        char[][] matrix = smallMatrix.getMatrix();

        char[][] expectedMatrix = {
                {'#', '#'},
                {'#', '#'},
        };

        assertTrue(Arrays.deepEquals(matrix, expectedMatrix));

        assertEquals(4L, smallMatrix.getEdgeLength(new Edge(new MatrixLocation(0,0), new MatrixLocation(4, 0))));
    }

    @Test
    public void testHarderExampleCanShrink() {
        String input = """
                7,1
                11,1
                11,7
                9,7
                9,5
                2,5
                2,3
                7,3
                """;

        SmallMatrix smallMatrix = new SmallMatrix(Arrays.stream(input.split("\n")).toList());
        char[][] matrix = smallMatrix.getMatrix();

        char[][] expectedMatrix = {
                {Character.MIN_VALUE, '#', Character.MIN_VALUE, '#'},
                {'#', '#', Character.MIN_VALUE, Character.MIN_VALUE},
                {'#', Character.MIN_VALUE, '#', Character.MIN_VALUE},
                {Character.MIN_VALUE, Character.MIN_VALUE, '#', '#'},
        };

        assertTrue(Arrays.deepEquals(matrix, expectedMatrix));
    }
}
