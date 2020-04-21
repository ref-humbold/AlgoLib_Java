// Tests: Structure of double heap
package algolib.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class DoubleHeapTest
{
    private final Integer[] numbers =
            new Integer[]{10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26};
    private final int minimum = Arrays.stream(numbers).min(Comparator.naturalOrder()).orElseThrow();
    private final int maximum = Arrays.stream(numbers).max(Comparator.naturalOrder()).orElseThrow();
    private DoubleHeap<Integer> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DoubleHeap<>(Arrays.asList(numbers));
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void size_WhenEmpty_ThenZero()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        int result = testObject.size();
        // then
        Assertions.assertEquals(0, result);
    }

    @Test
    public void size_WhenNotEmpty_ThenNumberOfElements()
    {
        int result = testObject.size();

        Assertions.assertEquals(numbers.length, result);
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        boolean result = testObject.isEmpty();
        // then
        Assertions.assertTrue(result);
    }

    @Test
    public void isEmpty_WhenNotEmpty_ThenFalse()
    {
        // when
        boolean result = testObject.isEmpty();
        // then
        Assertions.assertFalse(result);
    }

    @Test
    public void iterator_WhenNotEmpty_ThenFirstisMinimumAndLastIsMaximum()
    {
        // when
        List<Integer> result = new ArrayList<>(testObject);
        // then
        Assertions.assertEquals(minimum, result.get(0));
        Assertions.assertEquals(maximum, result.get(result.size() - 1));
    }

    @Test
    public void peek_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.peek();
        // then
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void peekFirst_WhenEmpty_ThenNull()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Integer result = testObject.peekFirst();
        // then
        Assertions.assertNull(result);
    }

    @Test
    public void peekFirst_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.peekFirst();
        // then
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void elementFirst_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Executable executable = () -> testObject.elementFirst();
        // then
        Assertions.assertThrows(NoSuchElementException.class, executable);
    }

    @Test
    public void elementFirst_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.elementFirst();
        // then
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void element_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.element();
        // then
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void peekLast_WhenEmpty_ThenNull()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Integer result = testObject.peekLast();
        // then
        Assertions.assertNull(result);
    }

    @Test
    public void peekLast_WhenSingleElement_ThenThisElement()
    {
        // given
        int element = 19;
        testObject = new DoubleHeap<>(List.of(element));
        // when
        Integer result = testObject.peekLast();
        // then
        Assertions.assertEquals(element, result);
    }

    @Test
    public void peekLast_WhenMultipleElements_ThenMaximalElement()
    {
        // when
        Integer result = testObject.peekLast();
        // then
        Assertions.assertEquals(maximum, result);
    }

    @Test
    public void elementLast_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Executable executable = () -> testObject.elementLast();
        // then
        Assertions.assertThrows(NoSuchElementException.class, executable);
    }

    @Test
    public void elementLast_WhenMultipleElements_ThenMaximalElement()
    {
        // when
        Integer result = testObject.elementLast();
        // then
        Assertions.assertEquals(maximum, result);
    }

    @Test
    public void offer_WhenEmpty_ThenAdded()
    {
        // given
        int element = 19;
        testObject = new DoubleHeap<>();
        // when
        testObject.offer(element);
        // then
        Assertions.assertEquals(1, testObject.size());
        Assertions.assertEquals(element, testObject.peekFirst());
        Assertions.assertEquals(element, testObject.peekLast());
    }

    @Test
    public void offer_WhenNewElementIsLess_ThenAddedToFirst()
    {
        // given
        int element = minimum - 1;
        // when
        testObject.offer(element);
        // then
        Assertions.assertEquals(numbers.length + 1, testObject.size());
        Assertions.assertEquals(element, testObject.peekFirst());
    }

    @Test
    public void offer_WhenNewElementIsGreater_ThenAddedToLast()
    {
        // given
        int element = maximum + 1;
        // when
        testObject.offer(element);
        // then
        Assertions.assertEquals(numbers.length + 1, testObject.size());
        Assertions.assertEquals(element, testObject.peekLast());
    }

    @Test
    public void add_WhenNewElement_ThenAdded()
    {
        // given
        int element = 46;
        // when
        testObject.add(element);
        // then
        Assertions.assertEquals(numbers.length + 1, testObject.size());
        Assertions.assertEquals(minimum, testObject.peekFirst());
        Assertions.assertEquals(maximum, testObject.peekLast());
    }

    @Test
    public void pollFirst_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.pollFirst();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void pollFirst_WhenSingleElement_ThenThisElementRemoved()
    {
        // given
        int element = 19;
        testObject = new DoubleHeap<>(List.of(element));
        // when
        Integer result = testObject.pollFirst();
        // then
        Assertions.assertTrue(testObject.isEmpty());
        Assertions.assertEquals(element, result);
    }

    @Test
    public void poll_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.poll();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void removeFirst_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Executable executable = () -> testObject.removeFirst();
        // then
        Assertions.assertThrows(NoSuchElementException.class, executable);
    }

    @Test
    public void removeFirst_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.removeFirst();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void removeFirst_WhenMultipleCalls_ThenSortedDescending()
    {
        // given
        Integer[] expected = Arrays.copyOf(numbers, numbers.length);

        Arrays.sort(expected);
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.removeFirst());
        // then
        Assertions.assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void remove_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.remove();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void pollLast_WhenEmpty_ThenNull()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Integer result = testObject.pollLast();
        // then
        Assertions.assertNull(result);
    }

    @Test
    public void pollLast_WhenSingleElement_ThenThisElementRemoved()
    {
        // given
        int element = 19;
        testObject = new DoubleHeap<>(List.of(element));
        // when
        Integer result = testObject.pollLast();
        // then
        Assertions.assertTrue(testObject.isEmpty());
        Assertions.assertEquals(element, result);
    }

    @Test
    public void pollLast_WhenMultipleElements_ThenMaximalElementRemoved()
    {
        // when
        Integer result = testObject.pollLast();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(maximum, result);
    }

    @Test
    public void removeLast_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Executable executable = () -> testObject.removeLast();
        // then
        Assertions.assertThrows(NoSuchElementException.class, executable);
    }

    @Test
    public void removeLast_WhenMultipleElements_ThenMaximalElementRemoved()
    {
        // when
        Integer result = testObject.removeLast();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(maximum, result);
    }

    @Test
    public void removeLast_WhenMultipleCalls_ThenSortedDescending()
    {
        // given
        Integer[] expected = Arrays.copyOf(numbers, numbers.length);

        Arrays.sort(expected, (n, m) -> m.compareTo(n));
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.removeLast());
        // then
        Assertions.assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void clear_WhenNotEmpty_ThenEmpty()
    {
        // when
        testObject.clear();
        // then
        Assertions.assertEquals(0, testObject.size());
    }
}
