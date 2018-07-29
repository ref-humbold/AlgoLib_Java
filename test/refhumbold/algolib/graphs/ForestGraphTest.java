// TESTY DLA STRUKTURY GRAFÓW DRZEW
package refhumbold.algolib.graphs;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ForestGraphTest
{
    private ForestGraph testObject;

    @Before
    public void setUp()
    {
        testObject = new ForestGraph(10);
    }

    @After
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void testTreesNumber()
    {
        int result = testObject.getTreesNumber();

        Assert.assertEquals(10, result);
    }

    @Test
    public void testAddVertex()
    {
        int result = testObject.addVertex();

        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new Object[]{}, testObject.getNeighbours(result).toArray());
    }

    @Test
    public void testAddEdgeWhenNoCycle()
    {
        int vertex1 = 1;
        int vertex2 = 2;

        testObject.addEdge(vertex1, vertex2);

        Assert.assertTrue(testObject.getNeighbours(vertex1).contains(vertex2));
        Assert.assertTrue(testObject.getNeighbours(vertex2).contains(vertex1));
    }

    @Test(expected = CycleException.class)
    public void testAddEdgeWhenCycle()
        throws CycleException
    {
        int vertex1 = 1;
        int vertex2 = 2;
        int vertex3 = 3;

        try
        {
            testObject.addEdge(vertex1, vertex2);
            testObject.addEdge(vertex2, vertex3);
        }
        catch(CycleException e)
        {
            e.printStackTrace();
            Assert.fail("Unexpected exception " + e.getClass().getSimpleName());
        }

        testObject.addEdge(vertex3, vertex1);
    }

    @Test
    public void testIsSameTreeWhenConnected()
    {
        int vertex1 = 1;
        int vertex2 = 2;

        testObject.addEdge(vertex1, vertex2);

        boolean result = testObject.isSameTree(vertex1, vertex2);

        Assert.assertTrue(result);
    }

    @Test
    public void testIsSameTreeWhenNotConnected()
    {
        int vertex1 = 1;
        int vertex2 = 2;

        boolean result = testObject.isSameTree(vertex1, vertex2);

        Assert.assertFalse(result);
    }
}
