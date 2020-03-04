// TESTY DLA STRUKTURY ZBIORÓW ROZŁĄCZNYCH
package algolib.structures;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void size()
    {
        int result = testObject.size();

        Assertions.assertEquals(10, result);
    }

    @Test
    public void contains_WhenContains()
    {
        boolean result = testObject.contains(4);

        Assertions.assertTrue(result);
    }

    @Test
    public void contains_WhenNotContains()
    {
        boolean result = testObject.contains(20);

        Assertions.assertFalse(result);
    }

    @Test
    public void add_WhenNewElements()
    {
        List<Integer> elems = List.of(20, 17, 35);

        testObject.add(elems);

        for(Integer e : elems)
        {
            Assertions.assertTrue(testObject.contains(e));
            Assertions.assertEquals(e, testObject.findSet(e));
        }
    }

    @Test
    public void add_WhenPresentElement()
    {
        List<Integer> elems = List.of(20, 7, 35);

        Assertions.assertThrows(IllegalArgumentException.class, () -> testObject.add(elems));
    }

    @Test
    public void findSet_WhenPresentElement()
    {
        Integer elem = 4;

        Integer result = testObject.findSet(elem);

        Assertions.assertEquals(elem, result);
    }

    @Test
    public void findSet_WhenAbsentElement()
    {
        Assertions.assertThrows(NoSuchElementException.class, () -> testObject.findSet(14));
    }

    @Test
    public void findSetOrDefault_WhenPresentElement()
    {
        Integer elem = 4;

        Integer result = testObject.findSetOrDefault(elem, 10);

        Assertions.assertEquals(elem, result);
    }

    @Test
    public void findSetOrDefault_WhenAbsentElement()
    {
        Integer def = 10;

        Integer result = testObject.findSetOrDefault(22, def);

        Assertions.assertEquals(def, result);
    }

    @Test
    public void unionSet_WhenDifferentSets_ThenSameRepresent()
    {
        Integer elem1 = 4;
        Integer elem2 = 6;

        testObject.unionSet(elem1, elem2);

        Assertions.assertTrue(testObject.isSameSet(elem1, elem2));
        Assertions.assertEquals(testObject.findSet(elem1), testObject.findSet(elem2));
    }

    @Test
    public void unionSet_WhenSingleElement_ThenSameRepresent()
    {
        Integer elem = 4;

        testObject.unionSet(elem, elem);

        Assertions.assertTrue(testObject.isSameSet(elem, elem));
        Assertions.assertEquals(testObject.findSet(elem), testObject.findSet(elem));
    }

    @Test
    public void unionSet_WhenSameSet_ThenSameRepresent()
    {
        Integer elem1 = 3;
        Integer elem2 = 8;
        testObject.unionSet(elem1, elem2);

        testObject.unionSet(elem2, elem1);

        Assertions.assertTrue(testObject.isSameSet(elem1, elem2));
        Assertions.assertEquals(testObject.findSet(elem1), testObject.findSet(elem2));
    }

    @Test
    public void unionSet_WhenNewElementsInChain_ThenSameRepresent()
    {
        List<Integer> elems = List.of(20, 17, 35);

        testObject.add(elems)
                  .unionSet(elems.get(0), elems.get(1))
                  .unionSet(elems.get(1), elems.get(2));

        Assertions.assertTrue(testObject.isSameSet(elems.get(0), elems.get(2)));
        Assertions.assertEquals(testObject.findSet(elems.get(0)), testObject.findSet(elems.get(2)));
    }

    @Test
    public void isSameSet_WhenDifferentSets_ThenFalse()
    {
        Integer elem1 = 4;
        Integer elem2 = 6;

        boolean result = testObject.isSameSet(elem1, elem2);

        Assertions.assertFalse(result);
    }

    @Test
    public void isSameSet_WhenSingleElement_ThenTrue()
    {
        Integer elem = 4;

        boolean result = testObject.isSameSet(elem, elem);

        Assertions.assertTrue(result);
    }

    @Test
    public void isSameSet_WhenSameSet_ThenTrue()
    {
        Integer elem1 = 3;
        Integer elem2 = 8;
        testObject.unionSet(elem1, elem2);

        boolean result = testObject.isSameSet(elem2, elem1);

        Assertions.assertTrue(result);
    }
}
