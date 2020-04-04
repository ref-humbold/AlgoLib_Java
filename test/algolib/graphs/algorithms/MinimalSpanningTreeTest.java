// TESTY DLA ALGORYTMÓW MINIMALNEGO DRZEWA SPINAJĄCEGO
package algolib.graphs.algorithms;

import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.UndirectedWeightedSimpleGraph;
import algolib.tuples.Triple;

public class MinimalSpanningTreeTest
{
    private UndirectedWeightedSimpleGraph graph;

    @BeforeEach
    public void setUp()
    {
        graph = new UndirectedWeightedSimpleGraph(5, Arrays.asList(Triple.of(0, 1, -1.0),
                                                                   Triple.of(0, 2, 4.0),
                                                                   Triple.of(1, 2, 9.0),
                                                                   Triple.of(1, 3, 7.0),
                                                                   Triple.of(1, 4, 12.0),
                                                                   Triple.of(2, 4, 6.0),
                                                                   Triple.of(3, 4, 3.0)));
    }

    @AfterEach
    public void tearDown()
    {
        graph = null;
    }

    @Test
    public void kruskal()
    {
        double result = MinimalSpanningTree.kruskal(graph);

        Assertions.assertEquals(12.0, result, 0.000001);
    }

    @Test
    public void prim()
    {
        double result = MinimalSpanningTree.prim(graph, 0);

        Assertions.assertEquals(12.0, result, 0.000001);
    }

    @Test
    public void prim_WhenDifferentSources()
    {
        double result1 = MinimalSpanningTree.prim(graph, 1);
        double result4 = MinimalSpanningTree.prim(graph, 4);

        Assertions.assertEquals(12.0, result1, 0.000001);
        Assertions.assertEquals(12.0, result4, 0.000001);
    }
}
