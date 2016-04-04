package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function1Test {

    @Test
    public void testApply() {
        assertEquals((int) TestFunctions.SQUARE.apply(3), 9);

        TestFunctions.A_TO_INT.apply(TestFunctions.A_INSTANCE);
        TestFunctions.A_TO_INT.apply(TestFunctions.B_INSTANCE);
        TestFunctions.B_TO_INT.apply(TestFunctions.B_INSTANCE);
    }

    @Test
    public void testCompose() {
        assertEquals((int) TestFunctions.SQUARE.compose(TestFunctions.SQUARE).apply(3), 81);

        TestFunctions.A_TO_INT.compose(TestFunctions.INT_TO_A);
        TestFunctions.INT_TO_A.compose(TestFunctions.A_TO_INT);
        TestFunctions.INT_TO_B.compose(TestFunctions.A_TO_INT);
    }

}
