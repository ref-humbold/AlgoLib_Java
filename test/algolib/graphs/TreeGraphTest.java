// TESTY DLA STRUKTURY GRAFÓW DRZEW
package algolib.graphs;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.tuples.Pair;

public class TreeGraphTest
{
    private TreeGraph testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new TreeGraph(10, Arrays.asList(Pair.of(0, 6), Pair.of(1, 2), Pair.of(2, 3),
                                                     Pair.of(3, 4), Pair.of(4, 5), Pair.of(6, 4),
                                                     Pair.of(7, 3), Pair.of(8, 3), Pair.of(9, 7)));
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void addVertex_WhenOneNeighbour()
    {
        int result = testObject.addVertex(Collections.singletonList(2));

        Assertions.assertEquals(10, result);
        Assertions.assertArrayEquals(new Object[]{2}, testObject.getNeighbours(result).toArray());
    }

    @Test
    public void addVertex_WhenNoNeighbours()
    {
        Assertions.assertThrows(NotConnectedException.class,
                                () -> testObject.addVertex(Collections.emptyList()));
    }

    @Test
    public void addVertex_WhenManyNeighbours()
    {
        Assertions.assertThrows(CycleException.class,
                                () -> testObject.addVertex(Arrays.asList(2, 5, 9)));
    }

    @Test
    public void addEdge()
    {
        int vertex1 = 1;
        int vertex2 = 5;

        Assertions.assertThrows(CycleException.class, () -> testObject.addEdge(vertex1, vertex2));
    }
}
