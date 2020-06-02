// Tests: Algorithms for minimal spanning tree
package algolib.graphs.algorithm;

import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.UndirectedGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.algorithms.MinimalSpanningTree;
import algolib.graphs.properties.Weighted;

public class MinimalSpanningTreeTest
{
    private UndirectedSimpleGraph<Void, Weight> graph;

    @BeforeEach
    public void setUp()
    {
        graph = new UndirectedSimpleGraph<>(Collections.nCopies(5, null));

        graph.addEdge(graph.getVertex(0), graph.getVertex(1), new Weight(-1.0));
        graph.addEdge(graph.getVertex(0), graph.getVertex(2), new Weight(4.0));
        graph.addEdge(graph.getVertex(1), graph.getVertex(2), new Weight(9.0));
        graph.addEdge(graph.getVertex(1), graph.getVertex(3), new Weight(7.0));
        graph.addEdge(graph.getVertex(1), graph.getVertex(4), new Weight(12.0));
        graph.addEdge(graph.getVertex(2), graph.getVertex(4), new Weight(6.0));
        graph.addEdge(graph.getVertex(3), graph.getVertex(4), new Weight(3.0));
    }

    @AfterEach
    public void tearDown()
    {
        graph = null;
    }

    @Test
    public void kruskal_ThenMST()
    {
        // when
        UndirectedGraph<Void, Weight> result = MinimalSpanningTree.kruskal(graph);
        // then
        double mstSize =
                result.getEdges().stream().mapToDouble(edge -> edge.property.getWeight()).sum();

        Assertions.assertThat(result.getVerticesCount()).isEqualTo(graph.getVerticesCount());
        Assertions.assertThat(result.getVertices()).hasSameElementsAs(graph.getVertices());
        Assertions.assertThat(result.getEdgesCount()).isEqualTo(4);
        Assertions.assertThat(result.getEdges())
                  .containsOnly(graph.getEdge(graph.getVertex(0), graph.getVertex(1)),
                                graph.getEdge(graph.getVertex(0), graph.getVertex(2)),
                                graph.getEdge(graph.getVertex(2), graph.getVertex(4)),
                                graph.getEdge(graph.getVertex(3), graph.getVertex(4)));
        Assertions.assertThat(mstSize).isCloseTo(12.0, Offset.offset(0.000001));
    }

    @Test
    public void prim_ThenMST()
    {
        // when
        UndirectedGraph<Void, Weight> result = MinimalSpanningTree.prim(graph, graph.getVertex(0));
        // then
        double mstSize =
                result.getEdges().stream().mapToDouble(edge -> edge.property.getWeight()).sum();

        Assertions.assertThat(result.getVerticesCount()).isEqualTo(graph.getVerticesCount());
        Assertions.assertThat(result.getVertices()).hasSameElementsAs(graph.getVertices());
        Assertions.assertThat(result.getEdgesCount()).isEqualTo(4);
        Assertions.assertThat(result.getEdges())
                  .containsOnly(graph.getEdge(graph.getVertex(0), graph.getVertex(1)),
                                graph.getEdge(graph.getVertex(0), graph.getVertex(2)),
                                graph.getEdge(graph.getVertex(2), graph.getVertex(4)),
                                graph.getEdge(graph.getVertex(3), graph.getVertex(4)));
        Assertions.assertThat(mstSize).isCloseTo(12.0, Offset.offset(0.000001));
    }

    @Test
    public void prim_WhenDifferentSources_ThenSameMST()
    {
        // when
        UndirectedGraph<Void, Weight> result1 = MinimalSpanningTree.prim(graph, graph.getVertex(1));
        UndirectedGraph<Void, Weight> result4 = MinimalSpanningTree.prim(graph, graph.getVertex(4));
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
