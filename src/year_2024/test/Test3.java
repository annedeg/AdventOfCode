package year_2024.test;

import org.junit.jupiter.api.Test;
import year_2024.main.Day3;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test3 {
    Day3 day = new Day3();

    @Test
    public void shouldReturnValid() {
        String input = "xmul(2,4)%&mu";
        int value = day.getInstructionValue(input);
        assertEquals(value, 8);
    }

    @Test
    public void shouldNotReturnInValid() {
        String input = "xmul( 2 ,4)%&mu";
        int value = day.getInstructionValue(input);
        assertEquals(value, 0);
    }
}
