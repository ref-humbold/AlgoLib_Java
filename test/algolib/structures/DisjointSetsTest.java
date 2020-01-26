// TESTY DLA STRUKTURY ZBIORÓW ROZŁĄCZNYCH
package algolib.structures;

import java.util.Arrays;
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
        testObject = new DisjointSets<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
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

        Assertions.assertEquals(9, result);
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
    public void addElem_WhenNewElement()
    {
        Integer elem = 20;

        testObject.addElem(elem);

        Assertions.assertTrue(testObject.contains(elem));
        Assertions.assertEquals(elem, testObject.findSet(elem));
    }

    @Test
    public void addElem_WhenPresentElement()
    {
        Integer elem = 7;

        Assertions.assertThrows(IllegalArgumentException.class, () -> testObject.addElem(elem));
    }

    @Test
    public void findSet()
    {
        Integer elem = 4;

        Integer result = testObject.findSet(elem);

        Assertions.assertEquals(elem, result);
    }

    @Test
    public void unionSet_WhenDifferentSets()
    {
        Integer elem1 = 4;
        Integer elem2 = 6;

        testObject.unionSet(elem1, elem2);

        Assertions.assertTrue(testObject.isSameSet(elem1, elem2));
        Assertions.assertEquals(testObject.findSet(elem1), testObject.findSet(elem2));
    }

    @Test
    public void unionSet_WhenSameSets1()
    {
        Integer elem = 4;

        testObject.unionSet(elem, elem);

        Assertions.assertTrue(testObject.isSameSet(elem, elem));
        Assertions.assertEquals(testObject.findSet(elem), testObject.findSet(elem));
    }

    @Test
    public void unionSet_WhenSameSets2()
    {
        Integer elem1 = 3;
        Integer elem2 = 8;
        testObject.unionSet(elem1, elem2);

        testObject.unionSet(elem2, elem1);

        Assertions.assertTrue(testObject.isSameSet(elem1, elem2));
        Assertions.assertEquals(testObject.findSet(elem1), testObject.findSet(elem2));
    }

    @Test
    public void isSameSet_WhenDifferentSets()
    {
        Integer elem1 = 4;
        Integer elem2 = 6;

        boolean result = testObject.isSameSet(elem1, elem2);

        Assertions.assertFalse(result);
    }

    @Test
    public void isSameSet_WhenSameSets1()
    {
        Integer elem = 4;

        boolean result = testObject.isSameSet(elem, elem);

        Assertions.assertTrue(result);
    }

    @Test
    public void isSameSet_WhenSameSets2()
    {
        Integer elem1 = 3;
        Integer elem2 = 8;
        testObject.unionSet(elem1, elem2);

        boolean result = testObject.isSameSet(elem2, elem1);

        Assertions.assertTrue(result);
    }
}
