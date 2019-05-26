// TESTY DLA GRAFÓW NIESKIEROWANYCH
package refhumbold.algolib.graphs;

import java.util.Arrays;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import refhumbold.algolib.tuples.ImmutablePair;

public class UndirectedSimpleGraphTest
{
    private UndirectedSimpleGraph testObject;

    @Before
    public void setUp()
    {
        testObject = new UndirectedSimpleGraph(10);
    }

    @After
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void testGetVerticesNumber()
    {
        int result = testObject.getVerticesNumber();

        Assert.assertEquals(10, result);
    }

    @Test
    public void testGetVertices()
    {
        Object[] result = testObject.getVertices().toArray();

        Arrays.sort(result);

        Assert.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, result);
    }

    @Test
    public void testAddVertex()
    {
        Integer[] neighbours = new Integer[]{2, 6};
        int result = testObject.addVertex(Arrays.asList(neighbours));
        Object[] resultNeighbours = testObject.getNeighbours(result).toArray();

        Arrays.sort(resultNeighbours);

        Assert.assertEquals(10, result);
        Assert.assertEquals(11, testObject.getVerticesNumber());
        Assert.assertArrayEquals(neighbours, resultNeighbours);
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

        int result = testObject.getEdgesNumber();

        Assert.assertEquals(6, result);
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

        Object[] result = testObject.getEdges().toArray();

        Arrays.sort(result);

        Assert.assertArrayEquals(new Object[]{ImmutablePair.make(0, 8), ImmutablePair.make(1, 5),
                                              ImmutablePair.make(2, 4), ImmutablePair.make(3, 6),
                                              ImmutablePair.make(3, 9), ImmutablePair.make(7, 7)},
                                 result);
    }

    @Test
    public void testAddEdge()
    {
        testObject.addEdge(1, 5);
        testObject.addEdge(1, 5);
        testObject.addEdge(5, 1);
        testObject.addEdge(1, 1);

        Object[] neighbours1 = testObject.getNeighbours(1).toArray();
        Object[] neighbours5 = testObject.getNeighbours(5).toArray();

        Arrays.sort(neighbours1);
        Arrays.sort(neighbours5);

        Assert.assertEquals(2, testObject.getEdgesNumber());
        Assert.assertArrayEquals(new Integer[]{1, 5}, neighbours1);
        Assert.assertArrayEquals(new Integer[]{1}, neighbours5);
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

        Object[] result = testObject.getNeighbours(1).toArray();

        Arrays.sort(result);

        Assert.assertArrayEquals(new Integer[]{1, 2, 3, 4, 6, 7, 9}, result);
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

        int result = testObject.getOutdegree(1);

        Assert.assertEquals(7, result);
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

        int result = testObject.getIndegree(1);

        Assert.assertEquals(7, result);
    }

    @Test
    public void testAsDirected()
    {
        testObject.addEdge(7, 7);
        testObject.addEdge(1, 5);
        testObject.addEdge(2, 4);
        testObject.addEdge(8, 0);
        testObject.addEdge(6, 3);
        testObject.addEdge(3, 6);
        testObject.addEdge(9, 3);
        testObject.addEdge(8, 0);

        DirectedSimpleGraph result = testObject.asDirected();
        Object[] expectedVertices = testObject.getVertices().toArray();
        Object[] resultVertices = result.getVertices().toArray();
        Object[] resultEdges = result.getEdges().toArray();

        Arrays.sort(expectedVertices);
        Arrays.sort(resultVertices);
        Arrays.sort(resultEdges);

        Assert.assertArrayEquals(expectedVertices, resultVertices);
        Assert.assertArrayEquals(new Object[]{ImmutablePair.make(0, 8), ImmutablePair.make(1, 5),
                                              ImmutablePair.make(2, 4), ImmutablePair.make(3, 6),
                                              ImmutablePair.make(3, 9), ImmutablePair.make(4, 2),
                                              ImmutablePair.make(5, 1), ImmutablePair.make(6, 3),
                                              ImmutablePair.make(7, 7), ImmutablePair.make(8, 0),
                                              ImmutablePair.make(9, 3)}, resultEdges);
    }
}
