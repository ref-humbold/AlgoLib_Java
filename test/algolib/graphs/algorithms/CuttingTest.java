// Tests: Algorithms for graph cutting (edge cut and vertex cut)
package algolib.graphs.algorithms;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedSimpleGraph;

public class CuttingTest
{
    private UndirectedSimpleGraph<Integer, Void, Void> graph;

    @Test
    public void findBridges_WhenPresentBridges_ThenBridges()
    {
        // given
        graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 12).boxed().collect(Collectors.toList()));
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 7);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(7, 8);
        graph.addEdge(7, 9);
        graph.addEdge(7, 11);
        graph.addEdge(8, 9);
        graph.addEdge(9, 10);
        graph.addEdge(9, 11);
        graph.addEdge(10, 11);
        // when
        List<Edge<Integer>> result = Cutting.findEdgeCut(graph);
        // then
        Assertions.assertThat(result).containsOnly(graph.getEdge(0, 7), graph.getEdge(5, 6));
    }

    @Test
    public void findBridges_WhenNoBridges_ThenEmptyList()
    {
        // given
        graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        // when
        List<Edge<Integer>> result = Cutting.findEdgeCut(graph);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findVertexSeparators_WhenPresentSeparators_ThenSeparators()
    {
        // given
        graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 12).boxed().collect(Collectors.toList()));
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 7);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(7, 8);
        graph.addEdge(7, 9);
        graph.addEdge(7, 11);
        graph.addEdge(8, 9);
        graph.addEdge(9, 10);
        graph.addEdge(9, 11);
        graph.addEdge(10, 11);
        // when
        List<Integer> result = Cutting.findVertexCut(graph);
        // then
        Assertions.assertThat(result).containsOnly(0, 1, 5, 7);
    }

    @Test
    public void findVertexSeparators_WhenNoSeparators_ThenEmptyList()
    {
        // given
        graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        // when
        List<Integer> result = Cutting.findVertexCut(graph);
        // then
        Assertions.assertThat(result).isEmpty();
    }
}
