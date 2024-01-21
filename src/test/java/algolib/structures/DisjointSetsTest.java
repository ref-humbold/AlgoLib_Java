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
        testObject = new DisjointSets<>(numbers);
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
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
    // region add & addAll

    @Test
    public void add_WhenEmpty_ThenNewSingletonSet()
    {
        // given
        testObject = new DisjointSets<>();

        Integer element = numbers.get(0);
        // when
        testObject.add(element);
        // then
        Assertions.assertThat(testObject.contains(element)).isTrue();
        Assertions.assertThat(testObject.findSet(element)).isEqualTo(element);
        Assertions.assertThat(testObject.size()).isOne();
    }

    @Test
    public void add_WhenNewElement_ThenNewSingletonSet()
    {
        // given
        Integer element = absent.get(0);
        // when
        testObject.add(element);
        // then
        Assertions.assertThat(testObject.contains(element)).isTrue();
        Assertions.assertThat(testObject.findSet(element)).isEqualTo(element);
        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size() + 1);
    }

    @Test
    public void add_WhenPresentElement_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.add(present.get(0)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void addAll_WhenEmpty_ThenNewSingletonSets()
    {
        // given
        List<Integer> elements = numbers;

        testObject = new DisjointSets<>();
        // when
        testObject.addAll(elements);
        // then
        for(Integer e : elements)
        {
            Assertions.assertThat(testObject.contains(e)).isTrue();
            Assertions.assertThat(testObject.findSet(e)).isEqualTo(e);
        }

        Assertions.assertThat(testObject.size()).isEqualTo(elements.size());
    }

    @Test
    public void addAll_WhenNewElements_ThenNewSingletonSets()
    {
        // given
        List<Integer> elements = absent;
        // when
        testObject.addAll(elements);
        // then
        for(Integer e : elements)
        {
            Assertions.assertThat(testObject.contains(e)).isTrue();
            Assertions.assertThat(testObject.findSet(e)).isEqualTo(e);
        }

        Assertions.assertThat(testObject.size()).isEqualTo(numbers.size() + elements.size());
    }

    @Test
    public void addAll_WhenPresentElements_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.addAll(present));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void addAll_WhenNewAndPresentElements_ThenIllegalArgumentException()
    {
        // given
        List<Integer> elements = Stream.concat(absent.stream(), present.stream()).toList();
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.addAll(elements));
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
        Integer element = present.get(0);
        // when
        Integer result = testObject.findSet(element);
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
    public void findSetOrDefault_WhenPresentElement_ThenRepresent()
    {
        // given
        Integer element = present.get(0);
        Integer defaultValue = absent.get(0);
        // when
        Integer result = testObject.findSetOrDefault(element, defaultValue);
        // then
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void findSetOrDefault_WhenAbsentElement_ThenDefaultValue()
    {
        // given
        Integer element = absent.get(0);
        Integer defaultValue = present.get(0);
        // when
        Integer result = testObject.findSetOrDefault(element, defaultValue);
        // then
        Assertions.assertThat(result).isEqualTo(defaultValue);
    }

    // endregion
    // region unionSet

    @Test
    public void unionSet_WhenDifferentSets_ThenSameRepresent()
    {
        // given
        Integer element1 = present.get(0);
        Integer element2 = present.get(1);
        // when
        testObject.unionSet(element1, element2);
        // then
        Assertions.assertThat(testObject.isSameSet(element1, element2)).isTrue();
        Assertions.assertThat(testObject.findSet(element2)).isEqualTo(testObject.findSet(element1));
    }

    @Test
    public void unionSet_WhenSingleElement_ThenSameRepresent()
    {
        // given
        Integer element = present.get(0);
        // when
        testObject.unionSet(element, element);
        // then
        Assertions.assertThat(testObject.isSameSet(element, element)).isTrue();
        Assertions.assertThat(testObject.findSet(element)).isEqualTo(testObject.findSet(element));
    }

    @Test
    public void unionSet_WhenSameSet_ThenSameRepresent()
    {
        // given
        Integer element1 = present.get(0);
        Integer element2 = present.get(1);
        testObject.unionSet(element1, element2);
        // when
        testObject.unionSet(element2, element1);
        // then
        Assertions.assertThat(testObject.isSameSet(element1, element2)).isTrue();
        Assertions.assertThat(testObject.findSet(element2)).isEqualTo(testObject.findSet(element1));
    }

    @Test
    public void unionSet_WhenNewElementsInChain_ThenSameRepresent()
    {
        // given
        Integer first = present.get(0);
        Integer last = present.get(present.size() - 1);

        // when
        for(int i = 1; i < present.size(); ++i)
            testObject.unionSet(present.get(i - 1), present.get(i));

        // then
        Assertions.assertThat(testObject.isSameSet(first, last)).isTrue();
        Assertions.assertThat(testObject.findSet(last)).isEqualTo(testObject.findSet(first));
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
        Integer element = present.get(0);
        // when
        boolean result = testObject.isSameSet(element, element);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void isSameSet_WhenSameSet_ThenTrue()
    {
        // given
        Integer element1 = present.get(0);
        Integer element2 = present.get(1);
        testObject.unionSet(element1, element2);
        // when
        boolean result = testObject.isSameSet(element2, element1);
        // then
        Assertions.assertThat(result).isTrue();
    }

    // endregion
}
