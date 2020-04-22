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
    public void iterator_WhenNotEmpty_ThenFirstMinimumAndLastMaximum()
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
    public void peekMin_WhenEmpty_ThenNull()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Integer result = testObject.peekMin();
        // then
        Assertions.assertNull(result);
    }

    @Test
    public void peekMin_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.peekMin();
        // then
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void elementMin_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Executable executable = () -> testObject.elementMin();
        // then
        Assertions.assertThrows(NoSuchElementException.class, executable);
    }

    @Test
    public void elementMin_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.elementMin();
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
    public void peekMax_WhenEmpty_ThenNull()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Integer result = testObject.peekMax();
        // then
        Assertions.assertNull(result);
    }

    @Test
    public void peekMax_WhenSingleElement_ThenThisElement()
    {
        // given
        int element = 19;
        testObject = new DoubleHeap<>(List.of(element));
        // when
        Integer result = testObject.peekMax();
        // then
        Assertions.assertEquals(element, result);
    }

    @Test
    public void peekMax_WhenMultipleElements_ThenMaximalElement()
    {
        // when
        Integer result = testObject.peekMax();
        // then
        Assertions.assertEquals(maximum, result);
    }

    @Test
    public void elementMax_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Executable executable = () -> testObject.elementMax();
        // then
        Assertions.assertThrows(NoSuchElementException.class, executable);
    }

    @Test
    public void elementMax_WhenMultipleElements_ThenMaximalElement()
    {
        // when
        Integer result = testObject.elementMax();
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
        Assertions.assertEquals(element, testObject.peekMin());
        Assertions.assertEquals(element, testObject.peekMax());
    }

    @Test
    public void offer_WhenNewElementIsLess_ThenAddedToMin()
    {
        // given
        int element = minimum - 1;
        // when
        testObject.offer(element);
        // then
        Assertions.assertEquals(numbers.length + 1, testObject.size());
        Assertions.assertEquals(element, testObject.peekMin());
    }

    @Test
    public void offer_WhenNewElementIsGreater_ThenAddedToMax()
    {
        // given
        int element = maximum + 1;
        // when
        testObject.offer(element);
        // then
        Assertions.assertEquals(numbers.length + 1, testObject.size());
        Assertions.assertEquals(element, testObject.peekMax());
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
        Assertions.assertEquals(minimum, testObject.peekMin());
        Assertions.assertEquals(maximum, testObject.peekMax());
    }

    @Test
    public void pollMin_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.pollMin();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void pollMin_WhenSingleElement_ThenThisElementRemoved()
    {
        // given
        int element = 19;
        testObject = new DoubleHeap<>(List.of(element));
        // when
        Integer result = testObject.pollMin();
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
    public void removeMin_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Executable executable = () -> testObject.removeMin();
        // then
        Assertions.assertThrows(NoSuchElementException.class, executable);
    }

    @Test
    public void removeMin_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.removeMin();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(minimum, result);
    }

    @Test
    public void removeMin_WhenMultipleCalls_ThenSortedDescending()
    {
        // given
        Integer[] expected = Arrays.copyOf(numbers, numbers.length);

        Arrays.sort(expected);
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.removeMin());
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
    public void pollMax_WhenEmpty_ThenNull()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Integer result = testObject.pollMax();
        // then
        Assertions.assertNull(result);
    }

    @Test
    public void pollMax_WhenSingleElement_ThenThisElementRemoved()
    {
        // given
        int element = 19;
        testObject = new DoubleHeap<>(List.of(element));
        // when
        Integer result = testObject.pollMax();
        // then
        Assertions.assertTrue(testObject.isEmpty());
        Assertions.assertEquals(element, result);
    }

    @Test
    public void pollMax_WhenMultipleElements_ThenMaximalElementRemoved()
    {
        // when
        Integer result = testObject.pollMax();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(maximum, result);
    }

    @Test
    public void removeMax_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Executable executable = () -> testObject.removeMax();
        // then
        Assertions.assertThrows(NoSuchElementException.class, executable);
    }

    @Test
    public void removeMax_WhenMultipleElements_ThenMaximalElementRemoved()
    {
        // when
        Integer result = testObject.removeMax();
        // then
        Assertions.assertEquals(numbers.length - 1, testObject.size());
        Assertions.assertEquals(maximum, result);
    }

    @Test
    public void removeMax_WhenMultipleCalls_ThenSortedDescending()
    {
        // given
        Integer[] expected = Arrays.copyOf(numbers, numbers.length);

        Arrays.sort(expected, (n, m) -> m.compareTo(n));
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.removeMax());
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
