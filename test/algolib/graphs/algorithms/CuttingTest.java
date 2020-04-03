// TESTY DLA ALGORYTMÓW WYSZUKIWANIA MOSTÓW I PUNKTÓW ARTYKULACJI
package algolib.graphs.algorithms;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.UndirectedGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.tuples.ComparablePair;
import algolib.tuples.Pair;

public class CuttingTest
{
    private UndirectedGraph graph;

    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
        graph = null;
    }

    @Test
    public void findBridges_WhenPresentBridges()
    {
        graph = new UndirectedSimpleGraph(12, Arrays.asList(Pair.make(0, 1), Pair.make(0, 2),
                                                            Pair.make(0, 7), Pair.make(1, 2),
                                                            Pair.make(1, 3), Pair.make(1, 4),
                                                            Pair.make(3, 5), Pair.make(4, 5),
                                                            Pair.make(5, 6), Pair.make(7, 8),
                                                            Pair.make(7, 9), Pair.make(7, 11),
                                                            Pair.make(8, 9), Pair.make(9, 10),
                                                            Pair.make(9, 11), Pair.make(10, 11)));

        Object[] result = Cutting.findEdgeCut(graph).toArray();

        Arrays.sort(result);

        Assertions.assertArrayEquals(new Object[]{Pair.make(0, 7), Pair.make(5, 6)}, result);
    }

    @Test
    public void findBridges_WhenNoBridges()
    {
        graph = new UndirectedSimpleGraph(6, Arrays.asList(Pair.make(0, 1), Pair.make(0, 2),
                                                           Pair.make(1, 2), Pair.make(1, 3),
                                                           Pair.make(1, 4), Pair.make(3, 5),
                                                           Pair.make(4, 5)));

        Collection<ComparablePair<Integer, Integer>> result = Cutting.findEdgeCut(graph);

        Assertions.assertArrayEquals(new Object[]{}, result.toArray());
    }

    @Test
    public void findVertexSeparators_WhenPresentSeparators()
    {
        graph = new UndirectedSimpleGraph(12, Arrays.asList(Pair.make(0, 1), Pair.make(0, 2),
                                                            Pair.make(0, 7), Pair.make(1, 2),
                                                            Pair.make(1, 3), Pair.make(1, 4),
                                                            Pair.make(3, 5), Pair.make(4, 5),
                                                            Pair.make(5, 6), Pair.make(7, 8),
                                                            Pair.make(7, 9), Pair.make(7, 11),
                                                            Pair.make(8, 9), Pair.make(9, 10),
                                                            Pair.make(9, 11), Pair.make(10, 11)));

        Object[] result = Cutting.findVertexCut(graph).toArray();

        Arrays.sort(result);

        Assertions.assertArrayEquals(new Integer[]{0, 1, 5, 7}, result);
    }

    @Test
    public void findVertexSeparators_WhenNoSeparators()
    {
        graph = new UndirectedSimpleGraph(6, Arrays.asList(Pair.make(0, 1), Pair.make(0, 2),
                                                           Pair.make(1, 2), Pair.make(1, 3),
                                                           Pair.make(1, 4), Pair.make(2, 3),
                                                           Pair.make(3, 5), Pair.make(4, 5)));

        Collection<Integer> result = Cutting.findVertexCut(graph);

        Assertions.assertArrayEquals(new Object[]{}, result.toArray());
    }
}
