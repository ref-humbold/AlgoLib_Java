package algolib.text;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Knuth-Morris-Pratt algorithm for pattern searching.
public class KnuthMorrisPrattTest
{
    @Test
    public void kmpSearch_WhenPatternFoundOnce_ThenSingleOccurrence()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmpSearch("abcde", "a");
        // then
        Assertions.assertThat(result).containsExactly(0);
    }

    @Test
    public void kmpSearch_WhenPatternFoundTwice_ThenTwoOccurrences()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmpSearch("abcdae", "a");
        // then
        Assertions.assertThat(result).containsExactly(0, 4);
    }

    @Test
    public void kmpSearch_WhenPatternFoundTwiceAndOverlaps_ThenTwoOccurrences()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmpSearch("aaabcde", "aa");
        // then
        Assertions.assertThat(result).containsExactly(0, 1);
    }

    @Test
    public void kmpSearch_WhenPatternNotFound_ThenEmpty()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmpSearch("abcde", "x");
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmpSearch_WhenPatternIsEmptyString_ThenEmpty()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmpSearch("abcde", "");
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmpSearch_WhenTextIsEmptyString_ThenEmpty()
    {
        // when
        List<Integer> result = KnuthMorrisPratt.kmpSearch("", "a");
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void kmpSearch_WhenTextIsNull_ThenNullPointerException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> KnuthMorrisPratt.kmpSearch(null, "a"));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void kmpSearch_WhenPatternIsNull_ThenNullPointerException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> KnuthMorrisPratt.kmpSearch("abcde", null));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }
}
