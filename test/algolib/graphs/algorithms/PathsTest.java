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
import algolib.graphs.Graph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.Vertex;
import algolib.graphs.properties.Weighted;
import algolib.tuples.Pair;

// Tests: Algorithms for shortest paths in graph
public class PathsTest
{
    private static final double INF = Weighted.INFINITY;
    private DirectedSimpleGraph<Integer, Void, Weight> directedGraph;
    private UndirectedSimpleGraph<Integer, Void, Weight> undirectedGraph;

    @BeforeEach
    public void setUp()
    {
        directedGraph = new DirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));
        directedGraph.addEdgeBetween(directedGraph.getVertex(0), directedGraph.getVertex(1),
                                     new Weight(4.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(1), directedGraph.getVertex(4),
                                     new Weight(7.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(1), directedGraph.getVertex(7),
                                     new Weight(12.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(2), directedGraph.getVertex(4),
                                     new Weight(6.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(2), directedGraph.getVertex(6),
                                     new Weight(8.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(3), directedGraph.getVertex(0),
                                     new Weight(3.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(3), directedGraph.getVertex(7),
                                     new Weight(5.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(4), directedGraph.getVertex(5),
                                     new Weight(1.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(4), directedGraph.getVertex(3),
                                     new Weight(10.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(5), directedGraph.getVertex(6),
                                     new Weight(4.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(5), directedGraph.getVertex(8),
                                     new Weight(2.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(6), directedGraph.getVertex(5),
                                     new Weight(7.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(7), directedGraph.getVertex(5),
                                     new Weight(2.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(7), directedGraph.getVertex(8),
                                     new Weight(6.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(8), directedGraph.getVertex(9),
                                     new Weight(10.0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(9), directedGraph.getVertex(6),
                                     new Weight(3.0));

        undirectedGraph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(0), undirectedGraph.getVertex(1),
                                       new Weight(4.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(1), undirectedGraph.getVertex(4),
                                       new Weight(7.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(1), undirectedGraph.getVertex(7),
                                       new Weight(12.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(2), undirectedGraph.getVertex(6),
                                       new Weight(8.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(3), undirectedGraph.getVertex(0),
                                       new Weight(3.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(3), undirectedGraph.getVertex(7),
                                       new Weight(5.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(4), undirectedGraph.getVertex(5),
                                       new Weight(1.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(4), undirectedGraph.getVertex(3),
                                       new Weight(10.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(5), undirectedGraph.getVertex(8),
                                       new Weight(2.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(7), undirectedGraph.getVertex(5),
                                       new Weight(2.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(7), undirectedGraph.getVertex(8),
                                       new Weight(6.0));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(9), undirectedGraph.getVertex(6),
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
        List<Double> distances = List.of(20.0, 0.0, INF, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0);
        Map<Vertex<Integer>, Double> expected = fromList(directedGraph, distances);

        directedGraph.addEdgeBetween(directedGraph.getVertex(2), directedGraph.getVertex(1),
                                     new Weight(-2.0));
        // when
        Map<Vertex<Integer>, Double> result =
                Paths.bellmanFord(directedGraph, directedGraph.getVertex(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(directedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void bellmanFord_WhenUndirectedGraph()
    {
        // given
        List<Double> distances = List.of(4.0, 0.0, INF, 7.0, 7.0, 8.0, INF, 10.0, 10.0, INF);
        Map<Vertex<Integer>, Double> expected = fromList(undirectedGraph, distances);
        // when
        Map<Vertex<Integer>, Double> result =
                Paths.bellmanFord(undirectedGraph.asDirected(), undirectedGraph.getVertex(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(undirectedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void bellmanFord_WhenNegativeCycle_ThenIllegalStateException()
    {
        // given
        directedGraph.addEdgeBetween(directedGraph.getVertex(8), directedGraph.getVertex(3),
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
        List<Double> distances = List.of(20.0, 0.0, INF, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0);
        Map<Vertex<Integer>, Double> expected = fromList(directedGraph, distances);
        // when
        Map<Vertex<Integer>, Double> result =
                Paths.dijkstra(directedGraph, directedGraph.getVertex(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(directedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void dijkstra_WhenUndirectedGraph()
    {
        // given
        List<Double> distances = List.of(4.0, 0.0, INF, 7.0, 7.0, 8.0, INF, 10.0, 10.0, INF);
        Map<Vertex<Integer>, Double> expected = fromList(undirectedGraph, distances);
        // when
        Map<Vertex<Integer>, Double> result =
                Paths.dijkstra(undirectedGraph, undirectedGraph.getVertex(1));
        // then
        Assertions.assertThat(result).containsOnlyKeys(undirectedGraph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void dijkstra_WhenNegativeEdge_ThenIllegalStateException()
    {
        // given
        directedGraph.addEdgeBetween(directedGraph.getVertex(2), directedGraph.getVertex(1),
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
        double[][] distances = {{0.0, 4.0, INF, 21.0, 11.0, 12.0, 16.0, 16.0, 14.0, 24.0},
                                {20.0, 0.0, INF, 17.0, 7.0, 8.0, 12.0, 12.0, 10.0, 20.0},
                                {18.0, -2.0, 0.0, 15.0, 5.0, 6.0, 8.0, 10.0, 8.0, 18.0},
                                {3.0, 7.0, INF, 0.0, 14.0, 7.0, 11.0, 5.0, 9.0, 19.0},
                                {13.0, 17.0, INF, 10.0, 0.0, 1.0, 5.0, 15.0, 3.0, 13.0},
                                {INF, INF, INF, INF, INF, 0.0, 4.0, INF, 2.0, 12.0},
                                {INF, INF, INF, INF, INF, 7.0, 0, INF, 9.0, 19.0},
                                {INF, INF, INF, INF, INF, 2.0, 6.0, 0.0, 4.0, 14.0},
                                {INF, INF, INF, INF, INF, 20.0, 13.0, INF, 0.0, 10.0},
                                {INF, INF, INF, INF, INF, 10.0, 3.0, INF, 12.0, 0.0}};
        Map<Pair<Vertex<Integer>, Vertex<Integer>>, Double> expected =
                fromMatrix(undirectedGraph, distances);

        directedGraph.addEdgeBetween(directedGraph.getVertex(2), directedGraph.getVertex(1),
                                     new Weight(-2.0));
        // when
        Map<Pair<Vertex<Integer>, Vertex<Integer>>, Double> result =
                Paths.floydWarshall(directedGraph);
        // then
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void floydWarshall_WhenUndirectedGraph()
    {
        // given
        double[][] distances = {{0.0, 4.0, INF, 3.0, 11.0, 10.0, INF, 8.0, 12.0, INF},
                                {4.0, 0.0, INF, 7.0, 7.0, 8.0, INF, 10.0, 10.0, INF},
                                {INF, INF, 0.0, INF, INF, INF, 8.0, INF, INF, 11.0},
                                {3.0, 7.0, INF, 0.0, 8.0, 7.0, INF, 5.0, 9.0, INF},
                                {11.0, 7.0, INF, 8.0, 0.0, 1.0, INF, 3.0, 3.0, INF},
                                {10, 8, INF, 7.0, 1.0, 0.0, INF, 2.0, 2.0, INF},
                                {INF, INF, 8.0, INF, INF, INF, 0.0, INF, INF, 3.0},
                                {8.0, 10.0, INF, 5.0, 3.0, 2.0, INF, 0.0, 4.0, INF},
                                {12.0, 10.0, INF, 9.0, 3.0, 2.0, INF, 4.0, 0.0, INF},
                                {INF, INF, 11.0, INF, INF, INF, 3.0, INF, INF, 0.0}};
        Map<Pair<Vertex<Integer>, Vertex<Integer>>, Double> expected =
                fromMatrix(undirectedGraph, distances);

        // when
        Map<Pair<Vertex<Integer>, Vertex<Integer>>, Double> result =
                Paths.floydWarshall(undirectedGraph.asDirected());
        // then
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    private Map<Vertex<Integer>, Double> fromList(Graph<Integer, Void, Weight> graph,
                                                  List<Double> distances)
    {
        Map<Vertex<Integer>, Double> map = new HashMap<>();

        for(int i = 0; i < distances.size(); ++i)
            map.put(graph.getVertex(i), distances.get(i));

        return map;
    }

    private Map<Pair<Vertex<Integer>, Vertex<Integer>>, Double> fromMatrix(
            Graph<Integer, Void, Weight> graph, double[][] distances)
    {
        Map<Pair<Vertex<Integer>, Vertex<Integer>>, Double> expected = new HashMap<>();

        for(int i = 0; i < distances.length; ++i)
            for(int j = 0; j < distances[i].length; ++j)
                expected.put(Pair.of(graph.getVertex(i), graph.getVertex(j)), distances[i][j]);

        return expected;
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
