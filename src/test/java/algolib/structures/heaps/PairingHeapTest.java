package algolib.structures.heaps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of pairing heap.
public class PairingHeapTest
{
    private final List<Integer> numbers =
            List.of(10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26);
    private final int minimum = numbers.stream().min(Comparator.naturalOrder()).orElseThrow();
    private PairingHeap<Integer> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new PairingHeap<>(numbers);
    }

    @Test
    public void constructor_WhenFromPairingHeap_ThenCopied()
    {
        // when
        var result = new PairingHeap<>(testObject);
        // then
        Assertions.assertThat(result.size()).isEqualTo(testObject.size());
        Assertions.assertThat(result.peek()).isEqualTo(testObject.peek());
        Assertions.assertThat(result).containsExactlyInAnyOrderElementsOf(testObject);
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        // when
        boolean result = new PairingHeap<Integer>().isEmpty();
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
        int result = new PairingHeap<Integer>().size();
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

    // region iterator

    @Test
    public void iterator_WhenEmpty_ThenNoElements()
    {
        // when
        Iterator<Integer> result = new PairingHeap<Integer>().iterator();
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
        Iterator<Integer> result = new PairingHeap<>(List.of(element)).iterator();
        // then
        Assertions.assertThat(result.hasNext()).isTrue();
        Assertions.assertThat(result.next()).isEqualTo(element);
        Assertions.assertThat(result.hasNext()).isFalse();
    }

    @Test
    public void iterator_WhenMultipleElements_ThenAllElementsMinimumFirst()
    {
        // when
        List<Integer> result = new ArrayList<>();

        testObject.iterator().forEachRemaining(result::add);
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrderElementsOf(numbers)
                  .contains(minimum, Index.atIndex(0));
    }

    // endregion
    // region add & offer && addAll

    @Test
    public void add_WhenEmpty_ThenAdded()
    {
        // given
        int element = numbers.get(0);

        testObject = new PairingHeap<>();
        // when
        testObject.add(element);
        // then
        Assertions.assertThat(testObject).hasSize(1);
        Assertions.assertThat(testObject.peek()).isEqualTo(element);
    }

    @Test
    public void add_WhenNewElementLessThanMinimum_ThenNewMinimum()
    {
        // given
        int element = minimum - 3;
        // when
        testObject.add(element);
        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() + 1);
        Assertions.assertThat(testObject.peek()).isEqualTo(element);
    }

    @Test
    public void offer_WhenNewElementGreaterThanMinimum_ThenAdded()
    {
        // when
        testObject.offer(minimum + 3);
        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() + 1);
        Assertions.assertThat(testObject.peek()).isEqualTo(minimum);
    }

    @Test
    public void addAll_WhenNewElements_ThenAllAdded()
    {
        // given
        List<Integer> elements = List.of(minimum - 3, minimum + 5, minimum + 13, minimum + 20);
        // when
        testObject.addAll(elements);
        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() + elements.size());
        Assertions.assertThat(testObject.peek())
                  .isEqualTo(elements.stream().min(Comparator.naturalOrder()).get());
    }

    // endregion
    // region peek & element

    @Test
    public void peek_WhenEmpty_ThenNull()
    {
        // when
        Integer result = new PairingHeap<Integer>().peek();
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void peek_WhenSingleElement_ThenThisElement()
    {
        // given
        int element = numbers.get(0);
        // when
        Integer result = new PairingHeap<>(List.of(element)).peek();
        // then
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void peek_WhenMultipleElements_ThenMinimalElement()
    {
        // when
        Integer result = testObject.peek();
        // then
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void element_WhenEmpty_ThenNoSuchElementException()
    {
        Assertions.assertThatThrownBy(() -> new PairingHeap<Integer>().element())
                  .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void element_WhenNotEmpty_ThenMinimalElement()
    {
        // when
        Integer result = testObject.element();
        // then
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    // endregion
    // region poll & remove

    @Test
    public void poll_WhenEmpty_ThenNull()
    {
        // when
        Integer result = new PairingHeap<Integer>().poll();
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void poll_WhenSingleElement_ThenThisElementRemoved()
    {
        // given
        int element = numbers.get(0);

        testObject = new PairingHeap<>(List.of(element));
        // when
        Integer result = testObject.poll();
        // then
        Assertions.assertThat(testObject).isEmpty();
        Assertions.assertThat(result).isEqualTo(element);
    }

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
    public void poll_WhenMultipleCalls_ThenSortedAscendingToComparator()
    {
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.poll());
        // then
        Assertions.assertThat(result)
                  .hasSameElementsAs(numbers)
                  .isSortedAccordingTo(testObject.comparator());
    }

    @Test
    public void remove_WhenEmpty_ThenNoSuchElementException()
    {
        Assertions.assertThatThrownBy(() -> new PairingHeap<Integer>().remove())
                  .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void remove_WhenNotEmpty_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.remove();
        // then
        Assertions.assertThat(testObject).hasSize(numbers.size() - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    // endregion
    // region merge

    @Test
    public void merge_WhenEmptyAndNotEmpty_ThenSameAsOther()
    {
        // given
        testObject = new PairingHeap<>();

        var other = new PairingHeap<>(numbers);
        // when
        testObject.merge(other);
        // then
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size());
        Assertions.assertThat(testObject.peek()).isEqualTo(other.peek());
    }

    @Test
    public void merge_WhenNotEmptyAndEmpty_ThenNoChanges()
    {
        // when
        testObject.merge(new PairingHeap<>());
        // then
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size());
        Assertions.assertThat(testObject.peek()).isEqualTo(minimum);
    }

    @Test
    public void merge_WhenOtherHasLessMinimum_ThenNewMinimum()
    {
        // given
        var other =
                new PairingHeap<>(List.of(minimum - 3, minimum + 5, minimum + 13, minimum + 20));
        // when
        testObject.merge(other);
        // then
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size() + other.size());
        Assertions.assertThat(testObject.peek()).isEqualTo(other.peek());
    }

    @Test
    public void merge_WhenOtherHasGreaterMinimum_ThenMinimumRemains()
    {
        // given
        var other = new PairingHeap<>(List.of(minimum + 5, minimum + 13, minimum + 20));
        // when
        testObject.merge(other);
        // then
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size() + other.size());
        Assertions.assertThat(testObject.peek()).isEqualTo(minimum);
    }

    @Test
    public void merge_WhenMultipleMerges_ThenChangedOnlyMergingHeap()
    {
        // given
        List<Integer> firstElements = List.of(10, 20);
        List<Integer> secondElements = List.of(4, 8);

        testObject = new PairingHeap<>();
        var first = new PairingHeap<>(firstElements);
        var second = new PairingHeap<>(secondElements);
        // when
        testObject.merge(first);
        testObject.merge(second);
        // then
        Assertions.assertThat(testObject.peek())
                  .isEqualTo(Stream.concat(firstElements.stream(), secondElements.stream())
                                   .min(Comparator.naturalOrder())
                                   .orElseThrow());
        Assertions.assertThat(testObject)
                  .containsExactlyInAnyOrderElementsOf(
                          Stream.concat(firstElements.stream(), secondElements.stream()).toList());
        Assertions.assertThat(first).containsExactlyInAnyOrderElementsOf(firstElements);
        Assertions.assertThat(second).containsExactlyInAnyOrderElementsOf(secondElements);
    }

    // endregion
}
