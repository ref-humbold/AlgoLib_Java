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
    public void toString_WhenNotEmpty_ThenTextRepresentation()
    {
        String result = testObject.toString();

        Assert.assertEquals("{|2, 6, 10, 14, 18, 24, 26, 30, 37, 45, 51, 68, 71, 97|}", result);
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        testObject = new AVLTree<>();

        boolean result = testObject.isEmpty();

        Assert.assertTrue(result);
    }

    @Test
    public void isEmpty_WhenNotEmpty_ThenFalse()
    {
        boolean result = testObject.isEmpty();

        Assert.assertFalse(result);
    }

    @Test
    public void size_WhenEmpty_ThenZero()
    {
        testObject = new AVLTree<>();

        int result = testObject.size();

        Assert.assertEquals(0, result);
    }

    @Test
    public void size_WhenNotEmpty_ThenNumberOfElements()
    {
        int result = testObject.size();

        Assert.assertEquals(14, result);
    }

    @Test
    public void contains_WhenPresentElement_ThenTrue()
    {
        for(Integer i : numbers)
        {
            boolean result = testObject.contains(i);

            Assert.assertTrue(result);
        }
    }

    @Test
    public void contains_WhenAbsentElement_ThenFalse()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            boolean result = testObject.contains(i);

            Assert.assertFalse(result);
        }
    }

    @Test
    public void iterator()
    {
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> iterator = testObject.iterator();

        while(iterator.hasNext())
            result.add(iterator.next());

        Arrays.sort(numbers);

        Assert.assertArrayEquals(numbers, result.toArray());
    }

    @Test
    public void descendingIterator()
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
    public void add_WhenNewElement_ThenTrue()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            boolean result = testObject.add(i);

            Assert.assertTrue(result);
            Assert.assertTrue(testObject.contains(i));
        }
    }

    @Test
    public void add_WhenPresentElement_ThenFalse()
    {
        for(Integer i : new Integer[]{14, 24, 30, 45})
        {
            boolean result = testObject.add(i);

            Assert.assertFalse(result);
            Assert.assertTrue(testObject.contains(i));
        }
    }

    @Test
    public void remove_WhenPresentElement_ThenTrue()
    {
        for(Integer i : new Integer[]{14, 24, 30, 45})
        {
            boolean result = testObject.remove(i);

            Assert.assertTrue(result);
            Assert.assertFalse(testObject.contains(i));
        }
    }

    @Test
    public void removeRoot_WhenTwoElements1()
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
    public void removeRoot_WhenTwoElements2()
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
    public void removeRoot_WhenOneElement()
    {
        int root = 0;

        testObject = new AVLTree<>(Collections.singletonList(root));

        boolean result = testObject.remove(root);

        Assert.assertTrue(result);
        Assert.assertFalse(testObject.contains(root));
        Assert.assertTrue(testObject.isEmpty());
    }

    @Test
    public void remove_WhenEmpty_ThenFalse()
    {
        testObject = new AVLTree<>();

        boolean result = testObject.remove(0);

        Assert.assertFalse(result);
        Assert.assertTrue(testObject.isEmpty());
    }

    @Test
    public void remove_WhenAbsentElement_ThenFalse()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            boolean result = testObject.remove(i);

            Assert.assertFalse(result);
            Assert.assertFalse(testObject.contains(i));
        }
    }

    @Test
    public void clear_ThenTrue()
    {
        testObject.clear();

        Assert.assertTrue(testObject.isEmpty());
        Assert.assertEquals(0, testObject.size());
    }
}
