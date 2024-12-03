package test;

import main.Day2;
import main.Day3;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
