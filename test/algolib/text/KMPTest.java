package algolib.text;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Knuth-Morris-Pratt algorithm
public class KMPTest
{
    @Test
    public void kmp_WhenPatternFoundOnce_ThenSingleOccurrence()
    {
        // when
        List<Integer> result = KMP.kmp("abcde", "a");
        // then
        Assertions.assertThat(result).containsExactly(0);
    }

    @Test
    public void kmp_WhenPatternFoundTwice_ThenTwoOccurrences()
    {
        // when
        List<Integer> result = KMP.kmp("abcdae", "a");
        // then
        Assertions.assertThat(result).containsExactly(0, 4);
    }

    @Test
    public void kmp_WhenPatternFoundTwiceAndIntersects_ThenTwoOccurrences()
    {
        // when
        List<Integer> result = KMP.kmp("aaabcde", "aa");
        // then
        Assertions.assertThat(result).containsExactly(0, 1);
    }

    @Test
    public void kmp_WhenPatternNotFound_ThenEmpty()
    {
        // when
        List<Integer> result = KMP.kmp("abcde", "x");
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmp_WhenPatternIsEmptyString_ThenEmpty()
    {
        // when
        List<Integer> result = KMP.kmp("abcde", "");
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmp_WhenTextIsEmptyString_ThenEmpty()
    {
        // when
        List<Integer> result = KMP.kmp("", "a");
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmp_WhenTextIsNull_ThenNullPointerException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> KMP.kmp(null, "a"));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void kmp_WhenPatternIsNull_ThenNullPointerException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> KMP.kmp("abcde", null));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }
}
