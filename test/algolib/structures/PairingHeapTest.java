package algolib.structures;

import java.util.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of pairing heap
public class PairingHeapTest
{
    private final Integer[] numbers =
            new Integer[]{10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26};
    private final int minimum = Arrays.stream(numbers).min(Comparator.naturalOrder()).orElseThrow();
    private PairingHeap<Integer> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new PairingHeap<>(Arrays.asList(numbers));
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
        // given
        testObject = new PairingHeap<>();
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
        testObject = new PairingHeap<>();
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

    @Test
    public void iterator_WhenEmpty_ThenNoElements()
    {
        // given
        testObject = new PairingHeap<>();
        // when
        Iterator<Integer> result = testObject.iterator();
        // then
        Assertions.assertThat(result.hasNext()).isFalse();
        Assertions.assertThatCode(result::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void iterator_WhenSingleElement_ThenThisElementOnly()
    {
        // given
        int element = 17;

        testObject = new PairingHeap<>(List.of(element));
        // when
        Iterator<Integer> result = testObject.iterator();
        // then
        Assertions.assertThat(result.hasNext()).isTrue();
        Assertions.assertThat(result.next()).isEqualTo(element);
        Assertions.assertThat(result.hasNext()).isFalse();
    }

    @Test
    public void iterator_WhenMultipleElements_ThenMinimumFirst()
    {
        // when
        List<Integer> result = new ArrayList<>();

        testObject.iterator().forEachRemaining(result::add);
        // then
        Assertions.assertThat(result).contains(minimum, Index.atIndex(0));
    }

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
        Assertions.assertThat(testObject.peek()).isEqualTo(minimum);
    }

    @Test
    public void offer_WhenEmpty_ThenAdded()
    {
        // given
        int element = 19;

        testObject = new PairingHeap<>();
        // when
        testObject.offer(element);
        // then
        Assertions.assertThat(testObject).hasSize(1);
        Assertions.assertThat(testObject.peek()).isEqualTo(element);
    }

    @Test
    public void offer_WhenNewElementIsLessThanMinimum_ThenNewMinimum()
    {
        // given
        int element = minimum - 3;
        // when
        testObject.offer(element);
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length + 1);
        Assertions.assertThat(testObject.peek()).isEqualTo(element);
    }

    // endregion
    // region peek & element

    @Test
    public void peek_WhenEmpty_ThenNull()
    {
        // given
        testObject = new PairingHeap<>();
        // when
        Integer result = testObject.peek();
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void peek_WhenSingleElement_ThenThisElement()
    {
        // given
        int element = 19;

        testObject = new PairingHeap<>(List.of(element));
        // when
        Integer result = testObject.peek();
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
        // given
        testObject = new PairingHeap<>();
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.element());
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void element_WhenSingleElement_ThenThisElement()
    {
        // given
        int element = 19;

        testObject = new PairingHeap<>(List.of(element));
        // when
        Integer result = testObject.element();
        // then
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void element_WhenMultipleElements_ThenMinimalElement()
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
        // given
        testObject = new PairingHeap<>();
        // when
        Integer result = testObject.poll();
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void poll_WhenSingleElement_ThenThisElementRemoved()
    {
        // given
        int element = 19;

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
        Assertions.assertThat(testObject).hasSize(numbers.length - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void remove_WhenEmpty_ThenNoSuchElementException()
    {
        // given
        testObject = new PairingHeap<>();
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.remove());
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void remove_WhenSingleElement_ThenThisElementRemoved()
    {
        // given
        int element = 19;

        testObject = new PairingHeap<>(List.of(element));
        // when
        Integer result = testObject.remove();
        // then
        Assertions.assertThat(testObject).isEmpty();
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void remove_WhenMultipleElements_ThenMinimalElementRemoved()
    {
        // when
        Integer result = testObject.remove();
        // then
        Assertions.assertThat(testObject).hasSize(numbers.length - 1);
        Assertions.assertThat(result).isEqualTo(minimum);
    }

    @Test
    public void remove_WhenMultipleCalls_ThenSorted()
    {
        // when
        List<Integer> result = new ArrayList<>();

        while(!testObject.isEmpty())
            result.add(testObject.remove());
        // then
        Assertions.assertThat(result).hasSameElementsAs(Arrays.asList(numbers));
        Assertions.assertThat(result).isSortedAccordingTo(Comparator.naturalOrder());
    }

    // endregion
    //region merge

    @Test
    public void merge_WhenEmptyAndNotEmpty_ThenSameAsOther()
    {
        // given
        testObject = new PairingHeap<>();

        var other = new PairingHeap<>(Arrays.asList(numbers));
        // when
        testObject.merge(other);
        // then
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.length);
        Assertions.assertThat(testObject.peek()).isEqualTo(other.peek());
    }

    @Test
    public void merge_WhenNotEmptyAndEmpty_ThenNoChanges()
    {
        // when
        testObject.merge(new PairingHeap<>());
        // then
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.length);
        Assertions.assertThat(testObject.peek()).isEqualTo(minimum);
    }

    @Test
    public void merge_WhenOtherHasGreaterMinimum_ThenMinimumRemains()
    {
        // given
        var other = new PairingHeap<>(List.of(minimum + 5, minimum + 13, minimum + 20));
        // when
        testObject.merge(other);
        // then
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.length + other.size());
        Assertions.assertThat(testObject.peek()).isEqualTo(minimum);
    }

    @Test
    public void merge_WhenOtherHasLessMinimum_ThenNewMinimum()
    {
        // given
        int newMinimum = minimum - 4;
        var other = new PairingHeap<>(List.of(newMinimum, minimum + 5, minimum + 13, minimum + 20));
        // when
        testObject.merge(other);
        // then
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.length + other.size());
        Assertions.assertThat(testObject.peek()).isEqualTo(newMinimum);
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
