package algolib.structures.heaps;

import java.util.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of double heap.
public class DoubleHeapTest
{
    private final List<Integer> numbers =
            List.of(10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26);
    private final int minimum = numbers.stream().min(Comparator.naturalOrder()).orElseThrow();
    private final int maximum = numbers.stream().max(Comparator.naturalOrder()).orElseThrow();
    private DoubleHeap<Integer> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DoubleHeap<>(numbers);
    }

    @Test
    public void constructor_WhenFromDoubleHeap_ThenCopied()
    {
        // given
        testObject = new DoubleHeap<>(Comparator.reverseOrder());
        testObject.addAll(numbers);

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
    public void constructor_WhenFromPriorityQueue_ThenCopied()
    {
        // given
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        priorityQueue.addAll(numbers);

        // when
        DoubleHeap<Number> result = new DoubleHeap<>(priorityQueue);

        // then
        Assertions.assertThat(result.comparator()).isEqualTo(priorityQueue.comparator());
        Assertions.assertThat(result.size()).isEqualTo(priorityQueue.size());
        Assertions.assertThat(result.peekMin()).isEqualTo(priorityQueue.peek());
        Assertions.assertThat(result).containsExactlyInAnyOrderElementsOf(priorityQueue);
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        // when
        boolean result = new DoubleHeap<Integer>().isEmpty();

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
        // when
        int result = new DoubleHeap<Integer>().size();

        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void size_WhenNotEmpty_ThenNumberOfElements()
    {
        // when
        int result = testObject.size();

        // then
        Assertions.assertThat(result).isEqualTo(numbers.size());
    }

    @Test
    public void clear_WhenNotEmpty_ThenEmpty()
    {
        // when
        testObject.clear();

        // then
        Assertions.assertThat(testObject).isEmpty();
    }

    // region iterator & descendingIterator

    @Test
    public void iterator_WhenEmpty_ThenNoElements()
    {
        // when
        Iterator<Integer> result = new DoubleHeap<Integer>().iterator();

        // then
        Assertions.assertThat(result.hasNext()).isFalse();
        Assertions.assertThatThrownBy(result::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void iterator_WhenSingleElement_ThenThisElementOnly()
    {
        // given
        int element = numbers.get(0);

        // when
        Iterator<Integer> result = new DoubleHeap<>(List.of(element)).iterator();

        // then
        Assertions.assertThat(result.hasNext()).isTrue();
        Assertions.assertThat(result.next()).isEqualTo(element);
        Assertions.assertThat(result.hasNext()).isFalse();
    }

    @Test
    public void iterator_WhenMultipleElements_ThenAllElementsMinimumFirstMaximumLast()
    {
        // when
        List<Integer> result = new ArrayList<>();

        testObject.iterator().forEachRemaining(result::add);

        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrderElementsOf(numbers)
                  .contains(minimum, Index.atIndex(0))
                  .contains(maximum, Index.atIndex(result.size() - 1));
    }

    @Test
    public void descendingIterator_WhenEmpty_ThenNoElements()
    {
        // when
        Iterator<Integer> result = new DoubleHeap<Integer>().descendingIterator();

        // then
        Assertions.assertThat(result.hasNext()).isFalse();
        Assertions.assertThatThrownBy(result::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void descendingIterator_WhenSingleElement_ThenThisElementOnly()
    {
        // given
        int element = numbers.get(0);

        // when
        Iterator<Integer> result = new DoubleHeap<>(List.of(element)).descendingIterator();

        // then
        Assertions.assertThat(result.hasNext()).isTrue();
        Assertions.assertThat(result.next()).isEqualTo(element);
        Assertions.assertThat(result.hasNext()).isFalse();
    }

    @Test
    public void descendingIterator_WhenMultipleElements_ThenAllElementsMaximumFirstMinimumLast()
    {
        // when
        List<Integer> result = new ArrayList<>();

        testObject.descendingIterator().forEachRemaining(result::add);

        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrderElementsOf(numbers)
                  .contains(maximum, Index.atIndex(0))
                  .contains(minimum, Index.atIndex(result.size() - 1));
    }

    // endregion
    // region add & offer & addAll

    @Test
    public void add_WhenEmpty_ThenAdded()
    {
        // given
        int element = numbers.get(0);

        testObject = new DoubleHeap<>();

        // when
        testObject.add(element);

        // then
        Assertions.assertThat(testObject).hasSize(1);
        Assertions.assertThat(testObject.peekMin()).isEqualTo(element);
        Assertions.assertThat(testObject.peekMax()).isEqualTo(element);
    }

    @Test
    public void add_WhenNewElementBetweenMinimumAndMaximum_ThenAdded()
    {
        // when
        testObject.add((minimum + maximum) / 2);

        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() + 1);
        Assertions.assertThat(testObject.peekMin()).isEqualTo(minimum);
        Assertions.assertThat(testObject.peekMax()).isEqualTo(maximum);
    }

    @Test
    public void offer_WhenNewElementLessThanMinimum_ThenNewMinimum()
    {
        // given
        int element = minimum - 3;

        // when
        testObject.offer(element);

        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() + 1);
        Assertions.assertThat(testObject.peekMin()).isEqualTo(element);
    }

    @Test
    public void offer_WhenNewElementGreaterThanMaximum_ThenNewMaximum()
    {
        // given
        int element = maximum + 3;

        // when
        testObject.offer(element);

        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() + 1);
        Assertions.assertThat(testObject.peekMax()).isEqualTo(element);
    }

    @Test
    public void addAll_WhenNewElements_ThenAllAdded()
    {
        // given
        List<Integer> elements =
                List.of(minimum - 3, minimum + 5, minimum + 13, minimum + 20, maximum + 3);

        // when
        testObject.addAll(elements);

        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() + elements.size());
        Assertions.assertThat(testObject.peekMin())
                  .isEqualTo(elements.stream().min(Comparator.naturalOrder()).get());
        Assertions.assertThat(testObject.peekMax())
                  .isEqualTo(elements.stream().max(Comparator.naturalOrder()).get());
    }

    // endregion
    // region peek*

    @Test
    public void peek_WhenMultipleElements_ThenMinimalElement()
    {
        // when
        Integer result = testObject.peek();

        // then
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void peekMin_WhenEmpty_ThenNull()
    {
        // when
        Integer result = new DoubleHeap<Integer>().peekMin();

        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void peekMin_WhenSingleElement_ThenThisElement()
    {
        // given
        int element = numbers.get(0);

        // when
        Integer result = new DoubleHeap<>(List.of(element)).peekMin();

        // then
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void peekMin_WhenMultipleElements_ThenMinimalElement()
    {
        // when
        Integer result = testObject.peekMin();

        // then
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void peekMax_WhenEmpty_ThenNull()
    {
        // when
        Integer result = new DoubleHeap<Integer>().peekMax();

        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void peekMax_WhenSingleElement_ThenThisElement()
    {
        // given
        int element = numbers.get(0);

        // when
        Integer result = new DoubleHeap<>(List.of(element)).peekMax();

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
    // region element*

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
        Assertions.assertThatThrownBy(() -> new DoubleHeap<Integer>().elementMin())
                  .isInstanceOf(NoSuchElementException.class);
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
        Assertions.assertThatThrownBy(() -> new DoubleHeap<Integer>().elementMax())
                  .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void elementMax_WhenNotEmpty_ThenMaximalElement()
    {
        // when
        Integer result = testObject.elementMax();

        // then
        Assertions.assertThat(result).isEqualTo(maximum);
    }

    // endregion
    // region poll*

    @Test
    public void poll_WhenMultipleElements_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.poll();

        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void pollMin_WhenEmpty_ThenNull()
    {
        // when
        Integer result = new DoubleHeap<Integer>().pollMin();

        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void pollMin_WhenSingleElement_ThenThisElementRemoved()
    {
        // given
        int element = numbers.get(0);

        testObject = new DoubleHeap<>(List.of(element));

        // when
        Integer result = testObject.pollMin();

        // then
        Assertions.assertThat(testObject).isEmpty();
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void pollMin_WhenMultipleElements_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.pollMin();

        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void pollMin_WhenMultipleCalls_ThenSortedAscendingToComparator()
    {
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.pollMin());

        // then
        Assertions.assertThat(result)
                  .hasSameElementsAs(numbers)
                  .isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    public void pollMax_WhenEmpty_ThenNull()
    {
        // when
        Integer result = new DoubleHeap<Integer>().pollMax();

        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void pollMax_WhenSingleElement_ThenThisElementRemoved()
    {
        // given
        int element = numbers.get(0);

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
        Assertions.assertThat(testObject).hasSize(numbers.size() - 1);
        Assertions.assertThat(result).isEqualTo(maximum);
    }

    @Test
    public void pollMax_WhenMultipleCalls_ThenSortedDescendingToComparator()
    {
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.pollMax());

        // then
        Assertions.assertThat(result)
                  .hasSameElementsAs(numbers)
                  .isSortedAccordingTo(Comparator.reverseOrder());
    }

    // endregion
    // region remove*

    @Test
    public void remove_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.remove();

        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void removeMin_WhenEmpty_ThenNoSuchElementException()
    {
        Assertions.assertThatThrownBy(() -> new DoubleHeap<Integer>().removeMin())
                  .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void removeMin_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.removeMin();

        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void removeMax_WhenEmpty_ThenNoSuchElementException()
    {
        Assertions.assertThatThrownBy(() -> new DoubleHeap<Integer>().removeMax())
                  .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void removeMax_WhenNotEmpty_ThenMaximalElementRemoved()
    {
        // when
        Integer result = testObject.removeMax();

        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() - 1);
        Assertions.assertThat(result).isEqualTo(maximum);
    }

    // endregion
}
