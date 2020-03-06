package com.thoughtworks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Pratice2AltTest {

    @Test
    void should_build_string_correctly_for_0() {
        String result = Pratice2.buildCountEachStringAlt(0);

        assertEquals("ling", result);
    }

    @Test
    void should_build_string_correctly_for_1() {
        String result = Pratice2.buildCountEachStringAlt(1);

        assertEquals("yi", result);
    }

    @Test
    void should_build_string_correctly_for_12() {
        String result = Pratice2.buildCountEachStringAlt(12);

        assertEquals("san", result);
    }

    @Test
    void should_build_string_correctly_for_12345() {
        String result = Pratice2.buildCountEachStringAlt(12345);

        assertEquals("yi wu", result);
    }

    @Test
    void should_build_string_correctly_for_5555() {
        String result = Pratice2.buildCountEachStringAlt(5555);

        assertEquals("er ling", result);
    }

    @Test
    void should_build_string_correctly_for_long_max() {
        String result = Pratice2.buildCountEachStringAlt(Long.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
        assertEquals("ba ba", result);
    }
}