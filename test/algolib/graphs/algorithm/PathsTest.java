// Tests: Algorithms for shortest paths in graph
package algolib.graphs.algorithm;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.Paths;
import algolib.graphs.properties.Weighted;
import algolib.tuples.Pair;

public class PathsTest
{
    private DirectedSimpleGraph<Void, Weight> directedGraph;
    private UndirectedSimpleGraph<Void, Weight> undirectedGraph;
    private List<Vertex<Void>> verticesDirected;
    private List<Vertex<Void>> verticesUndirected;

    @BeforeEach
    public void setUp()
    {
        directedGraph = new DirectedSimpleGraph<>(Collections.nCopies(10, null));
        verticesDirected = directedGraph.getVertices();

        directedGraph.addEdge(verticesDirected.get(0), verticesDirected.get(1), new Weight(4.0));
        directedGraph.addEdge(verticesDirected.get(1), verticesDirected.get(4), new Weight(7.0));
        directedGraph.addEdge(verticesDirected.get(1), verticesDirected.get(7), new Weight(12.0));
        directedGraph.addEdge(verticesDirected.get(2), verticesDirected.get(4), new Weight(6.0));
        directedGraph.addEdge(verticesDirected.get(2), verticesDirected.get(6), new Weight(8.0));
        directedGraph.addEdge(verticesDirected.get(3), verticesDirected.get(0), new Weight(3.0));
        directedGraph.addEdge(verticesDirected.get(3), verticesDirected.get(7), new Weight(5.0));
        directedGraph.addEdge(verticesDirected.get(4), verticesDirected.get(5), new Weight(1.0));
        directedGraph.addEdge(verticesDirected.get(4), verticesDirected.get(3), new Weight(10.0));
        directedGraph.addEdge(verticesDirected.get(5), verticesDirected.get(6), new Weight(4.0));
        directedGraph.addEdge(verticesDirected.get(5), verticesDirected.get(8), new Weight(2.0));
        directedGraph.addEdge(verticesDirected.get(6), verticesDirected.get(5), new Weight(7.0));
        directedGraph.addEdge(verticesDirected.get(7), verticesDirected.get(5), new Weight(2.0));
        directedGraph.addEdge(verticesDirected.get(7), verticesDirected.get(8), new Weight(6.0));
        directedGraph.addEdge(verticesDirected.get(8), verticesDirected.get(9), new Weight(10.0));
        directedGraph.addEdge(verticesDirected.get(9), verticesDirected.get(6), new Weight(3.0));

        undirectedGraph = new UndirectedSimpleGraph<>(Collections.nCopies(10, null));
        verticesUndirected = undirectedGraph.getVertices();

        undirectedGraph.addEdge(verticesUndirected.get(0), verticesUndirected.get(1),
                                new Weight(4.0));
        undirectedGraph.addEdge(verticesUndirected.get(1), verticesUndirected.get(4),
                                new Weight(7.0));
        undirectedGraph.addEdge(verticesUndirected.get(1), verticesUndirected.get(7),
                                new Weight(12.0));
        undirectedGraph.addEdge(verticesUndirected.get(2), verticesUndirected.get(6),
                                new Weight(8.0));
        undirectedGraph.addEdge(verticesUndirected.get(3), verticesUndirected.get(0),
                                new Weight(3.0));
        undirectedGraph.addEdge(verticesUndirected.get(3), verticesUndirected.get(7),
                                new Weight(5.0));
        undirectedGraph.addEdge(verticesUndirected.get(4), verticesUndirected.get(5),
                                new Weight(1.0));
        undirectedGraph.addEdge(verticesUndirected.get(4), verticesUndirected.get(3),
                                new Weight(10.0));
        undirectedGraph.addEdge(verticesUndirected.get(5), verticesUndirected.get(8),
                                new Weight(2.0));
        undirectedGraph.addEdge(verticesUndirected.get(7), verticesUndirected.get(5),
                                new Weight(2.0));
        undirectedGraph.addEdge(verticesUndirected.get(7), verticesUndirected.get(8),
                                new Weight(6.0));
        undirectedGraph.addEdge(verticesUndirected.get(9), verticesUndirected.get(6),
                                new Weight(3.0));
    }

