// Tests: Structure of AVL tree
package algolib.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AVLTreeTest
{
    private final Integer[] numbers =
            new Integer[]{10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26};
    private AVLTree<Integer> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new AVLTree<>(Arrays.asList(numbers));
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void toString_WhenNotEmpty_ThenTextRepresentation()
    {
        // when
        String result = testObject.toString();
        // then
        Assertions.assertThat(result)
                  .isEqualTo("{|2, 6, 10, 14, 18, 24, 26, 30, 37, 45, 51, 68, 71, 97|}");
    }

    @Test
    public void size_WhenEmpty_ThenZero()
    {
        // given
        testObject = new AVLTree<>();
        // when
        int result = testObject.size();
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void size_WhenNotEmpty_ThenNumberOfElements()
    {
        // when
        int result = testObject.size();
        // then
        Assertions.assertThat(result).isEqualTo(numbers.length);
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        // given
        testObject = new AVLTree<>();
        // when
        boolean result = testObject.isEmpty();
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void isEmpty_WhenNotEmpty_ThenFalse()
    {
        // when
        boolean result = testObject.isEmpty();
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void contains_WhenPresentElement_ThenTrue()
    {
        for(Integer i : numbers)
        {
            // when
            boolean result = testObject.contains(i);
            // then
            Assertions.assertThat(result).isTrue();
        }
    }

    @Test
    public void contains_WhenAbsentElement_ThenFalse()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            // when
            boolean result = testObject.contains(i);
            // then
            Assertions.assertThat(result).isFalse();
        }
    }

    @Test
    public void iterator_WhenNotEmpty_ThenSortedElements()
    {
        // when
        List<Integer> result = new ArrayList<>(testObject);
        // then
        Assertions.assertThat(result).isSorted();
        Assertions.assertThat(result).containsExactlyInAnyOrder(numbers);
    }

    @Test
    public void descendingIterator_WhenNotEmpty_ThenReverseSortedElements()
    {
        // given
        Iterator<Integer> iterator = testObject.descendingIterator();
        // when
        List<Integer> result = new ArrayList<>();

        while(iterator.hasNext())
            result.add(iterator.next());
        // then
        Assertions.assertThat(result).isSortedAccordingTo((n, m) -> m.compareTo(n));
        Assertions.assertThat(result).containsExactlyInAnyOrder(numbers);
    }

    @Test
    public void add_WhenNewElement_ThenTrue()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            // when
            boolean result = testObject.add(i);
            // then
            Assertions.assertThat(result).isTrue();
            Assertions.assertThat(testObject).contains(i);
        }
    }

    @Test
    public void add_WhenPresentElement_ThenFalse()
    {
        for(Integer i : new Integer[]{14, 24, 30, 45})
        {
            // when
            boolean result = testObject.add(i);
            // then
            Assertions.assertThat(result).isFalse();
            Assertions.assertThat(testObject).contains(i);
        }
    }

    @Test
    public void remove_WhenPresentElement_ThenTrue()
    {
        for(Integer i : new Integer[]{14, 24, 30, 45})
        {
            // when
            boolean result = testObject.remove(i);
            // then
            Assertions.assertThat(result).isTrue();
            Assertions.assertThat(testObject).doesNotContain(i);
        }
    }

    @Test
    public void removeRoot_WhenTwoElements1()
    {
        // given
        int root = 27;
        int elem = 11;

        testObject = new AVLTree<>(Arrays.asList(root, elem));
        // when
        boolean result = testObject.remove(root);
        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject).doesNotContain(root);
        Assertions.assertThat(testObject).contains(elem);
    }

    @Test
    public void removeRoot_WhenTwoElements2()
    {
        // given
        int root = 11;
        int elem = 27;

        testObject = new AVLTree<>(Arrays.asList(root, elem));
        // when
        boolean result = testObject.remove(root);
        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject).doesNotContain(root);
        Assertions.assertThat(testObject).contains(elem);
    }

    @Test
    public void removeRoot_WhenOneElement()
    {
        // given
        int root = 0;

        testObject = new AVLTree<>(Collections.singletonList(root));
        // when
        boolean result = testObject.remove(root);
        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject).doesNotContain(root);
        Assertions.assertThat(testObject).isEmpty();
    }

    @Test
    public void remove_WhenEmpty_ThenFalse()
    {
        // given
        testObject = new AVLTree<>();
        // when
        boolean result = testObject.remove(0);
        // then
        Assertions.assertThat(result).isFalse();
        Assertions.assertThat(testObject).isEmpty();
    }

    @Test
    public void remove_WhenAbsentElement_ThenFalse()
    {
        for(Integer i : new Integer[]{111, 140, 187})
        {
            // when
            boolean result = testObject.remove(i);
            // then
            Assertions.assertThat(result).isFalse();
            Assertions.assertThat(testObject).doesNotContain(i);
        }
    }

    @Test
    public void clear_ThenEmpty()
    {
        // when
        testObject.clear();
        // then
        Assertions.assertThat(testObject).isEmpty();
    }
}
