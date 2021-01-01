package algolib.text;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.tuples.Pair;

public class BaseWordsMapTest
{
    private BaseWordsMap testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = BaseWordsMap.build("mississippi");
    }

    @Test
    public void getCode_WhenEmptyRange_ThenZeroAndZero()
    {
        // when
        Pair<Integer, Integer> result = testObject.getCode(4, 4);
        // then
        Assertions.assertThat(result).isEqualTo(Pair.of(0, 0));
    }

    @Test
    public void getCode_WhenStartIndexGreaterThanEndIndex_ThenZeroAndZero()
    {
        // when
        Pair<Integer, Integer> result = testObject.getCode(6, 2);
        // then
        Assertions.assertThat(result).isEqualTo(Pair.of(0, 0));
    }

    @Test
    public void getCode_WhenSingleCharacter_ThenCodeAndZero()
    {
        // when
        Pair<Integer, Integer> result1 = testObject.getCode(1, 2);  // i
        Pair<Integer, Integer> result2 = testObject.getCode(0, 1);  // m
        Pair<Integer, Integer> result3 = testObject.getCode(8, 9);  // p
        Pair<Integer, Integer> result4 = testObject.getCode(3, 4);  // s
        // then
        Assertions.assertThat(result1).isEqualTo(Pair.of(1, 0));
        Assertions.assertThat(result2).isEqualTo(Pair.of(2, 0));
        Assertions.assertThat(result3).isEqualTo(Pair.of(3, 0));
        Assertions.assertThat(result4).isEqualTo(Pair.of(4, 0));
    }

    @Test
    public void getCode_WhenBaseWord_ThenCodeAndZero()
    {
        // when
        Pair<Integer, Integer> result1 = testObject.getCode(0, 1);  // m
        Pair<Integer, Integer> result2 = testObject.getCode(4, 6);  // is
        Pair<Integer, Integer> result3 = testObject.getCode(8, 10);  // pp
        Pair<Integer, Integer> result4 = testObject.getCode(7);  // ippi
        Pair<Integer, Integer> result5 = testObject.getCode(3, 7);  // siss
        // then
        Assertions.assertThat(result1).isEqualTo(Pair.of(2, 0));
        Assertions.assertThat(result2).isEqualTo(Pair.of(6, 0));
        Assertions.assertThat(result3).isEqualTo(Pair.of(9, 0));
        Assertions.assertThat(result4).isEqualTo(Pair.of(12, 0));
        Assertions.assertThat(result5).isEqualTo(Pair.of(16, 0));
    }

    @Test
    public void getCode_WhenComposedWord_ThenCodeAndCode()
    {
        // when
        Pair<Integer, Integer> result1 = testObject.getCode(0, 3);  // mis
        // then
        Assertions.assertThat(result1).isEqualTo(Pair.of(7, 6));
    }

    @Test
    public void getCode_WhenInvalidStartIndex_ThenIndexOutOfRangeException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.getCode(-1));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void getCode_WhenInvalidEndIndex_ThenIndexOutOfRangeException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.getCode(5, 15));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
    }
}
