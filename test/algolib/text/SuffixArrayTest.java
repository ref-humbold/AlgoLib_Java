package algolib.text;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SuffixArrayTest
{
    private static String TEXT = "mississippi";
    private SuffixArray testObject;

    @Before
    public void setUp()
    {
        testObject = new SuffixArray(TEXT);
    }

    @After
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void getText_ThenText()
    {
        String result = testObject.getText();

        Assert.assertEquals(TEXT, result);
    }

    @Test
    public void size_ThenNumberOfElements()
    {
        int result = testObject.size();

        Assert.assertEquals(11, result);
    }

    @Test
    public void get_WhenInRange_ThenSuffix()
    {
        String result0 = testObject.get(0);
        String result1 = testObject.get(3);
        String result2 = testObject.get(6);
        String result3 = testObject.get(9);

        Assert.assertEquals("i", result0);
        Assert.assertEquals("ississippi", result1);
        Assert.assertEquals("ppi", result2);
        Assert.assertEquals("ssippi", result3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_WhenOutOfRange_ThenIndexOutOfBoundsException()
    {
        testObject.get(20);
    }

    @Test
    public void indexAt_WhenInRange_ThenIndexInText()
    {
        int result0 = testObject.indexAt(0);
        int result1 = testObject.indexAt(3);
        int result2 = testObject.indexAt(6);
        int result3 = testObject.indexAt(9);

        Assert.assertEquals(10, result0);
        Assert.assertEquals(1, result1);
        Assert.assertEquals(8, result2);
        Assert.assertEquals(5, result3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void indexAt_WhenOutOfRange_ThenIndexOutOfBoundsException()
    {
        testObject.indexAt(20);
    }

    @Test
    public void indexOf_WhenInRange_ThenIndexInArray()
    {
        int result0 = testObject.indexOf(0);
        int result1 = testObject.indexOf(3);
        int result2 = testObject.indexOf(6);
        int result3 = testObject.indexOf(9);

        Assert.assertEquals(4, result0);
        Assert.assertEquals(8, result1);
        Assert.assertEquals(7, result2);
        Assert.assertEquals(5, result3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void indexOf_WhenOutOfRange_ThenIndexOutOfBoundsException()
    {
        testObject.indexOf(20);
    }

    @Test
    public void countLCP_WhenSameSuffix_ThenLengthOfPrefix()
    {
        int result = testObject.countLCP(4, 4);

        Assert.assertEquals(7, result);
    }

    @Test
    public void countLCP_WhenFirstEarlierThanSecondSuffix_ThenLengthOfPrefix()
    {
        int result = testObject.countLCP(1, 10);

        Assert.assertEquals(1, result);
    }

    @Test
    public void countLCP_WhenFirstFurtherThanSecondSuffix_ThenLengthOfPrefix()
    {
        int result = testObject.countLCP(9, 6);

        Assert.assertEquals(0, result);
    }

    @Test
    public void countLCP_WhenSwapSuffix_ThenSameLength()
    {
        int result0 = testObject.countLCP(2, 5);
        int result1 = testObject.countLCP(5, 2);

        Assert.assertEquals(3, result0);
        Assert.assertEquals(result0, result1);
    }
}
