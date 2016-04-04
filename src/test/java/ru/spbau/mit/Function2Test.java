package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function2Test {
    @Test
    public void testApply() {
        assertEquals((int) TestFunctions.SUM.apply(10, 11), 21);
        assertEquals((int) TestFunctions.X_SQUARE_PLUS_Y.apply(10, 11), 111);

        TestFunctions.AA_TO_INT.apply(TestFunctions.A_INSTANCE, TestFunctions.A_INSTANCE);
        TestFunctions.AA_TO_INT.apply(TestFunctions.B_INSTANCE, TestFunctions.A_INSTANCE);
        TestFunctions.AA_TO_INT.apply(TestFunctions.A_INSTANCE, TestFunctions.B_INSTANCE);
        TestFunctions.AA_TO_INT.apply(TestFunctions.B_INSTANCE, TestFunctions.B_INSTANCE);

        TestFunctions.BB_TO_INT.apply(TestFunctions.B_INSTANCE, TestFunctions.B_INSTANCE);
    }

    @Test
    public void testCompose() {
        assertEquals((int) TestFunctions.SUM.compose(TestFunctions.SQUARE).apply(3, 4), 49);
        assertEquals((int) TestFunctions.X_SQUARE_PLUS_Y.compose(TestFunctions.SQUARE).apply(3, 4), 169);

        TestFunctions.INT_INT_TO_A.compose(TestFunctions.A_TO_INT);
        TestFunctions.INT_INT_TO_B.compose(TestFunctions.A_TO_INT);
    }

    @Test
    public void testBind1() {
        assertEquals(TestFunctions.SUM.apply(3, 4),
                     TestFunctions.SUM.bind1(3).apply(4));
        assertEquals(TestFunctions.X_SQUARE_PLUS_Y.apply(3, 4),
                     TestFunctions.X_SQUARE_PLUS_Y.bind1(3).apply(4));
    }

    @Test
    public void testBind2() {
        assertEquals(TestFunctions.SUM.apply(3, 4),
                     TestFunctions.SUM.bind2(4).apply(3));
        assertEquals(TestFunctions.X_SQUARE_PLUS_Y.apply(3, 4),
                     TestFunctions.X_SQUARE_PLUS_Y.bind2(4).apply(3));
    }

    @Test
    public void testCurry() {
        assertEquals(TestFunctions.SUM.apply(3, 4),
                     TestFunctions.SUM.curry().apply(3).apply(4));
        assertEquals(TestFunctions.X_SQUARE_PLUS_Y.apply(3, 4),
                     TestFunctions.X_SQUARE_PLUS_Y.curry().apply(3).apply(4));
    }

}
