package ru.spbau.mit;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

public class CollectionsTest {

    public CollectionsTest() {
        Random random = new Random();
        final int randomRange = 1000;
        for (int i = 0; i < randomIntListSize; ++i) {
            randomIntList.add(random.nextInt(randomRange));
        }
    }

    @Test
    public void compilationTest() {
        Iterable<TestFunctions.A> listOfA = new ArrayList<>();
        Iterable<TestFunctions.B> listOfB = new ArrayList<>();

        Collections.map(TestFunctions.A_TO_INT, listOfA);
        Collections.map(TestFunctions.B_TO_INT, listOfB);
        Collections.map(TestFunctions.A_TO_INT, listOfB);
        // Should not compile
        // Collections.map(TestFunctions.B_TO_INT, listOfA);
        Collections.map(TestFunctions.A_PREDICATE, listOfA);

        Collections.filter(TestFunctions.A_PREDICATE, listOfA);
        Collections.filter(TestFunctions.B_PREDICATE, listOfB);
        Collections.filter(TestFunctions.A_PREDICATE, listOfB);
    }

    @Test
    public void testMap() {
        int i = 0;
        for (int mapElement : Collections.map(TestFunctions.SQUARE, randomIntList)) {
            assertTrue(i < randomIntListSize);
            assertEquals(randomIntList.get(i) * randomIntList.get(i), mapElement);
            ++i;
        }
        assertEquals(randomIntListSize, i);
    }

    @Test
    public void testFilter() {
        int i = 0;
        for (int afterFilterElement : Collections.filter(Predicate.ALWAYS_TRUE, randomIntList)) {
            assertTrue(i < randomIntListSize);
            assertEquals(afterFilterElement, (int) randomIntList.get(i));
            ++i;
        }
        assertEquals(randomIntListSize, i);
        assertFalse(Collections.filter(Predicate.ALWAYS_FALSE, randomIntList).iterator().hasNext());

        i = 0;
        for (int afterFilterElement : Collections.filter(TestFunctions.IS_EVEN, randomIntList)) {
            while (randomIntList.get(i) % 2 == 1 && i < randomIntListSize) {
                ++i;
            }
            assertTrue(i < randomIntListSize);
            assertEquals(afterFilterElement, (int) randomIntList.get(i));
            ++i;
        }
        while (i < randomIntListSize) {
            assertTrue(randomIntList.get(i) % 2 == 1);
            ++i;
        }
    }

    @Test
    public void testTakeWhile() {
        int i = 0;
        for (int takeWhileElement : Collections.takeWhile(TestFunctions.IS_EVEN, randomIntList)) {
            assertTrue(i < randomIntListSize);
            assertTrue(randomIntList.get(i) % 2 == 0);
            assertEquals(takeWhileElement, (int) randomIntList.get(i));
            ++i;
        }

        assertTrue(i == randomIntListSize || randomIntList.get(i) % 2 == 1);
    }

    @Test
    public void testTakeUntil() {
        int i = 0;
        for (int takeUntilElement : Collections.takeUntil(TestFunctions.IS_EVEN, randomIntList)) {
            assertTrue(i < randomIntListSize);
            assertTrue(randomIntList.get(i) % 2 == 1);
            assertEquals(takeUntilElement, (int) randomIntList.get(i));
            ++i;
        }

        assertTrue(i == randomIntListSize || randomIntList.get(i) % 2 == 0);
    }

    @Test
    public void testFoldl() {
        int i = 0;
        Deque<Integer> deque = Collections.foldl(TestFunctions.PUSH_BACK,
                new ArrayDeque<Integer>(),
                randomIntList);
        for (int dequeElement : deque) {
            assertTrue(i < randomIntListSize);
            assertEquals(dequeElement, (int) randomIntList.get(i));
            ++i;
        }
        assertTrue(i == randomIntListSize);
    }

    @Test
    public void testFoldr() {
        int i = 0;
        Deque<Integer> deque = Collections.foldr(TestFunctions.PUSH_FRONT,
                new ArrayDeque<Integer>(),
                randomIntList);
        for (int dequeElement : deque) {
            assertTrue(i < randomIntListSize);
            assertEquals(dequeElement, (int) randomIntList.get(i));
            ++i;
        }
        assertTrue(i == randomIntListSize);
    }


    private final ArrayList<Integer> randomIntList = new ArrayList<>();
    private final int randomIntListSize = 100;

}
