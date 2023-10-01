package algolib.graphs.algorithms;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.UndirectedGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.properties.Weighted;

// Tests: Algorithms for minimal spanning tree.
public class MinimalSpanningTreeTest
{
    private static final Offset<Double> OFFSET = Offset.offset(1e-6);
    private UndirectedSimpleGraph<Integer, Void, Weight> graph;

    @BeforeEach
    public void setUp()
    {
        graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 5).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(1), new Weight(-1.0));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(2), new Weight(4.0));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(2), new Weight(9.0));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(3), new Weight(7.0));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(4), new Weight(12.0));
        graph.addEdgeBetween(graph.getVertex(2), graph.getVertex(4), new Weight(6.0));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(4), new Weight(3.0));
    }

    @AfterEach
    public void tearDown()
    {
        graph = null;
    }

    @Test
    public void kruskal_ThenMinimalSpanningTree()
    {
        // when
        UndirectedGraph<Integer, Void, Weight> result = MinimalSpanningTree.kruskal(graph);
        // then
        double mstSize = result.getEdges()
                               .stream()
                               .mapToDouble(edge -> result.getProperties().get(edge).getWeight())
                               .sum();

        Assertions.assertThat(result.getVerticesCount()).isEqualTo(graph.getVerticesCount());
        Assertions.assertThat(result.getVertices()).hasSameElementsAs(graph.getVertices());
        Assertions.assertThat(result.getEdgesCount()).isEqualTo(4);
        Assertions.assertThat(result.getEdges())
                  .containsOnly(graph.getEdge(0, 1), graph.getEdge(0, 2), graph.getEdge(2, 4),
                                graph.getEdge(3, 4));
        Assertions.assertThat(mstSize).isCloseTo(12.0, OFFSET);
    }

    @Test
    public void prim_ThenMinimalSpanningTree()
    {
        // when
        UndirectedGraph<Integer, Void, Weight> result =
                MinimalSpanningTree.prim(graph, graph.getVertex(0));
        // then
        double mstSize = result.getEdges()
                               .stream()
                               .mapToDouble(edge -> result.getProperties().get(edge).getWeight())
                               .sum();

        Assertions.assertThat(result.getVerticesCount()).isEqualTo(graph.getVerticesCount());
        Assertions.assertThat(result.getVertices()).hasSameElementsAs(graph.getVertices());
        Assertions.assertThat(result.getEdgesCount()).isEqualTo(4);
        Assertions.assertThat(result.getEdges())
                  .containsOnly(graph.getEdge(0, 1), graph.getEdge(0, 2), graph.getEdge(2, 4),
                                graph.getEdge(3, 4));
        Assertions.assertThat(mstSize).isCloseTo(12.0, Offset.offset(0.000001));
    }

    @Test
    public void prim_WhenDifferentSources_ThenSameMinimalSpanningTree()
    {
        // when
        UndirectedGraph<Integer, Void, Weight> result1 =
                MinimalSpanningTree.prim(graph, graph.getVertex(1));
        UndirectedGraph<Integer, Void, Weight> result4 =
                MinimalSpanningTree.prim(graph, graph.getVertex(4));
        // then
        Assertions.assertThat(result1.getEdgesCount()).isEqualTo(result4.getEdgesCount());
        Assertions.assertThat(result1.getEdges()).hasSameElementsAs(result4.getEdges());
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
