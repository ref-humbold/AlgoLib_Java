// Tests: Algorithms for graph cutting (edge cut and vertex cut)
package algolib.graphs.algorithm;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.Cutting;

public class CuttingTest
{
    private UndirectedSimpleGraph<Void, Void> graph;

    @Test
    public void findBridges_WhenPresentBridges_ThenBridges()
    {
        // given
        graph = new UndirectedSimpleGraph<>(Collections.nCopies(12, null));
        graph.addEdge(graph.getVertex(0), graph.getVertex(1), null);
        graph.addEdge(graph.getVertex(0), graph.getVertex(2), null);
        graph.addEdge(graph.getVertex(0), graph.getVertex(7), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(2), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(3), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(4), null);
        graph.addEdge(graph.getVertex(3), graph.getVertex(5), null);
        graph.addEdge(graph.getVertex(4), graph.getVertex(5), null);
        graph.addEdge(graph.getVertex(5), graph.getVertex(6), null);
        graph.addEdge(graph.getVertex(7), graph.getVertex(8), null);
        graph.addEdge(graph.getVertex(7), graph.getVertex(9), null);
        graph.addEdge(graph.getVertex(7), graph.getVertex(11), null);
        graph.addEdge(graph.getVertex(8), graph.getVertex(9), null);
        graph.addEdge(graph.getVertex(9), graph.getVertex(10), null);
        graph.addEdge(graph.getVertex(9), graph.getVertex(11), null);
        graph.addEdge(graph.getVertex(10), graph.getVertex(11), null);
        // when
        List<Edge<Void, Void>> result = Cutting.findEdgeCut(graph);
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(graph.getEdge(graph.getVertex(0), graph.getVertex(7)),
                                             graph.getEdge(graph.getVertex(5), graph.getVertex(6)));
    }

    @Test
    public void findBridges_WhenNoBridges_ThenEmptyList()
    {
        // given
        graph = new UndirectedSimpleGraph<>(Collections.nCopies(6, null));
        graph.addEdge(graph.getVertex(0), graph.getVertex(1), null);
        graph.addEdge(graph.getVertex(0), graph.getVertex(2), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(2), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(3), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(4), null);
        graph.addEdge(graph.getVertex(3), graph.getVertex(5), null);
        graph.addEdge(graph.getVertex(4), graph.getVertex(5), null);
        // when
        List<Edge<Void, Void>> result = Cutting.findEdgeCut(graph);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findVertexSeparators_WhenPresentSeparators_ThenSeparators()
    {
        // given
        graph = new UndirectedSimpleGraph<>(Collections.nCopies(12, null));
        graph.addEdge(graph.getVertex(0), graph.getVertex(1), null);
        graph.addEdge(graph.getVertex(0), graph.getVertex(2), null);
        graph.addEdge(graph.getVertex(0), graph.getVertex(7), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(2), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(3), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(4), null);
        graph.addEdge(graph.getVertex(3), graph.getVertex(5), null);
        graph.addEdge(graph.getVertex(4), graph.getVertex(5), null);
        graph.addEdge(graph.getVertex(5), graph.getVertex(6), null);
        graph.addEdge(graph.getVertex(7), graph.getVertex(8), null);
        graph.addEdge(graph.getVertex(7), graph.getVertex(9), null);
        graph.addEdge(graph.getVertex(7), graph.getVertex(11), null);
        graph.addEdge(graph.getVertex(8), graph.getVertex(9), null);
        graph.addEdge(graph.getVertex(9), graph.getVertex(10), null);
        graph.addEdge(graph.getVertex(9), graph.getVertex(11), null);
        graph.addEdge(graph.getVertex(10), graph.getVertex(11), null);
        // when
        List<Vertex<Void>> result = Cutting.findVertexCut(graph);
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(graph.getVertex(0), graph.getVertex(1),
                                             graph.getVertex(5), graph.getVertex(7));
    }

    @Test
    public void findVertexSeparators_WhenNoSeparators_ThenEmptyList()
    {
        // given
        graph = new UndirectedSimpleGraph<>(Collections.nCopies(6, null));
        graph.addEdge(graph.getVertex(0), graph.getVertex(1), null);
        graph.addEdge(graph.getVertex(0), graph.getVertex(2), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(2), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(3), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(4), null);
        graph.addEdge(graph.getVertex(2), graph.getVertex(3), null);
        graph.addEdge(graph.getVertex(3), graph.getVertex(5), null);
        graph.addEdge(graph.getVertex(4), graph.getVertex(5), null);
        // when
        List<Vertex<Void>> result = Cutting.findVertexCut(graph);
        // then
        Assertions.assertThat(result).isEmpty();
    }
}
