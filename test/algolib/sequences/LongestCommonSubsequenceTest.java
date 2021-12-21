package algolib.sequences;

import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/** Tests{ Algorithm for longest common subsequence */
public class LongestCommonSubsequenceTest
{
    @Test
    public void countLCSLength_WhenEmpty_ThenZero()
    {
        // when
        int result = LongestCommonSubsequence.countLCSLength("qwertyuiop", "");
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void countLcsLength_WhenRepeatedSingleElement_ThenOne()
    {
        // when
        int result = LongestCommonSubsequence.countLCSLength("abcde", "eeee");
        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void countLcsLength_WhenSameText_ThenTextLength()
    {
        // given
        String sequence = "qwertyuiop";
        // when
        int result = LongestCommonSubsequence.countLCSLength(sequence, sequence);
        // then
        Assertions.assertThat(result).isEqualTo(sequence.length());
    }

    @Test
    public void countLcsLength_WhenSubtext_ThenSubtextLength()
    {
        // when
        int result = LongestCommonSubsequence.countLCSLength("qwertyuiop", "zxqwertyasdfuiopcvb");
        // then
        Assertions.assertThat(result).isEqualTo("qwertyuiop".length());
    }

    @Test
    public void countLcsLength_WhenDifferent_ThenZero()
    {
        // when
        int result = LongestCommonSubsequence.countLCSLength("qwertyuiop", "asdfghjkl");
        // then
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void countLcsLength_WhenCommonSubtext_ThenCommonSubtextLength()
    {
        // when
        int result = LongestCommonSubsequence.countLCSLength("qwertyuiop", "zxrtyasdfuiopcvb");
        // then
        Assertions.assertThat(result).isEqualTo("rtyuiop".length());
    }

    @Test
    public void countLcsLength_WhenSameSequence_ThenSequenceLength()
    {
        // given
        List<Integer> sequence = "qwertyuiop".chars().boxed().collect(Collectors.toList());
        // when
        int result = LongestCommonSubsequence.countLCSLength(sequence, sequence);
        // then
        Assertions.assertThat(result).isEqualTo(sequence.size());
    }

    @Test
    public void countLcsLength_WhenCommonSubsequence_ThenCommonSubsequenceLength()
    {
        // when
        int result = LongestCommonSubsequence.countLCSLength(
                "qwertyuiop".chars().boxed().collect(Collectors.toList()),
                "zxrtyasdfuiopcvb".chars().boxed().collect(Collectors.toList()));
        // then
        Assertions.assertThat(result).isEqualTo("rtyuiop".length());
    }
}
