// Tests: Algorithms for shortest paths
package algolib.graphs.algorithms;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
        diwgraph = new DirectedWeightedSimpleGraph(10, Arrays.asList(Triple.of(0, 1, 4.0),
                                                                     Triple.of(1, 4, 7.0),
                                                                     Triple.of(1, 7, 12.0),
                                                                     Triple.of(2, 4, 6.0),
                                                                     Triple.of(2, 6, 8.0),
                                                                     Triple.of(3, 0, 3.0),
                                                                     Triple.of(3, 7, 5.0),
                                                                     Triple.of(4, 5, 1.0),
                                                                     Triple.of(4, 3, 10.0),
                                                                     Triple.of(5, 6, 4.0),
                                                                     Triple.of(5, 8, 2.0),
                                                                     Triple.of(6, 5, 7.0),
                                                                     Triple.of(7, 5, 2.0),
                                                                     Triple.of(7, 8, 6.0),
                                                                     Triple.of(8, 9, 10.0),
                                                                     Triple.of(9, 6, 3.0)));

        uwgraph = new UndirectedWeightedSimpleGraph(10, Arrays.asList(Triple.of(0, 1, 4.0),
                                                                      Triple.of(1, 4, 7.0),
                                                                      Triple.of(1, 7, 12.0),
                                                                      Triple.of(2, 6, 8.0),
                                                                      Triple.of(3, 0, 3.0),
                                                                      Triple.of(3, 7, 5.0),
                                                                      Triple.of(4, 5, 1.0),
                                                                      Triple.of(4, 3, 10.0),
                                                                      Triple.of(5, 8, 2.0),
                                                                      Triple.of(7, 5, 2.0),
                                                                      Triple.of(7, 8, 6.0),
                                                                      Triple.of(9, 6, 3.0)));
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
        // given
        diwgraph.addWeightedEdge(2, 1, -2.0);
        // when
        List<Double> result = Paths.bellmanFord(diwgraph, 1);
        // then
        Assertions.assertThat(result)
                  .containsExactly(20.0, 0.0, Graph.INF, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0);
    }

    @Test
    public void bellmanFord_WhenUndirectedGraph()
    {
        // when
        List<Double> result = Paths.bellmanFord(uwgraph.asDirected(), 1);
        // then
        Assertions.assertThat(result)
                  .containsExactly(4.0, 0.0, Graph.INF, 7.0, 7.0, 8.0, Graph.INF, 10.0, 10.0,
                                   Graph.INF);
    }

    @Test
    public void bellmanFord_WhenNegativeCycle_ThenIllegalStateException()
    {
        // given
        diwgraph.addWeightedEdge(8, 3, -20.0);
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Paths.bellmanFord(diwgraph, 1));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void dijkstra_WhenDirectedGraph()
    {
        // when
        List<Double> result = Paths.dijkstra(diwgraph, 1);
        // then
        Assertions.assertThat(result)
                  .containsExactly(20.0, 0.0, Graph.INF, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0);
    }

    @Test
    public void dijkstra_WhenUndirectedGraph()
    {
        // when
        List<Double> result = Paths.dijkstra(uwgraph, 1);
        // then
        Assertions.assertThat(result)
                  .containsExactly(4.0, 0.0, Graph.INF, 7.0, 7.0, 8.0, Graph.INF, 10.0, 10.0,
                                   Graph.INF);
    }

    @Test
    public void dijkstra_WhenNegativeEdge_ThenIllegalStateException()
    {
        // given
        diwgraph.addWeightedEdge(2, 1, -2.0);
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Paths.dijkstra(diwgraph, 1));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void floydWarshall_WhenDirectedGraph()
    {
        // given
        diwgraph.addWeightedEdge(2, 1, -2.0);
        // when
        double[][] result = Paths.floydWarshall(diwgraph);
        // then
        Assertions.assertThat(result)
                  .isEqualTo(new double[][]{
                          {0.0, 4.0, Graph.INF, 21.0, 11.0, 12.0, 16.0, 16.0, 14.0, 24.0},
                          {20.0, 0.0, Graph.INF, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0},
                          {18.0, -2.0, 0.0, 15.0, 5.0, 6.0, 8.0, 10.0, 8.0, 18.0},
                          {3.0, 7.0, Graph.INF, 0.0, 14.0, 7.0, 11.0, 5.0, 9.0, 19.0},
                          {13.0, 17.0, Graph.INF, 10.0, 0.0, 1.0, 5.0, 15.0, 3.0, 13.0},
                          {Graph.INF, Graph.INF, Graph.INF, Graph.INF, Graph.INF, 0.0, 4.0,
                           Graph.INF, 2.0, 12.0},
                          {Graph.INF, Graph.INF, Graph.INF, Graph.INF, Graph.INF, 7.0, 0, Graph.INF,
                           9.0, 19.0},
                          {Graph.INF, Graph.INF, Graph.INF, Graph.INF, Graph.INF, 2.0, 6.0, 0.0,
                           4.0, 14.0},
                          {Graph.INF, Graph.INF, Graph.INF, Graph.INF, Graph.INF, 20.0, 13.0,
                           Graph.INF, 0.0, 10.0},
                          {Graph.INF, Graph.INF, Graph.INF, Graph.INF, Graph.INF, 10.0, 3.0,
                           Graph.INF, 12.0, 0.0}});
    }

    @Test
    public void floydWarshall_WhenUndirectedGraph()
    {
        // when
        double[][] result = Paths.floydWarshall(uwgraph.asDirected());
        // then
        Assertions.assertThat(result)
                  .isEqualTo(new double[][]{
                          {0.0, 4.0, Graph.INF, 3.0, 11.0, 10.0, Graph.INF, 8.0, 12.0, Graph.INF},
                          {4.0, 0.0, Graph.INF, 7.0, 7.0, 8.0, Graph.INF, 10.0, 10.0, Graph.INF},
                          {Graph.INF, Graph.INF, 0.0, Graph.INF, Graph.INF, Graph.INF, 8.0,
                           Graph.INF, Graph.INF, 11.0},
                          {3.0, 7.0, Graph.INF, 0.0, 8.0, 7.0, Graph.INF, 5.0, 9.0, Graph.INF},
                          {11.0, 7.0, Graph.INF, 8.0, 0.0, 1.0, Graph.INF, 3.0, 3.0, Graph.INF},
                          {10, 8, Graph.INF, 7.0, 1.0, 0.0, Graph.INF, 2.0, 2.0, Graph.INF},
                          {Graph.INF, Graph.INF, 8.0, Graph.INF, Graph.INF, Graph.INF, 0.0,
                           Graph.INF, Graph.INF, 3.0},
                          {8.0, 10.0, Graph.INF, 5.0, 3.0, 2.0, Graph.INF, 0.0, 4.0, Graph.INF},
                          {12.0, 10.0, Graph.INF, 9.0, 3.0, 2.0, Graph.INF, 4.0, 0.0, Graph.INF},
                          {Graph.INF, Graph.INF, 11.0, Graph.INF, Graph.INF, Graph.INF, 3.0,
                           Graph.INF, Graph.INF, 0.0}});
    }
}
