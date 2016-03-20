package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function2Test {
    @Test
    public void testApply() {

        final int ten = 10;
        final int eleven = 11;
        final int twentyOne = 21;
        final int oneHundredEleven = 111;
        assertEquals((int) TestFunctions.SUM.apply(ten, eleven), twentyOne);
        assertEquals((int) TestFunctions.X_SQUARE_PLUS_Y.apply(ten, eleven), oneHundredEleven);

        TestFunctions.AA_TO_INT.apply(TestFunctions.A_INSTANCE, TestFunctions.A_INSTANCE);
        TestFunctions.AA_TO_INT.apply(TestFunctions.B_INSTANCE, TestFunctions.A_INSTANCE);
        TestFunctions.AA_TO_INT.apply(TestFunctions.A_INSTANCE, TestFunctions.B_INSTANCE);
        TestFunctions.AA_TO_INT.apply(TestFunctions.B_INSTANCE, TestFunctions.B_INSTANCE);

        TestFunctions.BB_TO_INT.apply(TestFunctions.B_INSTANCE, TestFunctions.B_INSTANCE);
        // Should not compile
        // TestFunctions.BB_TO_INT.apply(TestFunctions.B_INSTANCE, TestFunctions.A_INSTANCE);
        // TestFunctions.BB_TO_INT.apply(TestFunctions.A_INSTANCE, TestFunctions.B_INSTANCE);
        // TestFunctions.BB_TO_INT.apply(TestFunctions.A_INSTANCE, TestFunctions.A_INSTANCE);
    }

    @Test
    public void testCompose() {
        final int fortyNine = 49;
        final int oneHundredSixtyNine = 169;
        assertEquals((int) TestFunctions.SUM.compose(TestFunctions.SQUARE).apply(THREE, FOUR), fortyNine);
        assertEquals((int) TestFunctions.X_SQUARE_PLUS_Y.
                                                compose(TestFunctions.SQUARE).apply(THREE, FOUR),
                    oneHundredSixtyNine);

        TestFunctions.INT_INT_TO_A.compose(TestFunctions.A_TO_INT);
        TestFunctions.INT_INT_TO_B.compose(TestFunctions.A_TO_INT);
        // Should not compile
        // TestFunctions.INT_INT_TO_A.compose(TestFunctions.B_TO_INT);
    }

    @Test
    public void testBind1() {
        assertEquals(TestFunctions.SUM.apply(THREE, FOUR),
                     TestFunctions.SUM.bind1(THREE).apply(FOUR));
        assertEquals(TestFunctions.X_SQUARE_PLUS_Y.apply(THREE, FOUR),
                     TestFunctions.X_SQUARE_PLUS_Y.bind1(THREE).apply(FOUR));
    }

    @Test
    public void testBind2() {
        assertEquals(TestFunctions.SUM.apply(THREE, FOUR),
                     TestFunctions.SUM.bind2(FOUR).apply(THREE));
        assertEquals(TestFunctions.X_SQUARE_PLUS_Y.apply(THREE, FOUR),
                     TestFunctions.X_SQUARE_PLUS_Y.bind2(FOUR).apply(THREE));
    }

    @Test
    public void testCurry() {
        assertEquals(TestFunctions.SUM.apply(THREE, FOUR),
                     TestFunctions.SUM.curry().apply(THREE).apply(FOUR));
        assertEquals(TestFunctions.X_SQUARE_PLUS_Y.apply(THREE, FOUR),
                     TestFunctions.X_SQUARE_PLUS_Y.curry().apply(THREE).apply(FOUR));
    }

    private static final int THREE = 3;
    private static final int FOUR = 4;

}
