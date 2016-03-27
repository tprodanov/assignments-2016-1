package ru.spbau.mit;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

public class CollectionsTest {

    public CollectionsTest() {
        final int smallIntListSize = 4;
        for (int i = 1; i <= smallIntListSize; ++i) {
            smallIntList.add(i);
        }

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
        Collections.map(TestFunctions.A_PREDICATE, listOfA);

        Collections.filter(TestFunctions.A_PREDICATE, listOfA);
        Collections.filter(TestFunctions.B_PREDICATE, listOfB);
        Collections.filter(TestFunctions.A_PREDICATE, listOfB);
    }

    @Test
    public void simpleTestMap() {
        List<Integer> squares = new ArrayList<>();
        squares.add(1);
        squares.add(FOUR);

        final int nine = 9;
        squares.add(nine);

        final int sixteen = 16;
        squares.add(sixteen);

        assertIterableEquals(squares, Collections.map(TestFunctions.SQUARE, smallIntList));
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
    public void simpleTestFilter() {
        List<Integer> evenNumbers = new ArrayList<>();
        evenNumbers.add(2);
        evenNumbers.add(FOUR);

        assertIterableEquals(evenNumbers, Collections.filter(TestFunctions.IS_EVEN, smallIntList));
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
    public void simpleTestTakeWhileUntil() {
        List<Integer> firstTwo = new ArrayList<>();
        firstTwo.add(1);
        firstTwo.add(2);

        assertIterableEquals(firstTwo,
                Collections.takeWhile(TestFunctions.LESS_EQUAL_TWO, smallIntList));
        assertIterableEquals(firstTwo,
                Collections.takeUntil(TestFunctions.LESS_EQUAL_TWO.not(), smallIntList));
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
    public void simpleTestFold() {
        final int oneHundredFortyEight = 148;
        assertEquals(oneHundredFortyEight,
                (int) Collections.foldl(TestFunctions.X_SQUARE_PLUS_Y, 0, smallIntList));

        final int thirty = 30;
        assertEquals(thirty,
                (int) Collections.foldr(TestFunctions.X_SQUARE_PLUS_Y, 0, smallIntList));
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

    @Test
    public void foldlTypesTest() {
        // Why "? extends G" is important in foldl declaration
        List<Integer> intList = new ArrayList<>();
        Collections.foldl(TestFunctions.A_INT_TO_B, TestFunctions.A_INSTANCE, intList);
    }

    private <T> void assertIterableEquals(Iterable<T> a, Iterable<T> b) {
        Iterator<T> aIterator = a.iterator();
        for (T bElement : b) {
            assertTrue(aIterator.hasNext());
            assertEquals(aIterator.next(), bElement);
        }
        assertFalse(aIterator.hasNext());
    }

    private final List<Integer> randomIntList = new ArrayList<>();
    private final int randomIntListSize = 100;

    private final List<Integer> smallIntList = new ArrayList<>();

    private static final int FOUR = 4;

}
