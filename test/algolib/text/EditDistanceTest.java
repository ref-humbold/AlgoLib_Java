package algolib.text;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

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

    // endregion
}
