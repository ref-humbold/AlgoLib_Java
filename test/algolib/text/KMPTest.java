// TESTY DLA ALGORYTMU KNUTHA-MORRISA-PRATTA
package algolib.text;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KMPTest
{
    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void kMP_WhenPatternFoundOnce()
    {
        String text = "abcde";
        String pattern = "a";

        List<Integer> result = KMP.kmp(text, pattern);

        Assertions.assertArrayEquals(new Integer[]{0}, result.toArray());
    }

    @Test
    public void kMP_WhenPatternFoundTwice()
    {
        String text = "abcdae";
        String pattern = "a";

        List<Integer> result = KMP.kmp(text, pattern);

        Assertions.assertArrayEquals(new Integer[]{0, 4}, result.toArray());
    }

    @Test
    public void kMP_WhenPatternFoundTwiceAndIntersects()
    {
        String text = "aaabcde";
        String pattern = "aa";

        List<Integer> result = KMP.kmp(text, pattern);

        Assertions.assertArrayEquals(new Integer[]{0, 1}, result.toArray());
    }

    @Test
    public void kMP_WhenPatternNotFound()
    {
        String text = "abcde";
        String pattern = "x";

        List<Integer> result = KMP.kmp(text, pattern);

        Assertions.assertArrayEquals(new Integer[]{}, result.toArray());
    }

    @Test
    public void kMP_WhenPatternIsEmptyString()
    {
        String text = "abcde";
        String pattern = "";

        List<Integer> result = KMP.kmp(text, pattern);

        Assertions.assertArrayEquals(new Integer[]{}, result.toArray());
    }

    @Test
    public void kMP_WhenPatternIsNull_ThenNullPointerException()
    {
        String text = "abcde";
        String pattern = null;

        Assertions.assertThrows(NullPointerException.class, () -> KMP.kmp(text, pattern));
    }

    @Test
    public void kMP_WhenTextIsEmptyString()
    {
        String text = "";
        String pattern = "a";

        List<Integer> result = KMP.kmp(text, pattern);

        Assertions.assertArrayEquals(new Integer[]{}, result.toArray());
    }

    @Test
    public void kMP_WhenTextIsNull_ThenNullPointerException()
    {
        String text = null;
        String pattern = "a";

        Assertions.assertThrows(NullPointerException.class, () -> KMP.kmp(text, pattern));
    }
}
