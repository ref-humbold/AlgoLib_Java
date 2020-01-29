package algolib.text;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuffixArrayTest
{
    private static String TEXT = "mississippi";
    private SuffixArray testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new SuffixArray(TEXT);
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void getText_ThenText()
    {
        String result = testObject.getText();

        Assertions.assertEquals(TEXT, result);
    }

    @Test
    public void size_ThenNumberOfElements()
    {
        int result = testObject.size();

        Assertions.assertEquals(11, result);
    }

    @Test
    public void get_WhenInRange_ThenSuffix()
    {
        String result0 = testObject.get(0);
        String result1 = testObject.get(3);
        String result2 = testObject.get(6);
        String result3 = testObject.get(9);

        Assertions.assertEquals("i", result0);
        Assertions.assertEquals("ississippi", result1);
        Assertions.assertEquals("ppi", result2);
        Assertions.assertEquals("ssippi", result3);
    }

    @Test
    public void get_WhenOutOfRange_ThenIndexOutOfBoundsException()
    {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> testObject.get(20));
    }

    @Test
    public void indexAt_WhenInRange_ThenIndexInText()
    {
        int result0 = testObject.indexAt(0);
        int result1 = testObject.indexAt(3);
        int result2 = testObject.indexAt(6);
        int result3 = testObject.indexAt(9);

        Assertions.assertEquals(10, result0);
        Assertions.assertEquals(1, result1);
        Assertions.assertEquals(8, result2);
        Assertions.assertEquals(5, result3);
    }

    @Test
    public void indexAt_WhenOutOfRange_ThenIndexOutOfBoundsException()
    {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> testObject.indexAt(20));
    }

    @Test
    public void indexOf_WhenInRange_ThenIndexInArray()
    {
        int result0 = testObject.indexOf(0);
        int result1 = testObject.indexOf(3);
        int result2 = testObject.indexOf(6);
        int result3 = testObject.indexOf(9);

        Assertions.assertEquals(4, result0);
        Assertions.assertEquals(8, result1);
        Assertions.assertEquals(7, result2);
        Assertions.assertEquals(5, result3);
    }

    @Test
    public void indexOf_WhenOutOfRange_ThenIndexOutOfBoundsException()
    {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> testObject.indexOf(20));
    }

    @Test
    public void countLCP_WhenSameSuffix_ThenLengthOfPrefix()
    {
        int result = testObject.countLCP(4, 4);

        Assertions.assertEquals(7, result);
    }

    @Test
    public void countLCP_WhenFirstEarlierThanSecondSuffix_ThenLengthOfPrefix()
    {
        int result = testObject.countLCP(1, 10);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void countLCP_WhenFirstFurtherThanSecondSuffix_ThenLengthOfPrefix()
    {
        int result = testObject.countLCP(9, 6);

        Assertions.assertEquals(0, result);
    }

    @Test
    public void countLCP_WhenSwapSuffix_ThenSameLength()
    {
        int result0 = testObject.countLCP(2, 5);
        int result1 = testObject.countLCP(5, 2);

        Assertions.assertEquals(3, result0);
        Assertions.assertEquals(result0, result1);
    }
}
