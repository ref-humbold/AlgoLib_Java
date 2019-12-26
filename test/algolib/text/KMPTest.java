// TESTY DLA ALGORYTMU KNUTHA-MORRISA-PRATTA
package algolib.text;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KMPTest
{
    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void kMP_WhenPatternFoundOnce()
    {
        String text = "abcde";
        String pattern = "a";

        List<Integer> result = KMP.kmp(text, pattern);

        Assert.assertArrayEquals(new Integer[]{0}, result.toArray());
    }

    @Test
    public void kMP_WhenPatternFoundTwice()
    {
        String text = "abcdae";
        String pattern = "a";

        List<Integer> result = KMP.kmp(text, pattern);

        Assert.assertArrayEquals(new Integer[]{0, 4}, result.toArray());
    }

    @Test
    public void kMP_WhenPatternFoundTwiceAndIntersects()
    {
        String text = "aaabcde";
        String pattern = "aa";

        List<Integer> result = KMP.kmp(text, pattern);

        Assert.assertArrayEquals(new Integer[]{0, 1}, result.toArray());
    }

    @Test
    public void kMP_WhenPatternNotFound()
    {
        String text = "abcde";
        String pattern = "x";

        List<Integer> result = KMP.kmp(text, pattern);

        Assert.assertArrayEquals(new Integer[]{}, result.toArray());
    }

    @Test
    public void kMP_WhenPatternIsEmptyString()
    {
        String text = "abcde";
        String pattern = "";

        List<Integer> result = KMP.kmp(text, pattern);

        Assert.assertArrayEquals(new Integer[]{}, result.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void kMP_WhenPatternIsNull()
    {
        String text = "abcde";
        String pattern = null;

        KMP.kmp(text, pattern);
    }

    @Test
    public void kMP_WhenTextIsEmptyString()
    {
        String text = "";
        String pattern = "a";

        List<Integer> result = KMP.kmp(text, pattern);

        Assert.assertArrayEquals(new Integer[]{}, result.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void kMP_WhenTextIsNull()
    {
        String text = null;
        String pattern = "a";

        KMP.kmp(text, pattern);
    }
}
