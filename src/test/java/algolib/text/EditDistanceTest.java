package algolib.text;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

// Tests: Algorithms for edit distance.
public class EditDistanceTest
{
    private static final Offset<Double> OFFSET = Offset.offset(1e-6);

    // region countLevenshtein

    @Test
    public void countLevenshtein_WhenSameText_ThenZero()
    {
        // given
        String text = "qwertyuiop";
        // when
        double result = EditDistance.countLevenshtein(text, text);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void countLevenshtein_WhenEmptySource_ThenSumOfInsertions()
    {
        // given
        String text = "qwertyuiop";
        double insertionCost = 2.0;
        // when
        double result = EditDistance.countLevenshtein("", text, insertionCost, 1.0, 1.0);
        // then
        Assertions.assertThat(result).isCloseTo(text.length() * insertionCost, OFFSET);
    }

    @Test
    public void countLevenshtein_WhenEmptyDestination_ThenSumOfDeletions()
    {
        // given
        String text = "qwertyuiop";
        double deletionCost = 2.0;
        // when
        double result = EditDistance.countLevenshtein(text, "", 1.0, deletionCost, 1.0);
        // then
        Assertions.assertThat(result).isCloseTo(text.length() * deletionCost, OFFSET);
    }

    @Test
    public void countLevenshtein_WhenNegativeCost_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> EditDistance.countLevenshtein("a", "b", 1.0, 1.0, -1.0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    // endregion
    // region countLcs

    @Test
    public void countLcs_WhenSameText_ThenZero()
    {
        // given
        String text = "qwertyuiop";
        // when
        double result = EditDistance.countLcs(text, text);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void countLcs_WhenEmptySource_ThenSumOfInsertions()
    {
        // given
        String text = "qwertyuiop";
        double insertionCost = 2.0;
        // when
        double result = EditDistance.countLcs("", text, insertionCost, 1.0);
        // then
        Assertions.assertThat(result).isCloseTo(text.length() * insertionCost, OFFSET);
    }

    @Test
    public void countLcs_WhenEmptyDestination_ThenSumOfDeletions()
    {
        // given
        String text = "qwertyuiop";
        double deletionCost = 2.0;
        // when
        double result = EditDistance.countLcs(text, "", 1.0, deletionCost);
        // then
        Assertions.assertThat(result).isCloseTo(text.length() * deletionCost, OFFSET);
    }

    @Test
    public void countLcs_WhenNegativeCost_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> EditDistance.countLcs("a", "b", 1.0, -1.0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    // endregion
    // region countHamming

    @Test
    public void countHamming_WhenEmpty_ThenZero()
    {
        // when
        double result = EditDistance.countHamming("", "");
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void countHamming_WhenSameText_ThenZero()
    {
        // given
        String text = "qwertyuiop";
        // when
        double result = EditDistance.countHamming(text, text);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void countHamming_WhenDifferentLength_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> EditDistance.countHamming("qwerty", "asdf"));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void countHamming_WhenNegativeCost_ThenIllegalArgumentException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> EditDistance.countHamming("a", "b", -1.0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    // endregion
}
