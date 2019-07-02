// TESTY DLA STRUKTURY GRAFÓW DRZEW
package algolib.graphs;

import java.util.Arrays;
import java.util.Collections;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algolib.tuples.ImmutablePair;

public class TreeGraphTest
{
    private TreeGraph testObject;

    @Before
    public void setUp()
    {
        testObject = new TreeGraph(10,
                                   Arrays.asList(ImmutablePair.make(0, 6), ImmutablePair.make(1, 2),
                                                 ImmutablePair.make(2, 3), ImmutablePair.make(3, 4),
                                                 ImmutablePair.make(4, 5), ImmutablePair.make(6, 4),
                                                 ImmutablePair.make(7, 3), ImmutablePair.make(8, 3),
                                                 ImmutablePair.make(9, 7)));
    }

    @After
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void testAddVertexWhenOneNeighbour()
    {
        int result = testObject.addVertex(Collections.singletonList(2));

        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new Object[]{2}, testObject.getNeighbours(result).toArray());
    }

    @Test(expected = NotConnectedException.class)
    public void testAddVertexWhenNoNeighbours()
    {
        testObject.addVertex(Collections.emptyList());
    }

    @Test(expected = CycleException.class)
    public void testAddVertexWhenManyNeighbours()
    {
        testObject.addVertex(Arrays.asList(2, 5, 9));
    }

    @Test(expected = CycleException.class)
    public void testAddEdge()
    {
        int vertex1 = 1;
        int vertex2 = 5;

        testObject.addEdge(vertex1, vertex2);
    }
}
