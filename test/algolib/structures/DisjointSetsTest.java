// TESTY DLA STRUKTURY ZBIORÓW ROZŁĄCZNYCH
package algolib.structures;

import java.util.Arrays;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DisjointSetsTest
{
    private DisjointSets<Integer> testObject;

    @Before
    public void setUp()
    {
        testObject = new DisjointSets<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @After
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void testSize()
    {
        int result = testObject.size();

        Assert.assertEquals(9, result);
    }

    @Test
    public void testContainsWhenContains()
    {
        boolean result = testObject.contains(4);

        Assert.assertTrue(result);
    }

    @Test
    public void testContainsWhenNotContains()
    {
        boolean result = testObject.contains(20);

        Assert.assertFalse(result);
    }

    @Test
    public void testAddElemWhenNewElement()
    {
        Integer elem = 20;

        testObject.addElem(elem);

        Assert.assertTrue(testObject.contains(elem));
        Assert.assertEquals(elem, testObject.findSet(elem));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddElemWhenPresentElement()
    {
        Integer elem = 7;

        testObject.addElem(elem);
    }

    @Test
    public void testFindSet()
    {
        Integer elem = 4;

        Integer result = testObject.findSet(elem);

        Assert.assertEquals(elem, result);
    }

    @Test
    public void testUnionSetWhenDifferentSets()
    {
        Integer elem1 = 4;
        Integer elem2 = 6;

        testObject.unionSet(elem1, elem2);

        Assert.assertTrue(testObject.isSameSet(elem1, elem2));
        Assert.assertEquals(testObject.findSet(elem1), testObject.findSet(elem2));
    }

    @Test
    public void testUnionSetWhenSameSets1()
    {
        Integer elem = 4;

        testObject.unionSet(elem, elem);

        Assert.assertTrue(testObject.isSameSet(elem, elem));
        Assert.assertEquals(testObject.findSet(elem), testObject.findSet(elem));
    }

    @Test
    public void testUnionSetWhenSameSets2()
    {
        Integer elem1 = 3;
        Integer elem2 = 8;
        testObject.unionSet(elem1, elem2);

        testObject.unionSet(elem2, elem1);

        Assert.assertTrue(testObject.isSameSet(elem1, elem2));
        Assert.assertEquals(testObject.findSet(elem1), testObject.findSet(elem2));
    }

    @Test
    public void testIsSameSetWhenDifferentSets()
    {
        Integer elem1 = 4;
        Integer elem2 = 6;

        boolean result = testObject.isSameSet(elem1, elem2);

        Assert.assertFalse(result);
    }

    @Test
    public void testIsSameSetWhenSameSets1()
    {
        Integer elem = 4;

        boolean result = testObject.isSameSet(elem, elem);

        Assert.assertTrue(result);
    }

    @Test
    public void testIsSameSetWhenSameSets2()
    {
        Integer elem1 = 3;
        Integer elem2 = 8;
        testObject.unionSet(elem1, elem2);

        boolean result = testObject.isSameSet(elem2, elem1);

        Assert.assertTrue(result);
    }
}
