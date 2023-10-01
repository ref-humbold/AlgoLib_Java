package algolib.sequences;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Algorithm for longest common subsequence.
public class LongestCommonSubsequenceTest
{
    @Test
    public void countLcsLength_WhenEmpty_ThenZero()
    {
        // when
        int result = LongestCommonSubsequence.countLcsLength("qwertyuiop", "");
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void countLcsLength_WhenRepeatedSingleElement_ThenOne()
    {
        // when
        int result = LongestCommonSubsequence.countLcsLength("abcde", "eeee");
        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void countLcsLength_WhenSameCharacterText_ThenShorterLength()
    {
        // given
        String text = "xxxx";
        // when
        int result = LongestCommonSubsequence.countLcsLength(text + text, text);
        // then
        Assertions.assertThat(result).isEqualTo(text.length());
    }

    @Test
    public void countLcsLength_WhenSameText_ThenTextLength()
    {
        // given
        String sequence = "qwertyuiop";
        // when
        int result = LongestCommonSubsequence.countLcsLength(sequence, sequence);
        // then
        Assertions.assertThat(result).isEqualTo(sequence.length());
    }

    @Test
    public void countLcsLength_WhenSubtext_ThenSubtextLength()
    {
        // when
        int result = LongestCommonSubsequence.countLcsLength("qwertyuiop", "zxqwertyasdfuiopcvb");
        // then
        Assertions.assertThat(result).isEqualTo("qwertyuiop".length());
    }

    @Test
    public void countLcsLength_WhenDifferent_ThenZero()
    {
        // when
        int result = LongestCommonSubsequence.countLcsLength("qwertyuiop", "asdfghjkl");
        // then
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void countLcsLength_WhenCommonSubtext_ThenCommonSubtextLength()
    {
        // when
        int result = LongestCommonSubsequence.countLcsLength("qwertyuiop", "zxrtyasdfuiopcvb");
        // then
        Assertions.assertThat(result).isEqualTo("rtyuiop".length());
    }

    @Test
    public void countLcsLength_WhenSameElementSequence_ThenShorterLength()
    {
        // given
        List<Integer> sequence = Collections.nCopies(25, 11);
        // when
        int result = LongestCommonSubsequence.countLcsLength(sequence,
                                                             Stream.concat(sequence.stream(),
                                                                           sequence.stream())
                                                                   .collect(Collectors.toList()));
        // then
        Assertions.assertThat(result).isEqualTo(sequence.size());
    }

    @Test
    public void countLcsLength_WhenSameSequence_ThenSequenceLength()
    {
        // given
        List<Integer> sequence = "qwertyuiop".chars().boxed().collect(Collectors.toList());
        // when
        int result = LongestCommonSubsequence.countLcsLength(sequence, sequence);
        // then
        Assertions.assertThat(result).isEqualTo(sequence.size());
    }

    @Test
    public void countLcsLength_WhenCommonSubsequence_ThenCommonSubsequenceLength()
    {
        // when
        int result = LongestCommonSubsequence.countLcsLength(
                "qwertyuiop".chars().boxed().collect(Collectors.toList()),
                "zxrtyasdfuiopcvb".chars().boxed().collect(Collectors.toList()));
        // then
        Assertions.assertThat(result).isEqualTo("rtyuiop".length());
    }
}
