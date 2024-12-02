package test;

import main.Day2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSafe {
    Day2 day2 = new Day2();

    @Test
    public void testNoChangeIsInvalid() {
        List<Integer> integers = List.of(1, 1, 1, 1);
        boolean safe = day2.safe(integers);
        assertFalse(safe);
    }

    @Test
    public void testBigChangeIsInvalid() {
        List<Integer> integers = List.of(1, 8, 9, 10);
        boolean safe = day2.safe(integers);
        assertFalse(safe);
    }

    @Test
    public void testChangeOfThreeIsValid() {
        List<Integer> integers = List.of(1, 4, 5, 7);
        boolean safe = day2.safe(integers);
        assertTrue(safe);
    }

    @Test
    public void testChangeOfOneIsValid() {
        List<Integer> integers = List.of(1, 2, 3, 4);
        boolean safe = day2.safe(integers);
        assertTrue(safe);
    }

    @Test
    public void testUpAndDownChangeIsInvalid() {
        List<Integer> integers = List.of(1, 3, 2, 5);
        boolean safe = day2.safe(integers);
        assertFalse(safe);
    }

    @Test
    public void testLastIsTheSameIsInvalid() {
        List<Integer> integers = List.of(1, 2, 3, 3);
        boolean safe = day2.safe(integers);
        assertFalse(safe);
    }
}
