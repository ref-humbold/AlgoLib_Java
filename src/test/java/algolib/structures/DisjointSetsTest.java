package algolib.structures;

import java.util.List;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of disjoint sets (union-find).
public class DisjointSetsTest
{
    private DisjointSets<Integer> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DisjointSets<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        // given
        testObject = new DisjointSets<>();
        // when
        boolean result = testObject.isEmpty();
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
    public void size_WhenElements_ThenSetsCount()
    {
        // when
        int result = testObject.size();
        // then
        Assertions.assertThat(result).isEqualTo(10);
    }

    @Test
    public void contains_WhenPresentElement_ThenTrue()
    {
        // when
        boolean result = testObject.contains(4);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void contains_WhenAbsentElement_ThenFalse()
    {
        // when
        boolean result = testObject.contains(20);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void add_WhenNewElement_ThenNewSingletonSet()
    {
        // given
        Integer element = 24;
        // when
        testObject.add(element);
        // then
        Assertions.assertThat(testObject.contains(element)).isTrue();
        Assertions.assertThat(testObject.findSet(element)).isEqualTo(element);
    }

    @Test
    public void add_WhenPresentElement_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.add(5));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void addAll_WhenNewElements_ThenNewSingletonSets()
    {
        // given
        List<Integer> elements = List.of(20, 17, 35);
        // when
        testObject.addAll(elements);
        // then
        for(Integer e : elements)
        {
            Assertions.assertThat(testObject.contains(e)).isTrue();
            Assertions.assertThat(testObject.findSet(e)).isEqualTo(e);
        }
    }

    @Test
    public void addAll_WhenPresentElement_ThenIllegalArgumentException()
    {
        // given
        List<Integer> elements = List.of(20, 7, 35);
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.addAll(elements));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findSet_WhenPresentElement_ThenRepresent()
    {
        // given
        Integer element = 4;
        // when
        Integer result = testObject.findSet(element);
        // then
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void findSet_WhenAbsentElement_ThenNoSuchElementException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.findSet(14));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findSetOrDefault_WhenPresentElement_ThenRepresent()
    {
        // given
        Integer element = 4;
        // when
        Integer result = testObject.findSetOrDefault(element, 10);
        // then
        Assertions.assertThat(result).isEqualTo(element);
    }

    @Test
    public void findSetOrDefault_WhenAbsentElement_ThenDefaultValue()
    {
        // given
        Integer defaultValue = 10;
        // when
        Integer result = testObject.findSetOrDefault(22, defaultValue);
        // then
        Assertions.assertThat(result).isEqualTo(defaultValue);
    }

    @Test
    public void unionSet_WhenDifferentSets_ThenSameRepresent()
    {
        // given
        Integer element1 = 4;
        Integer element2 = 6;
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
        Integer element = 4;
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
        Integer element1 = 3;
        Integer element2 = 8;
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
        List<Integer> elements = List.of(20, 17, 35);
        // when
        testObject.addAll(elements)
                  .unionSet(elements.get(0), elements.get(1))
                  .unionSet(elements.get(1), elements.get(2));
        // then
        Assertions.assertThat(testObject.isSameSet(elements.get(0), elements.get(2))).isTrue();
        Assertions.assertThat(testObject.findSet(elements.get(2)))
                  .isEqualTo(testObject.findSet(elements.get(0)));
    }

    @Test
    public void isSameSet_WhenDifferentSets_ThenFalse()
    {
        // given
        Integer element1 = 4;
        Integer element2 = 6;
        // when
        boolean result = testObject.isSameSet(element1, element2);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void isSameSet_WhenSingleElement_ThenTrue()
    {
        // given
        Integer element = 4;
        // when
        boolean result = testObject.isSameSet(element, element);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void isSameSet_WhenSameSet_ThenTrue()
    {
        // given
        Integer element1 = 3;
        Integer element2 = 8;
        testObject.unionSet(element1, element2);
        // when
        boolean result = testObject.isSameSet(element2, element1);
        // then
        Assertions.assertThat(result).isTrue();
    }
}
