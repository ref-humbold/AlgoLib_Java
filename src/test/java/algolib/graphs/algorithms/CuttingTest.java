package algolib.graphs.algorithms;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.Vertex;

// Tests: Algorithms for graph cutting (edge cut and vertex cut).
public class CuttingTest
{
    @Test
    public void findEdgeCut_WhenPresentBridges_ThenBridges()
    {
        // given
        UndirectedSimpleGraph<Integer, Void, Void> graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 12).boxed().collect(Collectors.toList()));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(7));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(3));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(5));
        graph.addEdgeBetween(graph.getVertex(4), graph.getVertex(5));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(6));
        graph.addEdgeBetween(graph.getVertex(7), graph.getVertex(8));
        graph.addEdgeBetween(graph.getVertex(7), graph.getVertex(9));
        graph.addEdgeBetween(graph.getVertex(7), graph.getVertex(11));
        graph.addEdgeBetween(graph.getVertex(8), graph.getVertex(9));
        graph.addEdgeBetween(graph.getVertex(9), graph.getVertex(10));
        graph.addEdgeBetween(graph.getVertex(9), graph.getVertex(11));
        graph.addEdgeBetween(graph.getVertex(10), graph.getVertex(11));

        // when
        Collection<Edge<Integer>> result = Cutting.findEdgeCut(graph);

        // then
        Assertions.assertThat(result).containsOnly(graph.getEdge(0, 7), graph.getEdge(5, 6));
    }

    @Test
    public void findEdgeCut_WhenNoBridges_ThenEmptyList()
    {
        // given
        UndirectedSimpleGraph<Integer, Void, Void> graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(3));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(5));
        graph.addEdgeBetween(graph.getVertex(4), graph.getVertex(5));

        // when
        Collection<Edge<Integer>> result = Cutting.findEdgeCut(graph);

        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findVertexCut_WhenPresentSeparators_ThenSeparators()
    {
        // given
        UndirectedSimpleGraph<Integer, Void, Void> graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 12).boxed().collect(Collectors.toList()));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(7));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(3));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(5));
        graph.addEdgeBetween(graph.getVertex(4), graph.getVertex(5));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(6));
        graph.addEdgeBetween(graph.getVertex(7), graph.getVertex(8));
        graph.addEdgeBetween(graph.getVertex(7), graph.getVertex(9));
        graph.addEdgeBetween(graph.getVertex(7), graph.getVertex(11));
        graph.addEdgeBetween(graph.getVertex(8), graph.getVertex(9));
        graph.addEdgeBetween(graph.getVertex(9), graph.getVertex(10));
        graph.addEdgeBetween(graph.getVertex(9), graph.getVertex(11));
        graph.addEdgeBetween(graph.getVertex(10), graph.getVertex(11));

        // when
        Collection<Vertex<Integer>> result = Cutting.findVertexCut(graph);

        // then
        Assertions.assertThat(result)
                  .containsOnly(graph.getVertex(0), graph.getVertex(1), graph.getVertex(5),
                                graph.getVertex(7));
    }

    @Test
    public void findVertexCut_WhenNoSeparators_ThenEmptyList()
    {
        // given
        UndirectedSimpleGraph<Integer, Void, Void> graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(3));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(2), graph.getVertex(3));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(5));
        graph.addEdgeBetween(graph.getVertex(4), graph.getVertex(5));

        // when
        Collection<Vertex<Integer>> result = Cutting.findVertexCut(graph);

        // then
        Assertions.assertThat(result).isEmpty();
    }
}
