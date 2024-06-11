package algolib.tuples;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComparableTripleTest
{
    @Test
    public void toString_ThenStringRepresentation()
    {
        // when
        String result = ComparableTriple.of(124, 'a', 7.8).toString();

        // then
        Assertions.assertThat(result).isEqualTo("(124, a, 7.8)");
    }

    @Test
    public void equals_WhenAllElementsEqual_ThenTrue()
    {
        // when
        boolean result = ComparableTriple.of(124, 'a', 7.8).equals(Triple.of(124, 'a', 7.8));

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void equals_WhenFirstElementDifferent_ThenFalse()
    {
        // when
        boolean result = ComparableTriple.of(124, 'a', 7.8).equals(Triple.of(118, 'a', 7.8));

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void equals_WhenSecondElementDifferent_ThenFalse()
    {
        // when
        boolean result = ComparableTriple.of(124, 'a', 7.8).equals(Triple.of(124, 'k', 7.8));

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void equals_WhenThirdElementDifferent_ThenFalse()
    {
        // when
        boolean result = ComparableTriple.of(124, 'a', 7.8).equals(Triple.of(124, 'a', -8.9));

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void compareTo_WhenAllElementsEqual_ThenZero()
    {
        // when
        int result = ComparableTriple.of(124, 'a', 7.8).compareTo(Triple.of(124, 'a', 7.8));

        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void compareTo_WhenFirstElementGreater_ThenPositive()
    {
        // when
        int result = ComparableTriple.of(124, 'a', 7.8).compareTo(Triple.of(118, 'k', -8.9));

        // then
        Assertions.assertThat(result).isPositive();
    }

    @Test
    public void compareTo_WhenSecondElementLess_ThenNegative()
    {
        // when
        int result = ComparableTriple.of(124, 'a', 7.8).compareTo(Triple.of(124, 'k', -8.9));

        // then
        Assertions.assertThat(result).isNegative();
    }

    @Test
    public void compareTo_WhenThirdElementGreater_ThenPositive()
    {
        // when
        int result = ComparableTriple.of(124, 'a', 7.8).compareTo(Triple.of(124, 'a', -8.9));

        // then
        Assertions.assertThat(result).isPositive();
    }

    @Test
    public void compareTo_WhenSecondElementNull_ThenNegative()
    {
        // when
        int result =
                ComparableTriple.of(124, (Character)null, 7.8).compareTo(Triple.of(124, 'a', 7.8));

        // then
        Assertions.assertThat(result).isNegative();
    }

    @Test
    public void compareTo_WhenSecondElementNotNull_ThenPositive()
    {
        // when
        int result = ComparableTriple.of(124, 'a', 7.8).compareTo(Triple.of(124, null, 7.8));

        // then
        Assertions.assertThat(result).isPositive();
    }
}
