package com.test.abc;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SawmillsTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private static String firstInput = "1\n" + "3 2 3 1\n" + "3\n" + "3 1 2 1\n" + "2 1 2\n" + "2 1 4\n" + "0";
    private static String SecondInput = "1\n" + "3 3 2 1\n" + "3\n" + "3 2 1 1\n" + "2 2 1\n" + "2 4 1\n" + "0";
    private static String firstOutPut = "Case:1\n" +
            "Max Profit:4\n" +
            "Order: [2, 3, 1] [1, 3, 2]\n" +
            "Case:2\n" +
            "Max Profit:8\n" +
            "Order: [1, 2, 1] [2, 1, 1] [1, 2] [2, 1] [1, 4]\n";
    private static String secoundOutPut="Case:1\n" +
            "Max Profit:4\n" +
            "Order: [2, 3, 1] [1, 3, 2]\n" +
            "Case:2\n" +
            "Max Profit:8\n" +
            "Order: [2, 1, 1] [1, 2, 1] [2, 1] [1, 2] [1, 4]\n";
    private static String thirdOutPut= "Case:1\n" +
            "Max Profit:4\n" +
            "Order: [7, 2]\n" +
            "Case:2\n" +
            "Max Profit:6\n" +
            "Order: [2, 1, 1] [1, 2, 1] [1, 4]\n";
    private static String firstWrongOutPut = "Case:1\n" +
            "Max Profit: 4\n" +
            "Order: [2, 3, 1] [1, 3, 2]\n" +
            "Case:2\n" +
            "Max Profit: 8\n" +
            "Order: [1, 2, 1] [2, 1, 1] [1, 2] [2, 1] [4, 1]\n";
    private Sawmills factory;

    @BeforeEach
    void setUp() {
        factory = new Sawmills();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Test
    @DisplayName("Check Right Answer for first Lego Land !!")
    public void checkRightAnswerCase() {
        assertEquals(factory.inputReader(firstInput), firstOutPut, "right Answer :)");

    }
    @Test
    @DisplayName("Check Right Answer for  Secound Lego Land !!")
    public void changingPromotionNumbers() {
        assertEquals(factory.inputReader(SecondInput), secoundOutPut, "right Answer :)");

    }

    @Test
    @DisplayName("Check wrong Answer for Lego Land !!")
    public void checkWrongAnswerCase() {
        assertNotEquals(factory.inputReader(firstInput), firstWrongOutPut, "wrong Answer :)");

    }
}
