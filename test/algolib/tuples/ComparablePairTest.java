package algolib.tuples;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComparablePairTest
{
    @Test
    public void toString_ThenStringRepresentation()
    {
        // when
        String result = ComparablePair.of(124, 'a').toString();
        // then
        Assertions.assertThat(result).isEqualTo("(124, a)");
    }

    @Test
    public void equals_WhenAllElementsEqual_ThenTrue()
    {
        // when
        boolean result = ComparablePair.of(124, 'a').equals(Pair.of(124, 'a'));
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void equals_WhenFirstElementDifferent_ThenFalse()
    {
        // when
        boolean result = ComparablePair.of(124, 'a').equals(Pair.of(118, 'a'));
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void equals_WhenSecondElementDifferent_ThenFalse()
    {
        // when
        boolean result = ComparablePair.of(124, 'a').equals(Pair.of(124, 'k'));
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void compareTo_WhenAllElementsEqual_ThenZero()
    {
        // when
        int result = ComparablePair.of(124, 'a').compareTo(Pair.of(124, 'a'));
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void compareTo_WhenFirstElementGreater_ThenPositive()
    {
        // when
        int result = ComparablePair.of(124, 'a').compareTo(Pair.of(118, 'k'));
        // then
        Assertions.assertThat(result).isPositive();
    }

    @Test
    public void compareTo_WhenSecondElementLess_ThenNegative()
    {
        // when
        int result = ComparablePair.of(124, 'a').compareTo(Pair.of(124, 'k'));
        // then
        Assertions.assertThat(result).isNegative();
    }

    @Test
    public void compareTo_WhenFirstElementNull_ThenNegative()
    {
        // when
        int result = ComparablePair.of((Integer)null, 'a').compareTo(Pair.of(124, 'k'));
        // then
        Assertions.assertThat(result).isNegative();
    }

    @Test
    public void compareTo_WhenFirstElementNotNull_ThenPositive()
    {
        // when
        int result = ComparablePair.of(124, 'a').compareTo(Pair.of(null, 'k'));
        // then
        Assertions.assertThat(result).isPositive();
    }
}
