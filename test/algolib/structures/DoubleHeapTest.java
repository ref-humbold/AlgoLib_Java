package algolib.structures;

import java.util.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of double heap
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

    @Test
    public void constructor_WhenFromDoubleHeap_ThenCopied()
    {
        // given
        testObject = new DoubleHeap<>(Comparator.comparing(i -> -i));
        testObject.addAll(Arrays.asList(numbers));
        // when
        DoubleHeap<Number> result = new DoubleHeap<>(testObject);
        // then
        Assertions.assertThat(result.comparator()).isEqualTo(testObject.comparator());
        Assertions.assertThat(result.size()).isEqualTo(testObject.size());
        Assertions.assertThat(result.peekMin()).isEqualTo(testObject.peekMin());
        Assertions.assertThat(result.peekMax()).isEqualTo(testObject.peekMax());
        Assertions.assertThat(result).containsExactlyInAnyOrderElementsOf(testObject);
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        boolean result = testObject.isEmpty();
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void isEmpty_WhenNotEmpty_ThenFalse()
    {
        // when
        boolean result = testObject.isEmpty();
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void size_WhenEmpty_ThenZero()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        int result = testObject.size();
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void size_WhenNotEmpty_ThenNumberOfElements()
    {
        // when
        int result = testObject.size();
        // then
        Assertions.assertThat(result).isEqualTo(numbers.length);
    }

    // region iterator & descendingIterator

    @Test
    public void iterator_WhenEmpty_ThenNoElements()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Iterator<Integer> result = testObject.iterator();
        // then
        Assertions.assertThat(result.hasNext()).isFalse();
        Assertions.assertThatCode(result::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void iterator_WhenOneElement_ThenThisElementOnly()
    {
        // given
        int element = 17;
        testObject = new DoubleHeap<>(List.of(element));
        // when
        Iterator<Integer> result = testObject.iterator();
        // then
        Assertions.assertThat(result.hasNext()).isTrue();
        Assertions.assertThat(result.next()).isEqualTo(element);
        Assertions.assertThat(result.hasNext()).isFalse();
    }

    @Test
    public void iterator_WhenNotEmpty_ThenFirstMinimumAndLastMaximum()
    {
        // when
        List<Integer> result = new ArrayList<>();

        testObject.iterator().forEachRemaining(result::add);
        // then
        Assertions.assertThat(result).contains(minimum, Index.atIndex(0));
        Assertions.assertThat(result).contains(maximum, Index.atIndex(result.size() - 1));
    }

    @Test
    public void descendingIterator_WhenEmpty_ThenNoElements()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Iterator<Integer> result = testObject.descendingIterator();
        // then
        Assertions.assertThat(result.hasNext()).isFalse();
        Assertions.assertThatCode(result::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void descendingIterator_WhenOneElement_ThenThisElementOnly()
    {
        // given
        int element = 17;
        testObject = new DoubleHeap<>(List.of(element));
        // when
        Iterator<Integer> result = testObject.descendingIterator();
        // then
        Assertions.assertThat(result.hasNext()).isTrue();
        Assertions.assertThat(result.next()).isEqualTo(element);
        Assertions.assertThat(result.hasNext()).isFalse();
    }

    @Test
    public void descendingIterator_WhenNotEmpty_ThenFirstMaximumAndLastMinimum()
    {
        // when
        List<Integer> result = new ArrayList<>();

        testObject.descendingIterator().forEachRemaining(result::add);
        // then
        Assertions.assertThat(result).contains(maximum, Index.atIndex(0));
        Assertions.assertThat(result).contains(minimum, Index.atIndex(result.size() - 1));
    }

    // endregion
    // region add & offer

    @Test
    public void add_WhenNewElement_ThenAdded()
    {
        // given
        int element = 46;
        // when
        testObject.add(element);
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length + 1);
        Assertions.assertThat(testObject.peekMin()).isEqualTo(minimum);
        Assertions.assertThat(testObject.peekMax()).isEqualTo(maximum);
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
        Assertions.assertThat(testObject).hasSize(1);
        Assertions.assertThat(testObject.peekMin()).isEqualTo(element);
        Assertions.assertThat(testObject.peekMax()).isEqualTo(element);
    }

    @Test
    public void offer_WhenNewElementIsLess_ThenAddedToMin()
    {
        // given
        int element = minimum - 1;
        // when
        testObject.offer(element);
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length + 1);
        Assertions.assertThat(testObject.peekMin()).isEqualTo(element);
    }

    @Test
    public void offer_WhenNewElementIsGreater_ThenAddedToMax()
    {
        // given
        int element = maximum + 1;
        // when
        testObject.offer(element);
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length + 1);
        Assertions.assertThat(testObject.peekMax()).isEqualTo(element);
    }

    // endregion
    // region peek

    @Test
    public void peek_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.peek();
        // then
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void peekMin_WhenEmpty_ThenNull()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Integer result = testObject.peekMin();
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void peekMin_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.peekMin();
        // then
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void peekMax_WhenEmpty_ThenNull()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Integer result = testObject.peekMax();
        // then
        Assertions.assertThat(result).isNull();
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
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void peekMax_WhenMultipleElements_ThenMaximalElement()
    {
        // when
        Integer result = testObject.peekMax();
        // then
        Assertions.assertThat(result).isEqualTo(maximum);
    }

    // endregion
    // region element

    @Test
    public void element_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.element();
        // then
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void elementMin_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.elementMin());
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void elementMin_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.elementMin();
        // then
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void elementMax_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.elementMax());
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void elementMax_WhenMultipleElements_ThenMaximalElement()
    {
        // when
        Integer result = testObject.elementMax();
        // then
        Assertions.assertThat(result).isEqualTo(maximum);
    }

    // endregion
    // region poll

    @Test
    public void poll_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.poll();
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void pollMin_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.pollMin();
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
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
        Assertions.assertThat(testObject).isEmpty();
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void pollMax_WhenEmpty_ThenNull()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Integer result = testObject.pollMax();
        // then
        Assertions.assertThat(result).isNull();
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
        Assertions.assertThat(testObject).isEmpty();
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void pollMax_WhenMultipleElements_ThenMaximalElementRemoved()
    {
        // when
        Integer result = testObject.pollMax();
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length - 1);
        Assertions.assertThat(result).isEqualTo(maximum);
    }

    // endregion
    // region remove

    @Test
    public void remove_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.remove();
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void removeMin_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.removeMin());
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void removeMin_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.removeMin();
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void removeMin_WhenMultipleCalls_ThenSortedAsComparator()
    {
        // given
        testObject = new DoubleHeap<>(Comparator.naturalOrder());
        testObject.addAll(Arrays.asList(numbers));
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.removeMin());
        // then
        Assertions.assertThat(result).hasSameElementsAs(Arrays.asList(numbers));
        Assertions.assertThat(result).isSortedAccordingTo(testObject.comparator());
    }

    @Test
    public void removeMax_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new DoubleHeap<>();
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.removeMax());
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void removeMax_WhenMultipleElements_ThenMaximalElementRemoved()
    {
        // when
        Integer result = testObject.removeMax();
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length - 1);
        Assertions.assertThat(result).isEqualTo(maximum);
    }

    @Test
    public void removeMax_WhenMultipleCalls_ThenSortedReversedToComparator()
    {
        // given
        testObject = new DoubleHeap<>(Comparator.naturalOrder());
        testObject.addAll(Arrays.asList(numbers));
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.removeMax());
        // then
        Assertions.assertThat(result).hasSameElementsAs(Arrays.asList(numbers));
        Assertions.assertThat(result).isSortedAccordingTo(testObject.comparator().reversed());
    }

    // endregion

    @Test
    public void clear_WhenNotEmpty_ThenEmpty()
    {
        // when
        testObject.clear();
        // then
        Assertions.assertThat(testObject).isEmpty();
    }
}
