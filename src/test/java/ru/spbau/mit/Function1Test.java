package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function1Test {

    @Test
    public void testApply() {
        assertEquals((int) TestFunctions.square.apply(3), 9);

        TestFunctions.aToInt.apply(TestFunctions.aInstance);
        TestFunctions.aToInt.apply(TestFunctions.bInstance);
        TestFunctions.bToInt.apply(TestFunctions.bInstance);

        // Should not compile
        // TestFunctions.bToInt.apply(TestFunctions.aInstance);
    }

    @Test
    public void testCompose() {
        assertEquals((int) TestFunctions.square.compose(TestFunctions.square).apply(3), 81);

        TestFunctions.aToInt.compose(TestFunctions.intToA);
        TestFunctions.intToA.compose(TestFunctions.aToInt);
        TestFunctions.intToB.compose(TestFunctions.aToInt);
        // Should not compile
        // TestFunctions.intToA.compose(TestFunctions.bToInt);
    }

}
