// TESTY DLA ALGORYTMÓW WYZNACZANIA NAJKRÓTSZYCH ŚCIEŻEK
package algolib.graphs.algorithms;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedWeightedSimpleGraph;
import algolib.graphs.Graph;
import algolib.graphs.UndirectedWeightedSimpleGraph;
import algolib.tuples.Triple;

public class PathsTest
{
    private DirectedWeightedSimpleGraph diwgraph;
    private UndirectedWeightedSimpleGraph uwgraph;

    @BeforeEach
    public void setUp()
    {
        diwgraph = new DirectedWeightedSimpleGraph(10, Arrays.asList(Triple.make(0, 1, 4.0),
                                                                     Triple.make(1, 4, 7.0),
                                                                     Triple.make(1, 7, 12.0),
                                                                     Triple.make(2, 4, 6.0),
                                                                     Triple.make(2, 6, 8.0),
                                                                     Triple.make(3, 0, 3.0),
                                                                     Triple.make(3, 7, 5.0),
                                                                     Triple.make(4, 5, 1.0),
                                                                     Triple.make(4, 3, 10.0),
                                                                     Triple.make(5, 6, 4.0),
                                                                     Triple.make(5, 8, 2.0),
                                                                     Triple.make(6, 5, 7.0),
                                                                     Triple.make(7, 5, 2.0),
                                                                     Triple.make(7, 8, 6.0),
                                                                     Triple.make(8, 9, 10.0),
                                                                     Triple.make(9, 6, 3.0)));

        uwgraph = new UndirectedWeightedSimpleGraph(10, Arrays.asList(Triple.make(0, 1, 4.0),
                                                                      Triple.make(1, 4, 7.0),
                                                                      Triple.make(1, 7, 12.0),
                                                                      Triple.make(2, 6, 8.0),
                                                                      Triple.make(3, 0, 3.0),
                                                                      Triple.make(3, 7, 5.0),
                                                                      Triple.make(4, 5, 1.0),
                                                                      Triple.make(4, 3, 10.0),
                                                                      Triple.make(5, 8, 2.0),
                                                                      Triple.make(7, 5, 2.0),
                                                                      Triple.make(7, 8, 6.0),
                                                                      Triple.make(9, 6, 3.0)));
    }

    @AfterEach
    public void tearDown()
    {
        diwgraph = null;
        uwgraph = null;
    }

    @Test
    public void bellmanFord_WhenDirectedGraph()
    {
        int source = 1;

        diwgraph.addWeightedEdge(2, 1, -2.0);

        List<Double> result = Paths.bellmanFord(diwgraph, source);

        Assertions.assertArrayEquals(
                new Double[]{20.0, 0.0, Graph.INF, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0},
                result.toArray());
    }

    @Test
    public void bellmanFord_WhenUndirectedGraph()
    {
        int source = 1;

        List<Double> result = Paths.bellmanFord(uwgraph.asDirected(), source);
        double i = Graph.INF;

        Assertions.assertArrayEquals(new Double[]{4.0, 0.0, i, 7.0, 7.0, 8.0, i, 10.0, 10.0, i},
                                     result.toArray());
    }

    @Test
    public void bellmanFord_WhenNegativeCycle_ThenIllegalStateException()
    {
        int source = 1;

        diwgraph.addWeightedEdge(8, 3, -20.0);

        Assertions.assertThrows(IllegalStateException.class,
                                () -> Paths.bellmanFord(diwgraph, source));
    }

    @Test
    public void dijkstra_WhenDirectedGraph()
    {
        int source = 1;

        List<Double> result = Paths.dijkstra(diwgraph, source);

        Assertions.assertArrayEquals(
                new Double[]{20.0, 0.0, Graph.INF, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0},
                result.toArray());
    }

    @Test
    public void dijkstra_WhenUndirectedGraph()
    {
        int source = 1;

        List<Double> result = Paths.dijkstra(uwgraph, source);

        Assertions.assertArrayEquals(
                new Double[]{4.0, 0.0, Graph.INF, 7.0, 7.0, 8.0, Graph.INF, 10.0, 10.0, Graph.INF},
                result.toArray());
    }

    @Test
    public void dijkstra_WhenNegativeEdge_ThenIllegalStateException()
    {
        int source = 1;

        diwgraph.addWeightedEdge(2, 1, -2.0);

        Assertions.assertThrows(IllegalStateException.class,
                                () -> Paths.dijkstra(diwgraph, source));
    }

    @Test
    public void floydWarshall_WhenDirectedGraph()
    {
        diwgraph.addWeightedEdge(2, 1, -2.0);

        double[][] result = Paths.floydWarshall(diwgraph);
        double i = Graph.INF;

        Assertions.assertArrayEquals(
                new double[][]{{0.0, 4.0, i, 21.0, 11.0, 12.0, 16.0, 16.0, 14.0, 24.0},
                               {20.0, 0.0, i, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0},
                               {18.0, -2.0, 0.0, 15.0, 5.0, 6.0, 8.0, 10.0, 8.0, 18.0},
                               {3.0, 7.0, i, 0.0, 14.0, 7.0, 11.0, 5.0, 9.0, 19.0},
                               {13.0, 17.0, i, 10.0, 0.0, 1.0, 5.0, 15.0, 3.0, 13.0},
                               {i, i, i, i, i, 0.0, 4.0, i, 2.0, 12.0},
                               {i, i, i, i, i, 7.0, 0, i, 9.0, 19.0},
                               {i, i, i, i, i, 2.0, 6.0, 0.0, 4.0, 14.0},
                               {i, i, i, i, i, 20.0, 13.0, i, 0.0, 10.0},
                               {i, i, i, i, i, 10.0, 3.0, i, 12.0, 0.0}}, result);
    }

    @Test
    public void floydWarshall_WhenUndirectedGraph()
    {
        double[][] result = Paths.floydWarshall(uwgraph.asDirected());
        double i = Graph.INF;

        Assertions.assertArrayEquals(new double[][]{{0.0, 4.0, i, 3.0, 11.0, 10.0, i, 8.0, 12.0, i},
                                                    {4.0, 0.0, i, 7.0, 7.0, 8.0, i, 10.0, 10.0, i},
                                                    {i, i, 0.0, i, i, i, 8.0, i, i, 11.0},
                                                    {3.0, 7.0, i, 0.0, 8.0, 7.0, i, 5.0, 9.0, i},
                                                    {11.0, 7.0, i, 8.0, 0.0, 1.0, i, 3.0, 3.0, i},
                                                    {10, 8, i, 7.0, 1.0, 0.0, i, 2.0, 2.0, i},
                                                    {i, i, 8.0, i, i, i, 0.0, i, i, 3.0},
                                                    {8.0, 10.0, i, 5.0, 3.0, 2.0, i, 0.0, 4.0, i},
                                                    {12.0, 10.0, i, 9.0, 3.0, 2.0, i, 4.0, 0.0, i},
                                                    {i, i, 11.0, i, i, i, 3.0, i, i, 0.0}}, result);
    }
}
