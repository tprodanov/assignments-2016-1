package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function2Test {
    @Test
    public void testApply() {

        assertEquals((int) TestFunctions.sum.apply(10, 11), 21);
        assertEquals((int) TestFunctions.xSquarePlusY.apply(10, 11), 111);

        TestFunctions.aaToInt.apply(TestFunctions.aInstance, TestFunctions.aInstance);
        TestFunctions.aaToInt.apply(TestFunctions.bInstance, TestFunctions.aInstance);
        TestFunctions.aaToInt.apply(TestFunctions.aInstance, TestFunctions.bInstance);
        TestFunctions.aaToInt.apply(TestFunctions.bInstance, TestFunctions.bInstance);

        TestFunctions.bbToInt.apply(TestFunctions.bInstance, TestFunctions.bInstance);
        // Should not compile
        // TestFunctions.bbToInt.apply(TestFunctions.bInstance, TestFunctions.aInstance);
        // TestFunctions.bbToInt.apply(TestFunctions.aInstance, TestFunctions.bInstance);
        // TestFunctions.bbToInt.apply(TestFunctions.aInstance, TestFunctions.aInstance);
    }

    @Test
    public void testCompose() {
        assertEquals((int) TestFunctions.sum.compose(TestFunctions.square).apply(3, 4), 49);
        assertEquals((int) TestFunctions.xSquarePlusY.compose(TestFunctions.square).apply(3, 4), 169);

        TestFunctions.intIntToA.compose(TestFunctions.aToInt);
        TestFunctions.intIntToB.compose(TestFunctions.aToInt);
        // Should not compile
        // TestFunctions.intIntToA.compose(TestFunctions.bToInt);
    }

    @Test
    public void testBind1() {
        assertEquals(TestFunctions.sum.apply(3, 4),
                     TestFunctions.sum.bind1(3).apply(4));
        assertEquals(TestFunctions.xSquarePlusY.apply(3, 4),
                     TestFunctions.xSquarePlusY.bind1(3).apply(4));
    }

    @Test
    public void testBind2() {
        assertEquals(TestFunctions.sum.apply(3, 4),
                     TestFunctions.sum.bind2(4).apply(3));
        assertEquals(TestFunctions.xSquarePlusY.apply(3, 4),
                     TestFunctions.xSquarePlusY.bind2(4).apply(3));
    }

    @Test
    public void testCurry() {
        assertEquals(TestFunctions.sum.apply(3, 4),
                     TestFunctions.sum.curry().apply(3).apply(4));
        assertEquals(TestFunctions.xSquarePlusY.apply(3, 4),
                     TestFunctions.xSquarePlusY.curry().apply(3).apply(4));
    }

}
