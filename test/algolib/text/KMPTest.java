package algolib.text;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Knuth-Morris-Pratt algorithm
public class KMPTest
{
    @Test
    public void kmp_WhenPatternFoundOnce_ThenSingleOccurrences()
    {
        // given
        String text = "abcde";
        String pattern = "a";
        // when
        List<Integer> result = KMP.kmp(text, pattern);
        // then
        Assertions.assertThat(result).containsExactly(0);
    }

    @Test
    public void kmp_WhenPatternFoundTwice_ThenBothOccurrences()
    {
        String text = "abcdae";
        String pattern = "a";
        // when
        List<Integer> result = KMP.kmp(text, pattern);
        // then
        Assertions.assertThat(result).containsExactly(0, 4);
    }

    @Test
    public void kmp_WhenPatternFoundTwiceAndIntersects_ThenBothOccurrences()
    {
        String text = "aaabcde";
        String pattern = "aa";
        // when
        List<Integer> result = KMP.kmp(text, pattern);
        // then
        Assertions.assertThat(result).containsExactly(0, 1);
    }

    @Test
    public void kmp_WhenPatternNotFound_ThenEmpty()
    {
        String text = "abcde";
        String pattern = "x";
        // when
        List<Integer> result = KMP.kmp(text, pattern);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmp_WhenPatternIsEmptyString_ThenEmpty()
    {
        String text = "abcde";
        String pattern = "";
        // when
        List<Integer> result = KMP.kmp(text, pattern);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmp_WhenPatternIsNull_ThenNullPointerException()
    {
        String text = "abcde";
        String pattern = null;
        // when
        Throwable throwable = Assertions.catchThrowable(() -> KMP.kmp(text, pattern));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void kmp_WhenTextIsEmptyString_ThenEmpty()
    {
        String text = "";
        String pattern = "a";
        // when
        List<Integer> result = KMP.kmp(text, pattern);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmp_WhenTextIsNull_ThenNullPointerException()
    {
        String text = null;
        String pattern = "a";
        // when
        Throwable throwable = Assertions.catchThrowable(() -> KMP.kmp(text, pattern));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }
}
