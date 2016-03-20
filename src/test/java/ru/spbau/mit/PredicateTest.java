package ru.spbau.mit;

import org.junit.Test;
import static org.junit.Assert.*;

public class PredicateTest {

    @Test
    public void testApply() {
        assertTrue(TestFunctions.isEven.apply(4));
        assertFalse(TestFunctions.isEven.apply(5));

        TestFunctions.aPredicate.apply(TestFunctions.aInstance);
        TestFunctions.aPredicate.apply(TestFunctions.bInstance);

        TestFunctions.bPredicate.apply(TestFunctions.bInstance);
        // Should not compile
        // TestFunctions.bPredicate.apply(TestFunctions.aInstance);
    }

    @Test
    public void testTrueFalse() {
        assertTrue(Predicate.ALWAYS_TRUE.apply(true));
        assertTrue(Predicate.ALWAYS_TRUE.apply(false));
        assertTrue(Predicate.ALWAYS_TRUE.apply(TestFunctions.aInstance));
        assertTrue(Predicate.ALWAYS_TRUE.apply(TestFunctions.bInstance));
        assertTrue(Predicate.ALWAYS_TRUE.apply(12000000));

        assertFalse(Predicate.ALWAYS_FALSE.apply(true));
        assertFalse(Predicate.ALWAYS_FALSE.apply(false));
        assertFalse(Predicate.ALWAYS_FALSE.apply(TestFunctions.aInstance));
        assertFalse(Predicate.ALWAYS_FALSE.apply(TestFunctions.bInstance));
        assertFalse(Predicate.ALWAYS_FALSE.apply(12000000));
    }

    @Test
    public void testLogicalOperations() {
        assertTrue(Predicate.ALWAYS_TRUE.or(Predicate.ALWAYS_FALSE).apply(null));
        assertTrue(Predicate.ALWAYS_FALSE.or(Predicate.ALWAYS_TRUE).apply(null));
        assertFalse(Predicate.ALWAYS_TRUE.and(Predicate.ALWAYS_FALSE).apply(null));
        assertFalse(Predicate.ALWAYS_FALSE.and(Predicate.ALWAYS_TRUE).apply(null));

        assertTrue(TestFunctions.isEven.and(TestFunctions.isPositive).apply(10));
        assertFalse(TestFunctions.isEven.and(TestFunctions.isPositive).apply(-10));
        assertFalse(TestFunctions.isPositive.and(TestFunctions.isEven).apply(-10));
        assertFalse(TestFunctions.isEven.and(TestFunctions.isPositive).apply(9));

        assertTrue(TestFunctions.isEven.or(TestFunctions.isPositive).apply(11));
        assertFalse(TestFunctions.isEven.and(TestFunctions.isEven.not()).apply(10));
        assertFalse(TestFunctions.isEven.and(TestFunctions.isEven.not()).apply(11));
        assertTrue(TestFunctions.isEven.not().apply(11));
    }

    @Test
    public void testLaziness() {
        assertTrue(Predicate.ALWAYS_TRUE.or(TestFunctions.throwingPredicate).apply(null));
        assertFalse(Predicate.ALWAYS_FALSE.and(TestFunctions.throwingPredicate).apply(null));
    }

    @Test(expected = TestFunctions.PredicateError.class)
    public void testLazinessLeftSide() {
        TestFunctions.throwingPredicate.or(Predicate.ALWAYS_TRUE).apply(null);
    }
}
