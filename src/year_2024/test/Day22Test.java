package year_2024.test;

import org.junit.jupiter.api.Test;
import year_2024.main.Day22;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day22Test {
    Day22 day22 = new Day22();

    @Test
    public void mixWillResultInCorrectValue() {
        long mix = day22.mix(15, 42);
        assertEquals(37, mix);
    }

    @Test
    public void pruneWillResultInCorrectValue() {
        long prune = day22.prune(100000000);
        assertEquals(16113920, prune);
    }

    @Test
    public void nextValueCalculatesCorrectValue() {
        long result = day22.calculateNextSecret(123);
        assertEquals(15887950, result);
    }
}
