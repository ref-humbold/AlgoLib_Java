// Tests: Algorithms for shortest paths in graph
package algolib.graphs.algorithms;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.Graph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.Vertex;
import algolib.graphs.properties.Weighted;
import algolib.tuples.Pair;

public class PathsTest
{
    private DirectedSimpleGraph<Void, Weight> directedGraph;
    private UndirectedSimpleGraph<Void, Weight> undirectedGraph;

    @BeforeEach
    public void setUp()
    {
        directedGraph = new DirectedSimpleGraph<>(Collections.nCopies(10, null));

        directedGraph.addEdge(directedGraph.getVertex(0), directedGraph.getVertex(1),
                              new Weight(4.0));
        directedGraph.addEdge(directedGraph.getVertex(1), directedGraph.getVertex(4),
                              new Weight(7.0));
        directedGraph.addEdge(directedGraph.getVertex(1), directedGraph.getVertex(7),
                              new Weight(12.0));
        directedGraph.addEdge(directedGraph.getVertex(2), directedGraph.getVertex(4),
                              new Weight(6.0));
        directedGraph.addEdge(directedGraph.getVertex(2), directedGraph.getVertex(6),
                              new Weight(8.0));
        directedGraph.addEdge(directedGraph.getVertex(3), directedGraph.getVertex(0),
                              new Weight(3.0));
        directedGraph.addEdge(directedGraph.getVertex(3), directedGraph.getVertex(7),
                              new Weight(5.0));
        directedGraph.addEdge(directedGraph.getVertex(4), directedGraph.getVertex(5),
                              new Weight(1.0));
        directedGraph.addEdge(directedGraph.getVertex(4), directedGraph.getVertex(3),
                              new Weight(10.0));
        directedGraph.addEdge(directedGraph.getVertex(5), directedGraph.getVertex(6),
                              new Weight(4.0));
        directedGraph.addEdge(directedGraph.getVertex(5), directedGraph.getVertex(8),
                              new Weight(2.0));
        directedGraph.addEdge(directedGraph.getVertex(6), directedGraph.getVertex(5),
                              new Weight(7.0));
        directedGraph.addEdge(directedGraph.getVertex(7), directedGraph.getVertex(5),
                              new Weight(2.0));
        directedGraph.addEdge(directedGraph.getVertex(7), directedGraph.getVertex(8),
                              new Weight(6.0));
        directedGraph.addEdge(directedGraph.getVertex(8), directedGraph.getVertex(9),
                              new Weight(10.0));
        directedGraph.addEdge(directedGraph.getVertex(9), directedGraph.getVertex(6),
                              new Weight(3.0));

        undirectedGraph = new UndirectedSimpleGraph<>(Collections.nCopies(10, null));

        undirectedGraph.addEdge(undirectedGraph.getVertex(0), undirectedGraph.getVertex(1),
                                new Weight(4.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(1), undirectedGraph.getVertex(4),
                                new Weight(7.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(1), undirectedGraph.getVertex(7),
                                new Weight(12.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(2), undirectedGraph.getVertex(6),
                                new Weight(8.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(3), undirectedGraph.getVertex(0),
                                new Weight(3.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(3), undirectedGraph.getVertex(7),
                                new Weight(5.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(4), undirectedGraph.getVertex(5),
                                new Weight(1.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(4), undirectedGraph.getVertex(3),
                                new Weight(10.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(5), undirectedGraph.getVertex(8),
                                new Weight(2.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(7), undirectedGraph.getVertex(5),
                                new Weight(2.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(7), undirectedGraph.getVertex(8),
                                new Weight(6.0));
        undirectedGraph.addEdge(undirectedGraph.getVertex(9), undirectedGraph.getVertex(6),
                                new Weight(3.0));
    }

    @AfterEach
    public void tearDown()
    {
        directedGraph = null;
        undirectedGraph = null;
    }

    @Test
    public void bellmanFord_WhenDirectedGraph()
    {
        // given
        Map<Vertex<Void>, Double> expected = getExpected(directedGraph,
                                                         List.of(20.0, 0.0, Weighted.INFINITY, 17.0,
                                                                 7.0, 8.0, 12.0, 12.0, 10.0, 20.0));

        directedGraph.addEdge(directedGraph.getVertex(2), directedGraph.getVertex(1),
                              new Weight(-2.0));
        // when
        Map<Vertex<Void>, Double> result =
                Paths.bellmanFord(directedGraph, directedGraph.getVertex(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(directedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void bellmanFord_WhenUndirectedGraph()
    {
        // given
        Map<Vertex<Void>, Double> expected = getExpected(undirectedGraph,
                                                         List.of(4.0, 0.0, Weighted.INFINITY, 7.0,
                                                                 7.0, 8.0, Weighted.INFINITY, 10.0,
                                                                 10.0, Weighted.INFINITY));
        // when
        Map<Vertex<Void>, Double> result =
                Paths.bellmanFord(undirectedGraph.asDirected(), undirectedGraph.getVertex(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(undirectedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void bellmanFord_WhenNegativeCycle_ThenIllegalStateException()
    {
        // given
        directedGraph.addEdge(directedGraph.getVertex(8), directedGraph.getVertex(3),
                              new Weight(-20.0));
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> Paths.bellmanFord(directedGraph, directedGraph.getVertex(1)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void dijkstra_WhenDirectedGraph()
    {
        // given
        Map<Vertex<Void>, Double> expected = getExpected(directedGraph,
                                                         List.of(20.0, 0.0, Weighted.INFINITY, 17.0,
                                                                 7.0, 8.0, 12.0, 12.0, 10.0, 20.0));
        // when
        Map<Vertex<Void>, Double> result =
                Paths.dijkstra(directedGraph, directedGraph.getVertex(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(directedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void dijkstra_WhenUndirectedGraph()
    {
        // given
        Map<Vertex<Void>, Double> expected = getExpected(undirectedGraph,
                                                         List.of(4.0, 0.0, Weighted.INFINITY, 7.0,
                                                                 7.0, 8.0, Weighted.INFINITY, 10.0,
                                                                 10.0, Weighted.INFINITY));
        // when
        Map<Vertex<Void>, Double> result =
                Paths.dijkstra(undirectedGraph, undirectedGraph.getVertex(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(undirectedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void dijkstra_WhenNegativeEdge_ThenIllegalStateException()
    {
        // given
        directedGraph.addEdge(directedGraph.getVertex(2), directedGraph.getVertex(1),
                              new Weight(-2.0));
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> Paths.dijkstra(directedGraph, directedGraph.getVertex(1)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void floydWarshall_WhenDirectedGraph()
    {
        // given
        Map<Pair<Vertex<Void>, Vertex<Void>>, Double> expected = new HashMap<>();
        double[][] distances = new double[][]{
                {0.0, 4.0, Weighted.INFINITY, 21.0, 11.0, 12.0, 16.0, 16.0, 14.0, 24.0},
                {20.0, 0.0, Weighted.INFINITY, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0},
                {18.0, -2.0, 0.0, 15.0, 5.0, 6.0, 8.0, 10.0, 8.0, 18.0},
                {3.0, 7.0, Weighted.INFINITY, 0.0, 14.0, 7.0, 11.0, 5.0, 9.0, 19.0},
                {13.0, 17.0, Weighted.INFINITY, 10.0, 0.0, 1.0, 5.0, 15.0, 3.0, 13.0},
                {Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY,
                 Weighted.INFINITY, 0.0, 4.0, Weighted.INFINITY, 2.0, 12.0},
                {Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY,
                 Weighted.INFINITY, 7.0, 0, Weighted.INFINITY, 9.0, 19.0},
                {Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY,
                 Weighted.INFINITY, 2.0, 6.0, 0.0, 4.0, 14.0},
                {Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY,
                 Weighted.INFINITY, 20.0, 13.0, Weighted.INFINITY, 0.0, 10.0},
                {Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY, Weighted.INFINITY,
                 Weighted.INFINITY, 10.0, 3.0, Weighted.INFINITY, 12.0, 0.0}};

        for(int i = 0; i < distances.length; ++i)
            for(int j = 0; j < distances[i].length; ++j)
                expected.put(Pair.of(directedGraph.getVertex(i), directedGraph.getVertex(j)),
                             distances[i][j]);

        directedGraph.addEdge(directedGraph.getVertex(2), directedGraph.getVertex(1),
                              new Weight(-2.0));
        // when
        Map<Pair<Vertex<Void>, Vertex<Void>>, Double> result = Paths.floydWarshall(directedGraph);
        // then
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void floydWarshall_WhenUndirectedGraph()
    {
        // given
        Map<Pair<Vertex<Void>, Vertex<Void>>, Double> expected = new HashMap<>();
        double[][] distances = new double[][]{
                {0.0, 4.0, Weighted.INFINITY, 3.0, 11.0, 10.0, Weighted.INFINITY, 8.0, 12.0,
                 Weighted.INFINITY},
                {4.0, 0.0, Weighted.INFINITY, 7.0, 7.0, 8.0, Weighted.INFINITY, 10.0, 10.0,
                 Weighted.INFINITY},
                {Weighted.INFINITY, Weighted.INFINITY, 0.0, Weighted.INFINITY, Weighted.INFINITY,
                 Weighted.INFINITY, 8.0, Weighted.INFINITY, Weighted.INFINITY, 11.0},
                {3.0, 7.0, Weighted.INFINITY, 0.0, 8.0, 7.0, Weighted.INFINITY, 5.0, 9.0,
                 Weighted.INFINITY},
                {11.0, 7.0, Weighted.INFINITY, 8.0, 0.0, 1.0, Weighted.INFINITY, 3.0, 3.0,
                 Weighted.INFINITY},
                {10, 8, Weighted.INFINITY, 7.0, 1.0, 0.0, Weighted.INFINITY, 2.0, 2.0,
                 Weighted.INFINITY},
                {Weighted.INFINITY, Weighted.INFINITY, 8.0, Weighted.INFINITY, Weighted.INFINITY,
                 Weighted.INFINITY, 0.0, Weighted.INFINITY, Weighted.INFINITY, 3.0},
                {8.0, 10.0, Weighted.INFINITY, 5.0, 3.0, 2.0, Weighted.INFINITY, 0.0, 4.0,
                 Weighted.INFINITY},
                {12.0, 10.0, Weighted.INFINITY, 9.0, 3.0, 2.0, Weighted.INFINITY, 4.0, 0.0,
                 Weighted.INFINITY},
                {Weighted.INFINITY, Weighted.INFINITY, 11.0, Weighted.INFINITY, Weighted.INFINITY,
                 Weighted.INFINITY, 3.0, Weighted.INFINITY, Weighted.INFINITY, 0.0}};

        for(int i = 0; i < distances.length; ++i)
            for(int j = 0; j < distances[i].length; ++j)
                expected.put(Pair.of(undirectedGraph.getVertex(i), undirectedGraph.getVertex(j)),
                             distances[i][j]);

        // when
        Map<Pair<Vertex<Void>, Vertex<Void>>, Double> result =
                Paths.floydWarshall(undirectedGraph.asDirected());
        // then
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    private Map<Vertex<Void>, Double> getExpected(Graph<Void, Weight> graph, List<Double> distances)
    {
        Map<Vertex<Void>, Double> map = new HashMap<>();

        for(int i = 0; i < distances.size(); ++i)
            map.put(graph.getVertex(i), distances.get(i));

        return map;
    }

    private static final class Weight
            implements Weighted
    {
        private double weight;

        private Weight(double weight)
        {
            this.weight = weight;
        }

        @Override
        public double getWeight()
        {
            return weight;
        }
    }
}
