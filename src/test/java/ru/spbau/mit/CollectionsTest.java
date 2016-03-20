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
        for (int i = 0; i < randomIntListSize; ++i) {
            randomIntList.add(random.nextInt(1000));
        }
    }

    @Test
    public void compilationTest() {
        Iterable<TestFunctions.A> listOfA = new ArrayList<>();
        Iterable<TestFunctions.B> listOfB = new ArrayList<>();

        Collections.map(TestFunctions.aToInt, listOfA);
        Collections.map(TestFunctions.bToInt, listOfB);
        Collections.map(TestFunctions.aToInt, listOfB);
        // Should not compile
        // Collections.map(TestFunctions.bToInt, listOfA);
        Collections.map(TestFunctions.aPredicate, listOfA);

        Collections.filter(TestFunctions.aPredicate, listOfA);
        Collections.filter(TestFunctions.bPredicate, listOfB);
        Collections.filter(TestFunctions.aPredicate, listOfB);
    }

    @Test
    public void testMap() {
        int i = 0;
        for (int mapElement : Collections.map(TestFunctions.square, randomIntList)) {
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
        for (int afterFilterElement : Collections.filter(TestFunctions.isEven, randomIntList)) {
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
        for (int takeWhileElement : Collections.takeWhile(TestFunctions.isEven, randomIntList)) {
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
        for (int takeUntilElement : Collections.takeUntil(TestFunctions.isEven, randomIntList)) {
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
        Deque<Integer> deque = Collections.foldl(TestFunctions.pushBack,
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
        Deque<Integer> deque = Collections.foldr(TestFunctions.pushFront,
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
    private int randomIntListSize = 100;

}