    @AfterEach
    public void tearDown()
    {
        directedGraph = null;
        undirectedGraph = null;
        verticesDirected = null;
        verticesUndirected = null;
    }

    @Test
    public void bellmanFord_WhenDirectedGraph()
    {
        // given
        Map<Vertex<Void>, Double> expected = getExpected(verticesDirected,
                                                         List.of(20.0, 0.0, Weighted.INFINITY, 17.0,
                                                                 7.0, 8.0, 12.0, 12.0, 10.0, 20.0));

        directedGraph.addEdge(verticesDirected.get(2), verticesDirected.get(1), new Weight(-2.0));
        // when
        Map<Vertex<Void>, Double> result =
                Paths.bellmanFord(directedGraph, verticesDirected.get(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(verticesDirected);
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void bellmanFord_WhenUndirectedGraph()
    {
        // given
        Map<Vertex<Void>, Double> expected = getExpected(verticesUndirected,
                                                         List.of(4.0, 0.0, Weighted.INFINITY, 7.0,
                                                                 7.0, 8.0, Weighted.INFINITY, 10.0,
                                                                 10.0, Weighted.INFINITY));
        // when
        Map<Vertex<Void>, Double> result =
                Paths.bellmanFord(undirectedGraph.asDirected(), verticesUndirected.get(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(verticesUndirected);
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void bellmanFord_WhenNegativeCycle_ThenIllegalStateException()
    {
        // given
        directedGraph.addEdge(verticesDirected.get(8), verticesDirected.get(3), new Weight(-20.0));
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> Paths.bellmanFord(directedGraph, verticesDirected.get(1)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void dijkstra_WhenDirectedGraph()
    {
        // given
        Map<Vertex<Void>, Double> expected = getExpected(verticesDirected,
                                                         List.of(20.0, 0.0, Weighted.INFINITY, 17.0,
                                                                 7.0, 8.0, 12.0, 12.0, 10.0, 20.0));
        // when
        Map<Vertex<Void>, Double> result = Paths.dijkstra(directedGraph, verticesDirected.get(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(verticesDirected);
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void dijkstra_WhenUndirectedGraph()
    {
        // given
        Map<Vertex<Void>, Double> expected = getExpected(verticesUndirected,
                                                         List.of(4.0, 0.0, Weighted.INFINITY, 7.0,
                                                                 7.0, 8.0, Weighted.INFINITY, 10.0,
                                                                 10.0, Weighted.INFINITY));
        // when
        Map<Vertex<Void>, Double> result =
                Paths.dijkstra(undirectedGraph, verticesUndirected.get(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(verticesUndirected);
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void dijkstra_WhenNegativeEdge_ThenIllegalStateException()
    {
        // given
        directedGraph.addEdge(verticesDirected.get(2), verticesDirected.get(1), new Weight(-2.0));
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> Paths.dijkstra(directedGraph, verticesDirected.get(1)));
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
                expected.put(Pair.of(verticesDirected.get(i), verticesDirected.get(j)),
                             distances[i][j]);

        directedGraph.addEdge(verticesDirected.get(2), verticesDirected.get(1), new Weight(-2.0));
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
                expected.put(Pair.of(verticesUndirected.get(i), verticesUndirected.get(j)),
                             distances[i][j]);

        // when
        Map<Pair<Vertex<Void>, Vertex<Void>>, Double> result =
                Paths.floydWarshall(undirectedGraph.asDirected());
        // then
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    private Map<Vertex<Void>, Double> getExpected(List<Vertex<Void>> vertices,
                                                  List<Double> distances)
    {
        Map<Vertex<Void>, Double> map = new HashMap<>();

        for(int i = 0; i < distances.size(); ++i)
            map.put(vertices.get(i), distances.get(i));

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
