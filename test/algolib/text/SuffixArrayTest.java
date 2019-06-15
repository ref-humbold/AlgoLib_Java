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
    public void testGetText()
    {
        String result = testObject.getText();

        Assert.assertEquals(TEXT, result);
    }

    @Test
    public void testSize()
    {
        int result = testObject.size();

        Assert.assertEquals(11, result);
    }

    @Test
    public void testGet()
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
    public void testAtWhenOutOfRange()
    {
        testObject.get(20);
    }

    @Test
    public void testIndexAt()
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
    public void testIndexAtWhenOutOfRange()
    {
        testObject.indexAt(20);
    }

    @Test
    public void textIndexOf()
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
    public void testIndexOfWhenOutOfRange()
    {
        testObject.indexOf(20);
    }

    @Test
    public void testLCPWhenSameSuffix()
    {
        int result = testObject.countLCP(4, 4);

        Assert.assertEquals(7, result);
    }

    @Test
    public void testLCPWhenDifferentSuffix1()
    {
        int result = testObject.countLCP(1, 10);

        Assert.assertEquals(1, result);
    }

    @Test
    public void testLCPWhenDifferentSuffix2()
    {
        int result = testObject.countLCP(9, 6);

        Assert.assertEquals(0, result);
    }

    @Test
    public void testLCPWhenSwapSuffix()
    {
        int result0 = testObject.countLCP(2, 5);
        int result1 = testObject.countLCP(5, 2);

        Assert.assertEquals(3, result0);
        Assert.assertEquals(result0, result1);
    }
}
