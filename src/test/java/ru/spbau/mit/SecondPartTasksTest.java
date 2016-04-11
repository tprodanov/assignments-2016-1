package ru.spbau.mit;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
        final String firstName = "src/test/java/ru/spbau/mit/testFile1.txt";
        final String secondName = "src/test/java/ru/spbau/mit/testFile2.txt";
        final List<String> filenames = asList(firstName, secondName);
        assertEquals(
                Collections.singletonList("abcdef"),
                SecondPartTasks.findQuotes(filenames, "abc"));
        assertEquals(
                asList("abcdef", "defghi"),
                SecondPartTasks.findQuotes(filenames, "de"));
        assertEquals(
                asList("defghi", "ghijkl", "ghijkl"),
                SecondPartTasks.findQuotes(filenames, "hi"));
        assertEquals(
                Collections.emptyList(),
                SecondPartTasks.findQuotes(filenames, "zyw"));
        assertEquals(
                Collections.emptyList(),
                SecondPartTasks.findQuotes(Collections.emptyList(), ""));
    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(Math.PI / 4, SecondPartTasks.piDividedBy4(), 0.01);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> compositions = new HashMap<>();
        compositions.put("AuthorA:37", asList("10_letters", "15_letters.....", "12_letters.."));
        compositions.put("AuthorB:37", asList("11_letters.", "14_letters....", "12_letters.."));
        compositions.put("AuthorC:38", asList("12_letters..", "16_letters......", "10_letters"));
        assertEquals("AuthorC:38", SecondPartTasks.findPrinter(compositions));

        Map<String, List<String>> emptyCompositions = new HashMap<>();
        assertEquals(null, SecondPartTasks.findPrinter(emptyCompositions));
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> ordersA = new HashMap<>();
        ordersA.put("Bread", 3);
        ordersA.put("Milk", 10);
        ordersA.put("Whiskey", 1000);

        Map<String, Integer> ordersB = new HashMap<>();
        ordersB.put("Watermelon", 4);
        ordersB.put("Bread", 4);
        ordersB.put("Milk", 9);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("Bread", 7);
        expected.put("Milk", 19);
        expected.put("Whiskey", 1000);
        expected.put("Watermelon", 4);
        assertMapEquals(expected, SecondPartTasks.calculateGlobalOrder(asList(ordersA, ordersB)));

        assertTrue(SecondPartTasks.calculateGlobalOrder(Collections.emptyList()).isEmpty());
    }

    private <K, V> void assertMapEquals(Map<K, V> mapA, Map<?, ?> mapB) {
        assertEquals(mapA.size(), mapB.size());
        for (Map.Entry<K, V> entry : mapA.entrySet()) {
            assertTrue(mapB.containsKey(entry.getKey()));
            assertEquals(entry.getValue(), mapB.get(entry.getKey()));
        }
    }

}
