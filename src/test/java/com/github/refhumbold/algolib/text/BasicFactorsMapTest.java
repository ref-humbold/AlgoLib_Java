package com.github.refhumbold.algolib.text;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.github.refhumbold.algolib.tuples.Pair;

// Tests: Structure of basic factors map using Karp-Miller-Rosenberg algorithm.
public class BasicFactorsMapTest
{
    private BasicFactorsMap testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = BasicFactorsMap.build("mississippi");
    }

    @ParameterizedTest
    @MethodSource("paramsFor_getCode_WhenValidRange")
    public void getCode_WhenValidRange_ThenCode(
            int startIndex,
            Integer endIndex,
            Pair<Integer, Integer> expected)
    {
        // when
        Pair<Integer, Integer> result = endIndex == null
                                        ? testObject.getCode(startIndex)
                                        : testObject.getCode(startIndex, endIndex);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getCode_WhenStartIndexEqualToEndIndex_ThenIllegalArgumentException()
    {
        Assertions.assertThatThrownBy(() -> testObject.getCode(4, 4))
                  .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getCode_WhenStartIndexGreaterThanEndIndex_ThenIllegalArgumentException()
    {
        Assertions.assertThatThrownBy(() -> testObject.getCode(6, 2))
                  .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("paramsFor_getCode_WhenInvalidRange")
    public void getCode_WhenInvalidRange_ThenIndexOutOfRangeException(
            int startIndex,
            Integer endIndex)
    {
        Assertions.assertThatThrownBy(() -> {
            if(endIndex == null)
                testObject.getCode(startIndex);
            else
                testObject.getCode(startIndex, endIndex);
        }).isInstanceOf(IndexOutOfBoundsException.class);
    }

    private static Stream<Arguments> paramsFor_getCode_WhenValidRange()
    {
        return Stream.of(Arguments.of(0, 1, Pair.of(2, 0)), Arguments.of(0, 3, Pair.of(7, 6)),
                Arguments.of(0, null, Pair.of(20, 21)), Arguments.of(1, 2, Pair.of(1, 0)),
                Arguments.of(3, 4, Pair.of(4, 0)), Arguments.of(3, 7, Pair.of(16, 0)),
                Arguments.of(4, 6, Pair.of(6, 0)), Arguments.of(7, null, Pair.of(12, 0)),
                Arguments.of(8, 9, Pair.of(3, 0)), Arguments.of(8, 10, Pair.of(9, 0)));
    }

    private static Stream<Arguments> paramsFor_getCode_WhenInvalidRange()
    {
        return Stream.of(Arguments.of(-10, null), Arguments.of(22, null), Arguments.of(5, 15),
                Arguments.of(18, 28), Arguments.of(-3, 3));
    }
}
