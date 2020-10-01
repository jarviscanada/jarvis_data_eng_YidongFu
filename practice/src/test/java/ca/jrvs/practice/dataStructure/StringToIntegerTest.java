package ca.jrvs.practice.dataStructure;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringToIntegerTest {

    @Test
    public void myAtoi() {
        String input1 = "35";
        String input2 = "-42";
        String input3 = "35 and things";
        String input4 = "things and 35";
        String input5 = "-9283746483928";

        assertEquals(StringToInteger.myAtoi(input1), 35);
        assertEquals(StringToInteger.myAtoi(input2), -42);
        assertEquals(StringToInteger.myAtoi(input3), 35);
        assertEquals(StringToInteger.myAtoi(input4), 0);
        assertEquals(StringToInteger.myAtoi(input5), -2147483647);
    }
}