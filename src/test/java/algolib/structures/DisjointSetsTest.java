package algolib.structures;

import java.util.List;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Disjoint sets structure (union-find)
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
    public void contains_WhenPresentElement()
    {
        // when
        boolean result = testObject.contains(4);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void contains_WhenAbsentElement()
    {
        // when
        boolean result = testObject.contains(20);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void add_WhenNewElement()
    {
        // given
        Integer elem = 24;
        // when
        testObject.add(elem);
        // then
        Assertions.assertThat(testObject.contains(elem)).isTrue();
        Assertions.assertThat(testObject.findSet(elem)).isEqualTo(elem);
    }

    @Test
    public void add_WhenPresentElement()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.add(5));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void addAll_WhenNewElements()
    {
        // given
        List<Integer> elems = List.of(20, 17, 35);
        // when
        testObject.addAll(elems);
        // then
        for(Integer e : elems)
        {
            Assertions.assertThat(testObject.contains(e)).isTrue();
            Assertions.assertThat(testObject.findSet(e)).isEqualTo(e);
        }
    }

    @Test
    public void addAll_WhenPresentElement()
    {
        // given
        List<Integer> elems = List.of(20, 7, 35);
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.addAll(elems));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findSet_WhenPresentElement()
    {
        // given
        Integer elem = 4;
        // when
        Integer result = testObject.findSet(elem);
        // then
        Assertions.assertThat(result).isEqualTo(elem);
    }

    @Test
    public void findSet_WhenAbsentElement()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.findSet(14));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findSetOrDefault_WhenPresentElement()
    {
        // given
        Integer elem = 4;
        // when
        Integer result = testObject.findSetOrDefault(elem, 10);
        // then
        Assertions.assertThat(result).isEqualTo(elem);
    }

    @Test
    public void findSetOrDefault_WhenAbsentElement()
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
        Integer elem1 = 4;
        Integer elem2 = 6;
        // when
        testObject.unionSet(elem1, elem2);
        // then
        Assertions.assertThat(testObject.isSameSet(elem1, elem2)).isTrue();
        Assertions.assertThat(testObject.findSet(elem2)).isEqualTo(testObject.findSet(elem1));
    }

    @Test
    public void unionSet_WhenSingleElement_ThenSameRepresent()
    {
        // given
        Integer elem = 4;
        // when
        testObject.unionSet(elem, elem);
        // then
        Assertions.assertThat(testObject.isSameSet(elem, elem)).isTrue();
        Assertions.assertThat(testObject.findSet(elem)).isEqualTo(testObject.findSet(elem));
    }

    @Test
    public void unionSet_WhenSameSet_ThenSameRepresent()
    {
        // given
        Integer elem1 = 3;
        Integer elem2 = 8;
        testObject.unionSet(elem1, elem2);
        // when
        testObject.unionSet(elem2, elem1);
        // then
        Assertions.assertThat(testObject.isSameSet(elem1, elem2)).isTrue();
        Assertions.assertThat(testObject.findSet(elem2)).isEqualTo(testObject.findSet(elem1));
    }

    @Test
    public void unionSet_WhenNewElementsInChain_ThenSameRepresent()
    {
        // given
        List<Integer> elems = List.of(20, 17, 35);
        // when
        testObject.addAll(elems)
                  .unionSet(elems.get(0), elems.get(1))
                  .unionSet(elems.get(1), elems.get(2));
        // then
        Assertions.assertThat(testObject.isSameSet(elems.get(0), elems.get(2))).isTrue();
        Assertions.assertThat(testObject.findSet(elems.get(2)))
                  .isEqualTo(testObject.findSet(elems.get(0)));
    }

    @Test
    public void isSameSet_WhenDifferentSets_ThenFalse()
    {
        // given
        Integer elem1 = 4;
        Integer elem2 = 6;
        // when
        boolean result = testObject.isSameSet(elem1, elem2);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void isSameSet_WhenSingleElement_ThenTrue()
    {
        // given
        Integer elem = 4;
        // when
        boolean result = testObject.isSameSet(elem, elem);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void isSameSet_WhenSameSet_ThenTrue()
    {
        // given
        Integer elem1 = 3;
        Integer elem2 = 8;
        testObject.unionSet(elem1, elem2);
        // when
        boolean result = testObject.isSameSet(elem2, elem1);
        // then
        Assertions.assertThat(result).isTrue();
    }
}
