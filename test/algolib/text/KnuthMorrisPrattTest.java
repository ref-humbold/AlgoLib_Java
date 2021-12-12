package algolib.text;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Knuth-Morris-Pratt algorithm for pattern searching
public class KnuthMorrisPrattTest
{
    @Test
    public void kmp_WhenPatternFoundOnce_ThenSingleOccurrence()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmp("abcde", "a");
        // then
        Assertions.assertThat(result).containsExactly(0);
    }

    @Test
    public void kmp_WhenPatternFoundTwice_ThenTwoOccurrences()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmp("abcdae", "a");
        // then
        Assertions.assertThat(result).containsExactly(0, 4);
    }

    @Test
    public void kmp_WhenPatternFoundTwiceAndOverlaps_ThenTwoOccurrences()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmp("aaabcde", "aa");
        // then
        Assertions.assertThat(result).containsExactly(0, 1);
    }

    @Test
    public void kmp_WhenPatternNotFound_ThenEmpty()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmp("abcde", "x");
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmp_WhenPatternIsEmptyString_ThenEmpty()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmp("abcde", "");
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmp_WhenTextIsEmptyString_ThenEmpty()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmp("", "a");
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmp_WhenTextIsNull_ThenNullPointerException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> KnuthMorrisPratt.kmp(null, "a"));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void kmp_WhenPatternIsNull_ThenNullPointerException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> KnuthMorrisPratt.kmp("abcde", null));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }
}
