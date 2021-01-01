package algolib.text;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuffixArrayTest
{
    private static final String TEXT = "mississippi";
    private SuffixArray testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = SuffixArray.build(TEXT);
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void getText_ThenText()
    {
        // when
        String result = testObject.getText();
        // then
        Assertions.assertThat(result).isEqualTo(TEXT);
    }

    @Test
    public void size_ThenNumberOfElements()
    {
        // when
        int result = testObject.size();
        // then
        Assertions.assertThat(result).isEqualTo(11);
    }

    @Test
    public void get_WhenInRange_ThenSuffix()
    {
        // when
        String result0 = testObject.get(0);
        String result1 = testObject.get(3);
        String result2 = testObject.get(6);
        String result3 = testObject.get(9);
        // then
        Assertions.assertThat(result0).isEqualTo("i");
        Assertions.assertThat(result1).isEqualTo("ississippi");
        Assertions.assertThat(result2).isEqualTo("ppi");
        Assertions.assertThat(result3).isEqualTo("ssippi");
    }

    @Test
    public void get_WhenOutOfRange_ThenIndexOutOfBoundsException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.get(20));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void indexAt_WhenInRange_ThenIndexInText()
    {
        // when
        int result0 = testObject.indexAt(0);
        int result1 = testObject.indexAt(3);
        int result2 = testObject.indexAt(6);
        int result3 = testObject.indexAt(9);
        // then
        Assertions.assertThat(result0).isEqualTo(10);
        Assertions.assertThat(result1).isEqualTo(1);
        Assertions.assertThat(result2).isEqualTo(8);
        Assertions.assertThat(result3).isEqualTo(5);
    }

    @Test
    public void indexAt_WhenOutOfRange_ThenIndexOutOfBoundsException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.indexAt(20));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void indexOf_WhenInRange_ThenIndexInArray()
    {
        // when
        int result0 = testObject.indexOf(0);
        int result1 = testObject.indexOf(3);
        int result2 = testObject.indexOf(6);
        int result3 = testObject.indexOf(9);
        // then
        Assertions.assertThat(result0).isEqualTo(4);
        Assertions.assertThat(result1).isEqualTo(8);
        Assertions.assertThat(result2).isEqualTo(7);
        Assertions.assertThat(result3).isEqualTo(5);
    }

    @Test
    public void indexOf_WhenOutOfRange_ThenIndexOutOfBoundsException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.indexOf(20));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void countLCP_WhenSameSuffix_ThenLengthOfPrefix()
    {
        // when
        int result = testObject.countLCP(4, 4);
        // then
        Assertions.assertThat(result).isEqualTo(7);
    }

    @Test
    public void countLCP_WhenFirstEarlierThanSecondSuffix_ThenLengthOfPrefix()
    {
        // when
        int result = testObject.countLCP(1, 10);
        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void countLCP_WhenFirstFurtherThanSecondSuffix_ThenLengthOfPrefix()
    {
        // when
        int result = testObject.countLCP(9, 6);
        // then
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void countLCP_WhenSwapSuffix_ThenSameLength()
    {
        // when
        int result0 = testObject.countLCP(2, 5);
        int result1 = testObject.countLCP(5, 2);
        // then
        Assertions.assertThat(result0).isEqualTo(3);
        Assertions.assertThat(result1).isEqualTo(result0);
    }
}
