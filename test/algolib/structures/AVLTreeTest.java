// TESTY DLA DRZEWA AVL
package algolib.structures;

import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AVLTreeTest
{
    private AVLTree<Integer> testObject;
    private Integer[] numbers = new Integer[]{10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26};

    @BeforeEach
    public void setUp()
    {
        testObject = new AVLTree<>(Comparator.naturalOrder());
        testObject.addAll(Arrays.asList(numbers));
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void toString_WhenNotEmpty_ThenTextRepresentation()
    {
        String result = testObject.toString();

        Assertions.assertEquals("{|2, 6, 10, 14, 18, 24, 26, 30, 37, 45, 51, 68, 71, 97|}", result);
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        testObject = new AVLTree<>();

        boolean result = testObject.isEmpty();

        Assertions.assertTrue(result);
    }

    @Test
    public void isEmpty_WhenNotEmpty_ThenFalse()
    {
        boolean result = testObject.isEmpty();

        Assertions.assertFalse(result);
    }

    @Test
    public void size_WhenEmpty_ThenZero()
    {
        testObject = new AVLTree<>();

        int result = testObject.size();

        Assertions.assertEquals(0, result);
    }

    @Test
    public void size_WhenNotEmpty_ThenNumberOfElements()
    {
        int result = testObject.size();

        Assertions.assertEquals(14, result);
    }

    @Test
    public void contains_WhenPresentElement_ThenTrue()
    {
        for(Integer i : numbers)
        {
            boolean result = testObject.contains(i);

            Assertions.assertTrue(result);
        }
    }

    @Test
    public void contains_WhenAbsentElement_ThenFalse()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            boolean result = testObject.contains(i);

            Assertions.assertFalse(result);
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

        Assertions.assertArrayEquals(numbers, result.toArray());
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

        Assertions.assertArrayEquals(revNumbers.toArray(), result.toArray());
    }

    @Test
    public void add_WhenNewElement_ThenTrue()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            boolean result = testObject.add(i);

            Assertions.assertTrue(result);
            Assertions.assertTrue(testObject.contains(i));
        }
    }

    @Test
    public void add_WhenPresentElement_ThenFalse()
    {
        for(Integer i : new Integer[]{14, 24, 30, 45})
        {
            boolean result = testObject.add(i);

            Assertions.assertFalse(result);
            Assertions.assertTrue(testObject.contains(i));
        }
    }

    @Test
    public void remove_WhenPresentElement_ThenTrue()
    {
        for(Integer i : new Integer[]{14, 24, 30, 45})
        {
            boolean result = testObject.remove(i);

            Assertions.assertTrue(result);
            Assertions.assertFalse(testObject.contains(i));
        }
    }

    @Test
    public void removeRoot_WhenTwoElements1()
    {
        int root = 27;
        int elem = 11;

        testObject = new AVLTree<>(Arrays.asList(root, elem));

        boolean result = testObject.remove(root);

        Assertions.assertTrue(result);
        Assertions.assertFalse(testObject.contains(root));
        Assertions.assertTrue(testObject.contains(elem));
    }

    @Test
    public void removeRoot_WhenTwoElements2()
    {
        int root = 11;
        int elem = 27;

        testObject = new AVLTree<>(Arrays.asList(root, elem));

        boolean result = testObject.remove(root);

        Assertions.assertTrue(result);
        Assertions.assertFalse(testObject.contains(root));
        Assertions.assertTrue(testObject.contains(elem));
    }

    @Test
    public void removeRoot_WhenOneElement()
    {
        int root = 0;

        testObject = new AVLTree<>(Collections.singletonList(root));

        boolean result = testObject.remove(root);

        Assertions.assertTrue(result);
        Assertions.assertFalse(testObject.contains(root));
        Assertions.assertTrue(testObject.isEmpty());
    }

    @Test
    public void remove_WhenEmpty_ThenFalse()
    {
        testObject = new AVLTree<>();

        boolean result = testObject.remove(0);

        Assertions.assertFalse(result);
        Assertions.assertTrue(testObject.isEmpty());
    }

    @Test
    public void remove_WhenAbsentElement_ThenFalse()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            boolean result = testObject.remove(i);

            Assertions.assertFalse(result);
            Assertions.assertFalse(testObject.contains(i));
        }
    }

    @Test
    public void clear_ThenTrue()
    {
        testObject.clear();

        Assertions.assertEquals(0, testObject.size());
    }
}
