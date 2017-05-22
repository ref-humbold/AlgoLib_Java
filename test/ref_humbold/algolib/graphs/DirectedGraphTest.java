package ref_humbold.algolib.graphs;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ref_humbold.algolib.structures.Pair;

public class DirectedGraphTest
{
    private DirectedGraph testObject;

    @Before
    public void setUp()
    {
        testObject = new DirectedGraph(10);
    }

    @After
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void testGetEdgesNumber()
    {
        testObject.addEdge(7, 7);
        testObject.addEdge(1, 5);
        testObject.addEdge(2, 4);
        testObject.addEdge(8, 0);
        testObject.addEdge(6, 3);
        testObject.addEdge(3, 6);
        testObject.addEdge(9, 3);
        testObject.addEdge(8, 0);

        Assert.assertEquals(7, testObject.getEdgesNumber());
    }

    @Test
    public void testGetEdges()
    {
        testObject.addEdge(7, 7);
        testObject.addEdge(1, 5);
        testObject.addEdge(2, 4);
        testObject.addEdge(8, 0);
        testObject.addEdge(6, 3);
        testObject.addEdge(3, 6);
        testObject.addEdge(9, 3);
        testObject.addEdge(8, 0);

        Collection<Pair<Integer, Integer>> result = testObject.getEdges();

        Assert.assertTrue(result.contains(new Pair<>(1, 5)));
        Assert.assertTrue(result.contains(new Pair<>(2, 4)));
        Assert.assertTrue(result.contains(new Pair<>(3, 6)));
        Assert.assertTrue(result.contains(new Pair<>(6, 3)));
        Assert.assertTrue(result.contains(new Pair<>(7, 7)));
        Assert.assertTrue(result.contains(new Pair<>(8, 0)));
        Assert.assertTrue(result.contains(new Pair<>(9, 3)));
        Assert.assertFalse(result.contains(new Pair<>(4, 7)));
        Assert.assertFalse(result.contains(new Pair<>(0, 6)));
        Assert.assertFalse(result.contains(new Pair<>(3, 3)));
        Assert.assertFalse(result.contains(new Pair<>(1, 0)));
        Assert.assertFalse(result.contains(new Pair<>(9, 8)));
    }

    @Test
    public void testAddEdge()
    {
        testObject.addEdge(1, 5);
        testObject.addEdge(1, 5);
        testObject.addEdge(1, 1);

        Assert.assertEquals(2, testObject.getEdgesNumber());
        Assert.assertTrue(testObject.getNeighbours(1).contains(1));
        Assert.assertTrue(testObject.getNeighbours(1).contains(5));
        Assert.assertFalse(testObject.getNeighbours(5).contains(1));
    }

    @Test
    public void testGetIndegree()
    {
        testObject.addEdge(1, 1);
        testObject.addEdge(3, 1);
        testObject.addEdge(4, 1);
        testObject.addEdge(7, 1);
        testObject.addEdge(9, 1);
        testObject.addEdge(1, 2);
        testObject.addEdge(1, 6);

        Assert.assertEquals(5, testObject.getIndegree(1));
    }

    @Test
    public void testGetVerticesNumber()
    {
        Assert.assertEquals(10, testObject.getVerticesNumber());
    }

    @Test
    public void testAddVertex()
    {
        int result = testObject.addVertex();

        Assert.assertEquals(10, result);
        Assert.assertEquals(11, testObject.getVerticesNumber());
    }

    @Test
    public void testGetVertices()
    {
        Assert.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                                 testObject.getVertices().toArray());
    }

    @Test
    public void testGetNeighbours()
    {
        testObject.addEdge(1, 1);
        testObject.addEdge(1, 3);
        testObject.addEdge(1, 4);
        testObject.addEdge(1, 7);
        testObject.addEdge(1, 9);
        testObject.addEdge(2, 1);
        testObject.addEdge(6, 1);

        Collection<Integer> result = testObject.getNeighbours(1);

        Assert.assertTrue(result.contains(1));
        Assert.assertTrue(result.contains(3));
        Assert.assertTrue(result.contains(4));
        Assert.assertTrue(result.contains(7));
        Assert.assertTrue(result.contains(9));
        Assert.assertFalse(result.contains(0));
        Assert.assertFalse(result.contains(2));
        Assert.assertFalse(result.contains(6));
        Assert.assertFalse(result.contains(8));
    }

    @Test
    public void testGetOutdegree()
    {
        testObject.addEdge(1, 1);
        testObject.addEdge(1, 3);
        testObject.addEdge(1, 4);
        testObject.addEdge(1, 7);
        testObject.addEdge(1, 9);
        testObject.addEdge(2, 1);
        testObject.addEdge(6, 1);

        Assert.assertEquals(5, testObject.getOutdegree(1));
    }
}
