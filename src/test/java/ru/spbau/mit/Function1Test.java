package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function1Test {

    @Test
    public void testApply() {
        final int three = 3;
        final int nine = 9;
        assertEquals((int) TestFunctions.SQUARE.apply(three), nine);

        TestFunctions.A_TO_INT.apply(TestFunctions.A_INSTANCE);
        TestFunctions.A_TO_INT.apply(TestFunctions.B_INSTANCE);
        TestFunctions.B_TO_INT.apply(TestFunctions.B_INSTANCE);
    }

    @Test
    public void testCompose() {
        final int three = 3;
        final int eightyOne = 81;
        assertEquals((int) TestFunctions.SQUARE.compose(TestFunctions.SQUARE).apply(three), eightyOne);

        TestFunctions.A_TO_INT.compose(TestFunctions.INT_TO_A);
        TestFunctions.INT_TO_A.compose(TestFunctions.A_TO_INT);
        TestFunctions.INT_TO_B.compose(TestFunctions.A_TO_INT);
    }

}
