// Tests: Algorithms for shortest paths in graph
package algolib.graphs.algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.properties.Weighted;
import algolib.tuples.Pair;

public class PathsTest
{
    private DirectedSimpleGraph<Integer, Void, Weight> directedGraph;
    private UndirectedSimpleGraph<Integer, Void, Weight> undirectedGraph;

    @BeforeEach
    public void setUp()
    {
        directedGraph = new DirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));

        directedGraph.addEdge(0, 1, new Weight(4.0));
        directedGraph.addEdge(1, 4, new Weight(7.0));
        directedGraph.addEdge(1, 7, new Weight(12.0));
        directedGraph.addEdge(2, 4, new Weight(6.0));
        directedGraph.addEdge(2, 6, new Weight(8.0));
        directedGraph.addEdge(3, 0, new Weight(3.0));
        directedGraph.addEdge(3, 7, new Weight(5.0));
        directedGraph.addEdge(4, 5, new Weight(1.0));
        directedGraph.addEdge(4, 3, new Weight(10.0));
        directedGraph.addEdge(5, 6, new Weight(4.0));
        directedGraph.addEdge(5, 8, new Weight(2.0));
        directedGraph.addEdge(6, 5, new Weight(7.0));
        directedGraph.addEdge(7, 5, new Weight(2.0));
        directedGraph.addEdge(7, 8, new Weight(6.0));
        directedGraph.addEdge(8, 9, new Weight(10.0));
        directedGraph.addEdge(9, 6, new Weight(3.0));

        undirectedGraph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));

        undirectedGraph.addEdge(0, 1, new Weight(4.0));
        undirectedGraph.addEdge(1, 4, new Weight(7.0));
        undirectedGraph.addEdge(1, 7, new Weight(12.0));
        undirectedGraph.addEdge(2, 6, new Weight(8.0));
        undirectedGraph.addEdge(3, 0, new Weight(3.0));
        undirectedGraph.addEdge(3, 7, new Weight(5.0));
        undirectedGraph.addEdge(4, 5, new Weight(1.0));
        undirectedGraph.addEdge(4, 3, new Weight(10.0));
        undirectedGraph.addEdge(5, 8, new Weight(2.0));
        undirectedGraph.addEdge(7, 5, new Weight(2.0));
        undirectedGraph.addEdge(7, 8, new Weight(6.0));
        undirectedGraph.addEdge(9, 6, new Weight(3.0));
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
        Map<Integer, Double> expected = getExpected(
                List.of(20.0, 0.0, Paths.INFINITY, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0));

        directedGraph.addEdge(2, 1, new Weight(-2.0));
        // when
        Map<Integer, Double> result = Paths.bellmanFord(directedGraph, 1);
        // then
        Assertions.assertThat(result).containsOnlyKeys(directedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void bellmanFord_WhenUndirectedGraph()
    {
        // given
        Map<Integer, Double> expected = getExpected(
                List.of(4.0, 0.0, Paths.INFINITY, 7.0, 7.0, 8.0, Paths.INFINITY, 10.0, 10.0,
                        Paths.INFINITY));
        // when
        Map<Integer, Double> result = Paths.bellmanFord(undirectedGraph.asDirected(), 1);
        // then
        Assertions.assertThat(result).containsOnlyKeys(undirectedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void bellmanFord_WhenNegativeCycle_ThenIllegalStateException()
    {
        // given
        directedGraph.addEdge(8, 3, new Weight(-20.0));
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Paths.bellmanFord(directedGraph, 1));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void dijkstra_WhenDirectedGraph()
    {
        // given
        Map<Integer, Double> expected = getExpected(
                List.of(20.0, 0.0, Paths.INFINITY, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0));
        // when
        Map<Integer, Double> result = Paths.dijkstra(directedGraph, 1);
        // then
        Assertions.assertThat(result).containsOnlyKeys(directedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void dijkstra_WhenUndirectedGraph()
    {
        // given
        Map<Integer, Double> expected = getExpected(
                List.of(4.0, 0.0, Paths.INFINITY, 7.0, 7.0, 8.0, Paths.INFINITY, 10.0, 10.0,
                        Paths.INFINITY));
        // when
        Map<Integer, Double> result = Paths.dijkstra(undirectedGraph, 1);
        // then
        Assertions.assertThat(result).containsOnlyKeys(undirectedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void dijkstra_WhenNegativeEdge_ThenIllegalStateException()
    {
        // given
        directedGraph.addEdge(2, 1, new Weight(-2.0));
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Paths.dijkstra(directedGraph, 1));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void floydWarshall_WhenDirectedGraph()
    {
        // given
        Map<Pair<Integer, Integer>, Double> expected = new HashMap<>();
        double[][] distances =
                new double[][]{{0.0, 4.0, Paths.INFINITY, 21.0, 11.0, 12.0, 16.0, 16.0, 14.0, 24.0},
                               {20.0, 0.0, Paths.INFINITY, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0},
                               {18.0, -2.0, 0.0, 15.0, 5.0, 6.0, 8.0, 10.0, 8.0, 18.0},
                               {3.0, 7.0, Paths.INFINITY, 0.0, 14.0, 7.0, 11.0, 5.0, 9.0, 19.0},
                               {13.0, 17.0, Paths.INFINITY, 10.0, 0.0, 1.0, 5.0, 15.0, 3.0, 13.0},
                               {Paths.INFINITY, Paths.INFINITY, Paths.INFINITY, Paths.INFINITY,
                                Paths.INFINITY, 0.0, 4.0, Paths.INFINITY, 2.0, 12.0},
                               {Paths.INFINITY, Paths.INFINITY, Paths.INFINITY, Paths.INFINITY,
                                Paths.INFINITY, 7.0, 0, Paths.INFINITY, 9.0, 19.0},
                               {Paths.INFINITY, Paths.INFINITY, Paths.INFINITY, Paths.INFINITY,
                                Paths.INFINITY, 2.0, 6.0, 0.0, 4.0, 14.0},
                               {Paths.INFINITY, Paths.INFINITY, Paths.INFINITY, Paths.INFINITY,
                                Paths.INFINITY, 20.0, 13.0, Paths.INFINITY, 0.0, 10.0},
                               {Paths.INFINITY, Paths.INFINITY, Paths.INFINITY, Paths.INFINITY,
                                Paths.INFINITY, 10.0, 3.0, Paths.INFINITY, 12.0, 0.0}};

        for(int i = 0; i < distances.length; ++i)
            for(int j = 0; j < distances[i].length; ++j)
                expected.put(Pair.of(i, j), distances[i][j]);

        directedGraph.addEdge(2, 1, new Weight(-2.0));
        // when
        Map<Pair<Integer, Integer>, Double> result = Paths.floydWarshall(directedGraph);
        // then
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void floydWarshall_WhenUndirectedGraph()
    {
        // given
        Map<Pair<Integer, Integer>, Double> expected = new HashMap<>();
        double[][] distances = new double[][]{
                {0.0, 4.0, Paths.INFINITY, 3.0, 11.0, 10.0, Paths.INFINITY, 8.0, 12.0,
                 Paths.INFINITY},
                {4.0, 0.0, Paths.INFINITY, 7.0, 7.0, 8.0, Paths.INFINITY, 10.0, 10.0,
                 Paths.INFINITY},
                {Paths.INFINITY, Paths.INFINITY, 0.0, Paths.INFINITY, Paths.INFINITY,
                 Paths.INFINITY, 8.0, Paths.INFINITY, Paths.INFINITY, 11.0},
                {3.0, 7.0, Paths.INFINITY, 0.0, 8.0, 7.0, Paths.INFINITY, 5.0, 9.0, Paths.INFINITY},
                {11.0, 7.0, Paths.INFINITY, 8.0, 0.0, 1.0, Paths.INFINITY, 3.0, 3.0,
                 Paths.INFINITY},
                {10, 8, Paths.INFINITY, 7.0, 1.0, 0.0, Paths.INFINITY, 2.0, 2.0, Paths.INFINITY},
                {Paths.INFINITY, Paths.INFINITY, 8.0, Paths.INFINITY, Paths.INFINITY,
                 Paths.INFINITY, 0.0, Paths.INFINITY, Paths.INFINITY, 3.0},
                {8.0, 10.0, Paths.INFINITY, 5.0, 3.0, 2.0, Paths.INFINITY, 0.0, 4.0,
                 Paths.INFINITY},
                {12.0, 10.0, Paths.INFINITY, 9.0, 3.0, 2.0, Paths.INFINITY, 4.0, 0.0,
                 Paths.INFINITY},
                {Paths.INFINITY, Paths.INFINITY, 11.0, Paths.INFINITY, Paths.INFINITY,
                 Paths.INFINITY, 3.0, Paths.INFINITY, Paths.INFINITY, 0.0}};

        for(int i = 0; i < distances.length; ++i)
            for(int j = 0; j < distances[i].length; ++j)
                expected.put(Pair.of(i, j), distances[i][j]);

        // when
        Map<Pair<Integer, Integer>, Double> result =
                Paths.floydWarshall(undirectedGraph.asDirected());
        // then
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    private Map<Integer, Double> getExpected(List<Double> distances)
    {
        Map<Integer, Double> map = new HashMap<>();

        for(int i = 0; i < distances.size(); ++i)
            map.put(i, distances.get(i));

        return map;
    }

    private static final class Weight
            implements Weighted
    {
        private final double weight;

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
