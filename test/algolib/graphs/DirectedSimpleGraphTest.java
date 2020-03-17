// TESTY DLA GRAFÓW SKIEROWANYCH
package algolib.graphs;

import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.tuples.Pair;

public class DirectedSimpleGraphTest
{
    private DirectedSimpleGraph testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DirectedSimpleGraph(10);
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void getVerticesNumber()
    {
        int result = testObject.getVerticesNumber();

        Assertions.assertEquals(10, result);
    }

    @Test
    public void getVertices()
    {
        Object[] result = testObject.getVertices().toArray();

        Arrays.sort(result);

        Assertions.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, result);
    }

    @Test
    public void addVertex()
    {
        Integer[] neighbours = new Integer[]{2, 6};
        int result = testObject.addVertex(Arrays.asList(neighbours));
        Object[] resultNeighbours = testObject.getNeighbours(result).toArray();

        Arrays.sort(resultNeighbours);

        Assertions.assertEquals(10, result);
        Assertions.assertEquals(11, testObject.getVerticesNumber());
        Assertions.assertArrayEquals(neighbours, resultNeighbours);
    }

    @Test
    public void getEdgesNumber()
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

        Assertions.assertEquals(7, result);
    }

    @Test
    public void getEdges()
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

        Assertions.assertArrayEquals(
                new Object[]{Pair.make(1, 5), Pair.make(2, 4),
                             Pair.make(3, 6), Pair.make(6, 3),
                             Pair.make(7, 7), Pair.make(8, 0),
                             Pair.make(9, 3)}, result);
    }

    @Test
    public void addEdge()
    {
        testObject.addEdge(1, 5);
        testObject.addEdge(1, 5);
        testObject.addEdge(1, 1);

        Object[] neighbours1 = testObject.getNeighbours(1).toArray();
        Object[] neighbours5 = testObject.getNeighbours(5).toArray();

        Arrays.sort(neighbours1);
        Arrays.sort(neighbours5);

        Assertions.assertEquals(2, testObject.getEdgesNumber());
        Assertions.assertArrayEquals(new Integer[]{1, 5}, neighbours1);
        Assertions.assertArrayEquals(new Integer[]{}, neighbours5);
    }

    @Test
    public void getNeighbours()
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

        Assertions.assertArrayEquals(new Integer[]{1, 3, 4, 7, 9}, result);
    }

    @Test
    public void getOutdegree()
    {
        testObject.addEdge(1, 1);
        testObject.addEdge(1, 3);
        testObject.addEdge(1, 4);
        testObject.addEdge(1, 7);
        testObject.addEdge(1, 9);
        testObject.addEdge(2, 1);
        testObject.addEdge(6, 1);

        int result = testObject.getOutdegree(1);

        Assertions.assertEquals(5, result);
    }

    @Test
    public void getIndegree()
    {
        testObject.addEdge(1, 1);
        testObject.addEdge(3, 1);
        testObject.addEdge(4, 1);
        testObject.addEdge(7, 1);
        testObject.addEdge(9, 1);
        testObject.addEdge(1, 2);
        testObject.addEdge(1, 6);

        int result = testObject.getIndegree(1);

        Assertions.assertEquals(5, result);
    }

    @Test
    public void reverse()
    {
        testObject.addEdge(1, 2);
        testObject.addEdge(3, 5);
        testObject.addEdge(4, 9);
        testObject.addEdge(5, 4);
        testObject.addEdge(5, 7);
        testObject.addEdge(6, 2);
        testObject.addEdge(6, 6);
        testObject.addEdge(7, 8);
        testObject.addEdge(9, 1);
        testObject.addEdge(9, 6);

        testObject.reverse();

        Object[] result = testObject.getEdges().toArray();

        Arrays.sort(result);

        Assertions.assertArrayEquals(
                new Object[]{Pair.make(1, 9), Pair.make(2, 1),
                             Pair.make(2, 6), Pair.make(4, 5),
                             Pair.make(5, 3), Pair.make(6, 6),
                             Pair.make(6, 9), Pair.make(7, 5),
                             Pair.make(8, 7), Pair.make(9, 4)}, result);
    }
}
