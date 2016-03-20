package ru.spbau.mit;

import org.junit.Test;
import static org.junit.Assert.*;

public class PredicateTest {

    @Test
    public void testApply() {
        final int four = 4;
        final int five = 5;
        assertTrue(TestFunctions.IS_EVEN.apply(four));
        assertFalse(TestFunctions.IS_EVEN.apply(five));

        TestFunctions.A_PREDICATE.apply(TestFunctions.A_INSTANCE);
        TestFunctions.A_PREDICATE.apply(TestFunctions.B_INSTANCE);

        TestFunctions.B_PREDICATE.apply(TestFunctions.B_INSTANCE);
        // Should not compile
        // TestFunctions.B_PREDICATE.apply(TestFunctions.A_INSTANCE);
    }

    @Test
    public void testTrueFalse() {
        assertTrue(Predicate.ALWAYS_TRUE.apply(true));
        assertTrue(Predicate.ALWAYS_TRUE.apply(false));
        assertTrue(Predicate.ALWAYS_TRUE.apply(TestFunctions.A_INSTANCE));
        assertTrue(Predicate.ALWAYS_TRUE.apply(TestFunctions.B_INSTANCE));
        final int trashInt = 121343523;
        assertTrue(Predicate.ALWAYS_TRUE.apply(trashInt));

        assertFalse(Predicate.ALWAYS_FALSE.apply(true));
        assertFalse(Predicate.ALWAYS_FALSE.apply(false));
        assertFalse(Predicate.ALWAYS_FALSE.apply(TestFunctions.A_INSTANCE));
        assertFalse(Predicate.ALWAYS_FALSE.apply(TestFunctions.B_INSTANCE));
        assertFalse(Predicate.ALWAYS_FALSE.apply(trashInt));
    }

    @Test
    public void testLogicalOperations() {
        assertTrue(Predicate.ALWAYS_TRUE.or(Predicate.ALWAYS_FALSE).apply(null));
        assertTrue(Predicate.ALWAYS_FALSE.or(Predicate.ALWAYS_TRUE).apply(null));
        assertFalse(Predicate.ALWAYS_TRUE.and(Predicate.ALWAYS_FALSE).apply(null));
        assertFalse(Predicate.ALWAYS_FALSE.and(Predicate.ALWAYS_TRUE).apply(null));

        final int ten = 10;
        final int minusTen = -10;
        final int nine = 9;
        assertTrue(TestFunctions.IS_EVEN.and(TestFunctions.IS_POSITIVE).apply(ten));
        assertFalse(TestFunctions.IS_EVEN.and(TestFunctions.IS_POSITIVE).apply(minusTen));
        assertFalse(TestFunctions.IS_POSITIVE.and(TestFunctions.IS_EVEN).apply(minusTen));
        assertFalse(TestFunctions.IS_EVEN.and(TestFunctions.IS_POSITIVE).apply(nine));

        final int eleven = 11;
        assertTrue(TestFunctions.IS_EVEN.or(TestFunctions.IS_POSITIVE).apply(eleven));
        assertFalse(TestFunctions.IS_EVEN.and(TestFunctions.IS_EVEN.not()).apply(ten));
        assertFalse(TestFunctions.IS_EVEN.and(TestFunctions.IS_EVEN.not()).apply(eleven));
        assertTrue(TestFunctions.IS_EVEN.not().apply(eleven));
    }

    @Test
    public void testLaziness() {
        assertTrue(Predicate.ALWAYS_TRUE.or(TestFunctions.THROWING_PREDICATE).apply(null));
        assertFalse(Predicate.ALWAYS_FALSE.and(TestFunctions.THROWING_PREDICATE).apply(null));
    }

    @Test(expected = TestFunctions.PredicateError.class)
    public void testLazinessLeftSide() {
        TestFunctions.THROWING_PREDICATE.or(Predicate.ALWAYS_TRUE).apply(null);
    }
}
