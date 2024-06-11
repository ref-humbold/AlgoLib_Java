package algolib.structures;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of AVL tree.
public class AvlTreeTest
{
    private final List<Integer> numbers =
            List.of(10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26);
    private final List<Integer> absent = List.of(111, 140, 187, 253);
    private final List<Integer> present = IntStream.range(0, numbers.size())
                                                   .filter(i -> i % 3 == 2)
                                                   .mapToObj(numbers::get)
                                                   .toList();
    private AvlTree<Integer> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new AvlTree<>(numbers);
    }

    @Test
    public void constructor_WhenFromAvlTree_ThenCopied()
    {
        // given
        testObject = new AvlTree<>(Comparator.comparing(i -> -i));
        testObject.addAll(numbers);

        // when
        AvlTree<Number> result = new AvlTree<>(testObject);

        // then
        Assertions.assertThat(result.comparator()).isEqualTo(testObject.comparator());
        Assertions.assertThat(result.size()).isEqualTo(testObject.size());
        Assertions.assertThat(result).containsExactlyInAnyOrderElementsOf(testObject);
    }

    @Test
    public void toString_WhenNotEmpty_ThenTextRepresentation()
    {
        // when
        String result = testObject.toString();

        // then
        Assertions.assertThat(result)
                  .isEqualTo("{|2, 6, 10, 14, 18, 24, 26, 30, 37, 45, 51, 68, 71, 97|}");
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        // when
        boolean result = new AvlTree<Integer>().isEmpty();

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
        int result = new AvlTree<Integer>().size();

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
        Iterator<Integer> result = new AvlTree<Integer>().iterator();

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
        Iterator<Integer> iterator = new AvlTree<>(List.of(element)).iterator();

        // then
        Assertions.assertThat(iterator.hasNext()).isTrue();
        Assertions.assertThat(iterator.next()).isEqualTo(element);
        Assertions.assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void iterator_WhenMultipleElements_ThenSortedElements()
    {
        // when
        List<Integer> result = new ArrayList<>(testObject);

        // then
        Assertions.assertThat(result).isSorted().containsExactlyInAnyOrderElementsOf(numbers);
    }

    @Test
    public void descendingIterator_WhenEmpty_ThenNoElements()
    {
        // when
        Iterator<Integer> result = new AvlTree<Integer>().descendingIterator();

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
        Iterator<Integer> iterator = new AvlTree<>(List.of(element)).descendingIterator();

        // then
        Assertions.assertThat(iterator.hasNext()).isTrue();
        Assertions.assertThat(iterator.next()).isEqualTo(element);
        Assertions.assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void descendingIterator_WhenMultipleElements_ThenReverseSortedElements()
    {
        // given
        Iterator<Integer> iterator = testObject.descendingIterator();

        // when
        List<Integer> result = new ArrayList<>();

        while(iterator.hasNext())
            result.add(iterator.next());

        // then
        Assertions.assertThat(result)
                  .isSortedAccordingTo((n, m) -> m.compareTo(n))
                  .containsExactlyInAnyOrderElementsOf(numbers);
    }

    // endregion
    // region contains

    @Test
    public void contains_WhenEmpty_ThenFalse()
    {
        // when
        boolean result = new AvlTree<Integer>().contains(numbers.get(0));

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void contains_WhenPresentElement_ThenTrue()
    {
        for(Integer i : present)
        {
            // when
            boolean result = testObject.contains(i);

            // then
            Assertions.assertThat(result).isTrue();
        }
    }

    @Test
    public void contains_WhenAbsentElement_ThenFalse()
    {
        for(Integer i : absent)
        {
            // when
            boolean result = testObject.contains(i);

            // then
            Assertions.assertThat(result).isFalse();
        }
    }

    // endregion
    // region add

    @Test
    public void add_WhenEmpty_ThenTrue()
    {
        // given
        int element = numbers.get(0);

        testObject = new AvlTree<>();

        // when
        boolean result = testObject.add(element);

        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject).contains(element).hasSize(1);
    }

    @Test
    public void add_WhenNewElement_ThenTrue()
    {
        for(Integer i : absent)
        {
            // when
            boolean result = testObject.add(i);

            // then
            Assertions.assertThat(result).isTrue();
            Assertions.assertThat(testObject).contains(i);
        }

        Assertions.assertThat(testObject).hasSize(numbers.size() + absent.size());
    }

    @Test
    public void add_WhenPresentElement_ThenFalse()
    {
        for(Integer i : present)
        {
            // when
            boolean result = testObject.add(i);

            // then
            Assertions.assertThat(result).isFalse();
            Assertions.assertThat(testObject).contains(i);
        }

        Assertions.assertThat(testObject).hasSameSizeAs(numbers);
    }

    // endregion
    // region remove

    @Test
    public void remove_WhenEmpty_ThenFalse()
    {
        // when
        boolean result = new AvlTree<Integer>().remove(numbers.get(0));

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void remove_WhenPresentElement_ThenTrue()
    {
        for(Integer i : present)
        {
            // when
            boolean result = testObject.remove(i);

            // then
            Assertions.assertThat(result).isTrue();
            Assertions.assertThat(testObject).doesNotContain(i);
        }

        Assertions.assertThat(testObject).hasSize(numbers.size() - present.size());
    }

    @Test
    public void remove_WhenAbsentElement_ThenFalse()
    {
        for(Integer i : absent)
        {
            // when
            boolean result = testObject.remove(i);

            // then
            Assertions.assertThat(result).isFalse();
            Assertions.assertThat(testObject).doesNotContain(i);
        }

        Assertions.assertThat(testObject).hasSameSizeAs(numbers);
    }

    @Test
    public void remove_WhenRootGreaterThanElement_ThenRemoved()
    {
        // given
        int root = absent.get(1);
        int element = absent.get(0);

        testObject = new AvlTree<>(List.of(root, element));

        // when
        boolean result = testObject.remove(root);

        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject).doesNotContain(root).contains(element).hasSize(1);
    }

    @Test
    public void remove_WhenRootLessThanElement_ThenRemoved()
    {
        // given
        int root = absent.get(0);
        int element = absent.get(1);

        testObject = new AvlTree<>(List.of(root, element));

        // when
        boolean result = testObject.remove(root);

        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject).doesNotContain(root).contains(element).hasSize(1);
    }

    @Test
    public void remove_WhenRootOnly_ThenEmpty()
    {
        // given
        int root = absent.get(0);

        testObject = new AvlTree<>(Collections.singletonList(root));

        // when
        boolean result = testObject.remove(root);

        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject).doesNotContain(root).isEmpty();
    }

    // endregion
    // region removeAll

    @Test
    public void removeAll_WhenEmpty_ThenFalse()
    {
        // when
        boolean result = new AvlTree<Integer>().removeAll(new HashSet<>(numbers));

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void removeAll_WhenPresentElements_ThenTrue()
    {
        // given
        Set<Integer> elements = new HashSet<>(present);

        // when
        boolean result = testObject.removeAll(elements);

        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject)
                  .doesNotContainAnyElementsOf(elements)
                  .hasSize(numbers.size() - present.size());
    }

    @Test
    public void removeAll_WhenAbsentElements_ThenFalse()
    {
        // given
        Set<Integer> elements = new HashSet<>(absent);

        // when
        boolean result = testObject.removeAll(elements);

        // then
        Assertions.assertThat(result).isFalse();
        Assertions.assertThat(testObject)
                  .doesNotContainAnyElementsOf(elements)
                  .hasSameSizeAs(numbers);
    }

    @Test
    public void removeAll_WhenPresentAndAbsentElements_ThenTrue()
    {
        // given
        Set<Integer> elements =
                Stream.concat(present.stream(), absent.stream()).collect(Collectors.toSet());

        // when
        boolean result = testObject.removeAll(elements);

        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject)
                  .doesNotContainAnyElementsOf(elements)
                  .hasSize(numbers.size() - present.size());
    }

    // endregion
}
