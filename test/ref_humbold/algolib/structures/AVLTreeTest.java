package ref_humbold.algolib.structures;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AVLTreeTest
{
    private AVLTree<Integer> testObject;

    @Before
    public void setUp()
    {
        testObject = new AVLTree<>();
    }

    @After
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void testToString()
    {
        testObject = new AVLTree<>(Arrays.asList(10, 6, 14, 37, 2, 30, 45, 18, 68));

        String result = testObject.toString();

        Assert.assertEquals("{|2, 6, 10, 14, 18, 30, 37, 45, 68|}", result);
    }

    @Test
    public void testIsEmptyWhenEmpty()
    {
        Assert.assertTrue(testObject.isEmpty());
    }

    @Test
    public void testIsEmptyWhenNonEmpty()
    {
        testObject.add(15);
        testObject.add(76);

        Assert.assertFalse(testObject.isEmpty());
    }

    @Test
    public void testSizeWhenEmpty()
    {
        Assert.assertEquals(0, testObject.size());
    }

    @Test
    public void testSizeWhenAdding()
    {
        testObject = new AVLTree<>(Arrays.asList(10, 6, 14, 37, 2, 30, 45, 18, 68));

        Assert.assertEquals(9, testObject.size());
    }

    @Test
    public void testSizeWhenRemoving()
    {
        testObject = new AVLTree<>(Arrays.asList(10, 6, 14, 37, 2, 30, 45, 18, 68));

        testObject.remove(30);
        testObject.remove(2);

        Assert.assertEquals(7, testObject.size());
    }

    @Test
    public void testContainsWhenInside()
    {
        Integer[] numbers = new Integer[]{10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 71, 68, 26};

        testObject = new AVLTree<>(Arrays.asList(numbers));

        for(Integer i : numbers)
            Assert.assertTrue(testObject.contains(i));
    }

    @Test
    public void testContainsWhenNotInside()
    {
        testObject = new AVLTree<>(Arrays.asList(10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 71, 68, 26));

        Assert.assertFalse(testObject.contains(40));
    }

    public void testIterator()
    {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testAdd()
    {
        Integer[] numbers = new Integer[]{10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 71, 68, 26};

        for(Integer i : numbers)
        {
            boolean added = testObject.add(i);

            Assert.assertTrue(added);
            Assert.assertTrue(testObject.contains(i));
        }
    }

    @Test
    public void testAddWhenRepeated()
    {
        testObject = new AVLTree<>(Arrays.asList(10, 6, 14, 37, 2, 30, 45, 18, 68));

        for(Integer i : new Integer[]{14, 30, 45})
        {
            boolean added = testObject.add(i);

            Assert.assertFalse(added);
            Assert.assertTrue(testObject.contains(i));
        }
    }

    @Test
    public void testRemove()
    {
        testObject = new AVLTree<>(Arrays.asList(10, 6, 14, 37, 2, 30, 45, 18, 68));

        boolean result = testObject.remove(30);

        Assert.assertTrue(result);
        Assert.assertFalse(testObject.contains(30));
    }

    @Test
    public void testRemoveWhenNotInside()
    {
        testObject = new AVLTree<>(Arrays.asList(10, 6, 14, 37, 2, 30, 45, 18, 68));

        boolean result = testObject.remove(40);

        Assert.assertFalse(result);
    }

    @Test
    public void testClear()
    {
        testObject = new AVLTree<>(Arrays.asList(10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 71, 68, 26));
        testObject.clear();

        Assert.assertTrue(testObject.isEmpty());
        Assert.assertEquals(0, testObject.size());
    }
}
