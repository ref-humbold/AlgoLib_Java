// Tests: Algorithms for minimal spanning tree
package algolib.graphs.algorithm;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.MinimalSpanningTree;
import algolib.graphs.properties.Weighted;

public class MinimalSpanningTreeTest
{
    private UndirectedSimpleGraph<Void, Weight> graph;
    private List<Vertex<Void>> vertices;

    @BeforeEach
    public void setUp()
    {
        graph = new UndirectedSimpleGraph<>(Collections.nCopies(5, null));
        vertices = graph.getVertices();

        graph.addEdge(vertices.get(0), vertices.get(1), new Weight(-1.0));
        graph.addEdge(vertices.get(0), vertices.get(2), new Weight(4.0));
        graph.addEdge(vertices.get(1), vertices.get(2), new Weight(9.0));
        graph.addEdge(vertices.get(1), vertices.get(3), new Weight(7.0));
        graph.addEdge(vertices.get(1), vertices.get(4), new Weight(12.0));
        graph.addEdge(vertices.get(2), vertices.get(4), new Weight(6.0));
        graph.addEdge(vertices.get(3), vertices.get(4), new Weight(3.0));
    }

    @AfterEach
    public void tearDown()
    {
        graph = null;
        vertices = null;
    }

    @Test
    public void kruskal_ThenSizeOfMST()
    {
        // when
        double result = MinimalSpanningTree.kruskal(graph);
        // then
        Assertions.assertThat(result).isCloseTo(12.0, Offset.offset(0.000001));
    }

    @Test
    public void prim_ThenSizeOfMST()
    {
        // when
        double result = MinimalSpanningTree.prim(graph, vertices.get(0));
        // then
        Assertions.assertThat(result).isCloseTo(12.0, Offset.offset(0.000001));
    }

    @Test
    public void prim_WhenDifferentSources_ThenSameSize()
    {
        // when
        double result1 = MinimalSpanningTree.prim(graph, vertices.get(1));
        double result4 = MinimalSpanningTree.prim(graph, vertices.get(4));
        // then
        Assertions.assertThat(result1).isCloseTo(12.0, Offset.offset(0.000001));
        Assertions.assertThat(result4).isCloseTo(result1, Offset.offset(0.000001));
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
