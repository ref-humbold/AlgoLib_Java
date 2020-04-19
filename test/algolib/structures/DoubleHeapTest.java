// Tests: Structure of double heap
package algolib.structures;

import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoubleHeapTest
{
    private final Integer[] numbers =
            new Integer[]{10, 6, 14, 97, 24, 37, 2, 30, 45, 18, 51, 71, 68, 26};
    private DoubleHeap<Integer> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DoubleHeap<>(Arrays.asList(numbers));
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        testObject = new DoubleHeap<>();

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
        testObject = new DoubleHeap<>();

        int result = testObject.size();

        Assertions.assertEquals(0, result);
    }

    @Test
    public void size_WhenNotEmpty_ThenNumberOfElements()
    {
        int result = testObject.size();

        Assertions.assertEquals(numbers.length, result);
    }
}
