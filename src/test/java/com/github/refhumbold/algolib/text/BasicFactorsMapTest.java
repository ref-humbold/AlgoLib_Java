package com.github.refhumbold.algolib.text;

import java.util.List;
import java.util.stream.IntStream;
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
    @MethodSource("paramsFor_GetCode_WhenRange")
    public void getCode_WhenRange_ThenCode(
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

    @Test
    public void getCode_WhenInvalidStartIndex_ThenIndexOutOfRangeException()
    {
        Assertions.assertThatThrownBy(() -> testObject.getCode(-1))
                  .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void getCode_WhenInvalidEndIndex_ThenIndexOutOfRangeException()
    {
        Assertions.assertThatThrownBy(() -> testObject.getCode(5, 15))
                  .isInstanceOf(IndexOutOfBoundsException.class);
    }

    private static Stream<Arguments> paramsFor_GetCode_WhenRange()
    {
        Integer[][] indices = {
                { 0, 1 }, { 0, 3 }, { 0, null }, { 1, 2 }, { 3, 4 }, { 3, 7 }, { 4, 6 },
                { 7, null }, { 8, 9 }, { 8, 10 }
        };
        List<Pair<Integer, Integer>> expectedResults =
                Stream.of(Pair.of(2, 0), Pair.of(7, 6), Pair.of(20, 21), Pair.of(1, 0),
                        Pair.of(4, 0), Pair.of(16, 0), Pair.of(6, 0), Pair.of(12, 0), Pair.of(3, 0),
                        Pair.of(9, 0)).toList();

        return IntStream.range(0, indices.length)
                        .mapToObj(i -> Arguments.of(indices[i][0], indices[i][1],
                                expectedResults.get(i)));
    }
}
