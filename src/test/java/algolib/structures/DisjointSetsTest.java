package algolib.structures;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of disjoint sets (union-find).
public class DisjointSetsTest
{
    private final List<Integer> numbers =
            List.of(10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26);
    private final List<Integer> absent = List.of(111, 140, 187, 253);
    private final List<Integer> present = IntStream.range(0, numbers.size())
                                                   .filter(i -> i % 3 == 2)
                                                   .mapToObj(numbers::get)
                                                   .toList();
    private DisjointSets<Integer> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DisjointSets<>(numbers.stream().map(List::of).toList());
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void constructor_WhenDuplicatesInDifferentSets_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> new DisjointSets<>(List.of(List.of(1, 2, 3), List.of(1, 11, 21, 31))));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void constructor_WhenDuplicatesInSameSet_ThenConstructed()
    {
        // given
        List<List<Integer>> sets = List.of(List.of(1, 2, 3), List.of(10, 100, 10));
        // when
        testObject = new DisjointSets<>(sets);
        // then
        Assertions.assertThat(testObject.size()).isEqualTo(sets.size());
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        // when
        boolean result = new DisjointSets<Integer>().isEmpty();
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void isEmpty_WhenNonEmpty_ThenFalse()
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
        int result = new DisjointSets<Integer>().size();
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void size_WhenElements_ThenNumberOfSets()
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
        Assertions.assertThat(testObject.size()).isZero();
    }

    // region contains

    @Test
    public void contains_WhenEmpty_ThenFalse()
    {
        // when
        boolean result = new DisjointSets<Integer>().contains(numbers.get(0));
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void contains_WhenPresentElement_ThenTrue()
    {
        // when
        boolean result = testObject.contains(present.get(0));
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void contains_WhenAbsentElement_ThenFalse()
    {
        // when
        boolean result = testObject.contains(absent.get(0));
        // then
        Assertions.assertThat(result).isFalse();
    }

    // endregion
    // region add

    @Test
    public void add_WhenEmpty_ThenNewSet()
    {
        // given
        testObject = new DisjointSets<>();
        // when
        DisjointSets<Integer> result = testObject.add(numbers);
        // then
        Assertions.assertThat(result).isSameAs(testObject);

        for(int element : numbers)
        {
            Assertions.assertThat(testObject.contains(element)).isTrue();
            Assertions.assertThat(testObject.findSet(element)).isEqualTo(numbers.get(0));
        }

        Assertions.assertThat(testObject.size()).isOne();
    }

    @Test
    public void add_WhenEmptyNewElements_ThenNoChanges()
    {
        // when
        DisjointSets<Integer> result = testObject.add(List.of());
        // then
        Assertions.assertThat(result).isSameAs(testObject);
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size());
    }

    @Test
    public void add_WhenNewElements_ThenNewSet()
    {
        // when
        DisjointSets<Integer> result = testObject.add(absent);
        // then
        Assertions.assertThat(result).isSameAs(testObject);

        for(int element : absent)
        {
            Assertions.assertThat(testObject.contains(element)).isTrue();
            Assertions.assertThat(testObject.findSet(element)).isEqualTo(absent.get(0));
        }

        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size() + 1);
    }

    @Test
    public void add_WhenPresentElements_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.add(present));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void add_WhenNewAndPresentElements_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> testObject.add(Stream.concat(absent.stream(), present.stream()).toList()));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void add_WhenEmptyNewElementsToPresentRepresent_ThenNoChanges()
    {
        // when
        DisjointSets<Integer> result = testObject.add(List.of(), present.get(0));
        // then
        Assertions.assertThat(result).isSameAs(testObject);
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size());
    }

    @Test
    public void add_WhenNewElementsToPresentRepresent_ThenAddedToExistingSet()
    {
        // given
        int represent = present.get(0);
        // when
        DisjointSets<Integer> result = testObject.add(absent, represent);
        // then
        Assertions.assertThat(result).isSameAs(testObject);

        for(int element : absent)
        {
            Assertions.assertThat(testObject.contains(element)).isTrue();
            Assertions.assertThat(testObject.findSet(element))
                      .isEqualTo(testObject.findSet(represent));
        }

        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size());
    }

    @Test
    public void add_WhenNewElementsToAbsentRepresent_ThenNoSuchElementException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> testObject.add(absent, absent.get(0)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void add_WhenPresentElementsToAbsentRepresent_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> testObject.add(present, absent.get(0)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    // endregion
    // region findSet*

    @Test
    public void findSet_WhenEmpty_ThenNoSuchElementException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> new DisjointSets<Integer>().findSet(numbers.get(0)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findSet_WhenPresentElement_ThenRepresent()
    {
        // given
        int element = present.get(0);
        // when
        int result = testObject.findSet(element);
        // then
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void findSet_WhenAbsentElement_ThenNoSuchElementException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.findSet(absent.get(0)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findSet_WhenSameSet_ThenSameRepresent()
    {
        // given
        testObject = new DisjointSets<>(List.of(numbers));
        // when
        int result1 = testObject.findSet(numbers.get(1));
        int result2 = testObject.findSet(numbers.get(2));
        // then
        Assertions.assertThat(result2).isEqualTo(result1);
    }

    @Test
    public void findSetOrDefault_WhenPresentElement_ThenRepresent()
    {
        // given
        int element = present.get(0);
        int defaultValue = absent.get(0);
        // when
        int result = testObject.findSetOrDefault(element, defaultValue);
        // then
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void findSetOrDefault_WhenAbsentElement_ThenDefaultValue()
    {
        // given
        int element = absent.get(0);
        int defaultValue = present.get(0);
        // when
        int result = testObject.findSetOrDefault(element, defaultValue);
        // then
        Assertions.assertThat(result).isEqualTo(defaultValue);
    }

    // endregion
    // region unionSet

    @Test
    public void unionSet_WhenDifferentSets_ThenSameRepresent()
    {
        // given
        int element1 = present.get(0);
        int element2 = present.get(1);
        // when
        DisjointSets<Integer> result = testObject.unionSet(element1, element2);
        // then
        Assertions.assertThat(result).isSameAs(testObject);
        Assertions.assertThat(testObject.isSameSet(element1, element2)).isTrue();
        Assertions.assertThat(testObject.findSet(element2)).isEqualTo(testObject.findSet(element1));
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size() - 1);
    }

    @Test
    public void unionSet_WhenSingleElement_ThenNoChanges()
    {
        // given
        int element = present.get(0);
        // when
        DisjointSets<Integer> result = testObject.unionSet(element, element);
        // then
        Assertions.assertThat(result).isSameAs(testObject);
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size());
    }

    @Test
    public void unionSet_WhenSameSet_ThenSameRepresent()
    {
        // given
        int element1 = present.get(0);
        int element2 = present.get(1);
        testObject.unionSet(element1, element2);
        // when
        DisjointSets<Integer> result = testObject.unionSet(element2, element1);
        // then
        Assertions.assertThat(result).isSameAs(testObject);
        Assertions.assertThat(testObject.isSameSet(element1, element2)).isTrue();
        Assertions.assertThat(testObject.findSet(element2)).isEqualTo(testObject.findSet(element1));
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size() - 1);
    }

    @Test
    public void unionSet_WhenNewElementsInChain_ThenSameRepresent()
    {
        // given
        int first = present.get(0);
        int last = present.get(present.size() - 1);

        // when
        for(int i = 1; i < present.size(); ++i)
            testObject.unionSet(present.get(i - 1), present.get(i));

        // then
        Assertions.assertThat(testObject.isSameSet(first, last)).isTrue();
        Assertions.assertThat(testObject.findSet(last)).isEqualTo(testObject.findSet(first));
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size() - present.size() + 1);
    }

    // endregion
    // region isSameSet

    @Test
    public void isSameSet_WhenDifferentSets_ThenFalse()
    {
        // when
        boolean result = testObject.isSameSet(present.get(0), present.get(1));
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void isSameSet_WhenSingleElement_ThenTrue()
    {
        // given
        int element = present.get(0);
        // when
        boolean result = testObject.isSameSet(element, element);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void isSameSet_WhenSameSet_ThenTrue()
    {
        // given
        int element1 = present.get(0);
        int element2 = present.get(1);
        testObject.unionSet(element1, element2);
        // when
        boolean result = testObject.isSameSet(element2, element1);
        // then
        Assertions.assertThat(result).isTrue();
    }

    // endregion
}
