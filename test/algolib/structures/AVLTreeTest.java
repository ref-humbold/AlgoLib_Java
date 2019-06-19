// TESTY DLA DRZEWA AVL
package algolib.structures;

import java.util.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AVLTreeTest
{
    private AVLTree<Integer> testObject;
    private Integer[] numbers = new Integer[]{10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26};

    @Before
    public void setUp()
    {
        testObject = new AVLTree<>(Comparator.naturalOrder());
        testObject.addAll(Arrays.asList(numbers));
    }

    @After
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void testToString()
    {
        String result = testObject.toString();

        Assert.assertEquals("{|2, 6, 10, 14, 18, 24, 26, 30, 37, 45, 51, 68, 71, 97|}", result);
    }

    @Test
    public void testIsEmptyWhenEmpty()
    {
        testObject = new AVLTree<>();

        boolean result = testObject.isEmpty();

        Assert.assertTrue(result);
    }

    @Test
    public void testIsEmptyWhenNotEmpty()
    {
        boolean result = testObject.isEmpty();

        Assert.assertFalse(result);
    }

    @Test
    public void testSizeWhenEmpty()
    {
        testObject = new AVLTree<>();

        int result = testObject.size();

        Assert.assertEquals(0, result);
    }

    @Test
    public void testSizeWhenNotEmpty()
    {
        int result = testObject.size();

        Assert.assertEquals(14, result);
    }

    @Test
    public void testContainsWhenPresentElement()
    {
        for(Integer i : numbers)
        {
            boolean result = testObject.contains(i);

            Assert.assertTrue(result);
        }
    }

    @Test
    public void testContainsWhenOuterElement()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            boolean result = testObject.contains(i);

            Assert.assertFalse(result);
        }
    }

    @Test
    public void testIterator()
    {
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> iterator = testObject.iterator();

        while(iterator.hasNext())
            result.add(iterator.next());

        Arrays.sort(numbers);

        Assert.assertArrayEquals(numbers, result.toArray());
    }

    @Test
    public void testDescendingIterator()
    {
        List<Integer> result = new ArrayList<>();
        List<Integer> revNumbers = Arrays.asList(numbers);
        Iterator<Integer> iterator = testObject.descendingIterator();

        while(iterator.hasNext())
            result.add(iterator.next());

        Collections.sort(revNumbers);
        Collections.reverse(revNumbers);

        Assert.assertArrayEquals(revNumbers.toArray(), result.toArray());
    }

    @Test
    public void testAddWhenNewElement()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            boolean result = testObject.add(i);

            Assert.assertTrue(result);
            Assert.assertTrue(testObject.contains(i));
        }
    }

    @Test
    public void testAddWhenPresentElement()
    {
        for(Integer i : new Integer[]{14, 24, 30, 45})
        {
            boolean result = testObject.add(i);

            Assert.assertFalse(result);
            Assert.assertTrue(testObject.contains(i));
        }
    }

    @Test
    public void testRemoveWhenPresentElement()
    {
        for(Integer i : new Integer[]{14, 24, 30, 45})
        {
            boolean result = testObject.remove(i);

            Assert.assertTrue(result);
            Assert.assertFalse(testObject.contains(i));
        }
    }

    @Test
    public void testRemoveRootWhenTwoElements1()
    {
        int root = 27;
        int elem = 11;

        testObject = new AVLTree<>(Arrays.asList(root, elem));

        boolean result = testObject.remove(root);

        Assert.assertTrue(result);
        Assert.assertFalse(testObject.contains(root));
        Assert.assertTrue(testObject.contains(elem));
    }

    @Test
    public void testRemoveRootWhenTwoElements2()
    {
        int root = 11;
        int elem = 27;

        testObject = new AVLTree<>(Arrays.asList(root, elem));

        boolean result = testObject.remove(root);

        Assert.assertTrue(result);
        Assert.assertFalse(testObject.contains(root));
        Assert.assertTrue(testObject.contains(elem));
    }

    @Test
    public void testRemoveRootWhenOneElement()
    {
        int root = 0;

        testObject = new AVLTree<>(Collections.singletonList(root));

        boolean result = testObject.remove(root);

        Assert.assertTrue(result);
        Assert.assertFalse(testObject.contains(root));
        Assert.assertTrue(testObject.isEmpty());
    }

    @Test
    public void testRemoveWhenEmpty()
    {
        testObject = new AVLTree<>();

        boolean result = testObject.remove(0);

        Assert.assertFalse(result);
        Assert.assertTrue(testObject.isEmpty());
    }

    @Test
    public void testRemoveWhenOuterElement()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            boolean result = testObject.remove(i);

            Assert.assertFalse(result);
            Assert.assertFalse(testObject.contains(i));
        }
    }

    @Test
    public void testClear()
    {
        testObject.clear();

        Assert.assertTrue(testObject.isEmpty());
        Assert.assertEquals(0, testObject.size());
    }
}
