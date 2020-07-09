// Tests: Algorithms for graph cutting (edge cut and vertex cut)
package algolib.graphs.algorithms;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedSimpleGraph;

public class CuttingTest
{
    @Test
    public void findEdgeCut_WhenPresentBridges_ThenBridges()
    {
        // given
        UndirectedSimpleGraph<Integer, Void, Void> graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 12).boxed().collect(Collectors.toList()));
        graph.addEdgeBetween(0, 1);
        graph.addEdgeBetween(0, 2);
        graph.addEdgeBetween(0, 7);
        graph.addEdgeBetween(1, 2);
        graph.addEdgeBetween(1, 3);
        graph.addEdgeBetween(1, 4);
        graph.addEdgeBetween(3, 5);
        graph.addEdgeBetween(4, 5);
        graph.addEdgeBetween(5, 6);
        graph.addEdgeBetween(7, 8);
        graph.addEdgeBetween(7, 9);
        graph.addEdgeBetween(7, 11);
        graph.addEdgeBetween(8, 9);
        graph.addEdgeBetween(9, 10);
        graph.addEdgeBetween(9, 11);
        graph.addEdgeBetween(10, 11);
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
        graph.addEdgeBetween(0, 1);
        graph.addEdgeBetween(0, 2);
        graph.addEdgeBetween(1, 2);
        graph.addEdgeBetween(1, 3);
        graph.addEdgeBetween(1, 4);
        graph.addEdgeBetween(3, 5);
        graph.addEdgeBetween(4, 5);
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
        graph.addEdgeBetween(0, 1);
        graph.addEdgeBetween(0, 2);
        graph.addEdgeBetween(0, 7);
        graph.addEdgeBetween(1, 2);
        graph.addEdgeBetween(1, 3);
        graph.addEdgeBetween(1, 4);
        graph.addEdgeBetween(3, 5);
        graph.addEdgeBetween(4, 5);
        graph.addEdgeBetween(5, 6);
        graph.addEdgeBetween(7, 8);
        graph.addEdgeBetween(7, 9);
        graph.addEdgeBetween(7, 11);
        graph.addEdgeBetween(8, 9);
        graph.addEdgeBetween(9, 10);
        graph.addEdgeBetween(9, 11);
        graph.addEdgeBetween(10, 11);
        // when
        Collection<Integer> result = Cutting.findVertexCut(graph);
        // then
        Assertions.assertThat(result).containsOnly(0, 1, 5, 7);
    }

    @Test
    public void findVertexCut_WhenNoSeparators_ThenEmptyList()
    {
        // given
        UndirectedSimpleGraph<Integer, Void, Void> graph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        graph.addEdgeBetween(0, 1);
        graph.addEdgeBetween(0, 2);
        graph.addEdgeBetween(1, 2);
        graph.addEdgeBetween(1, 3);
        graph.addEdgeBetween(1, 4);
        graph.addEdgeBetween(2, 3);
        graph.addEdgeBetween(3, 5);
        graph.addEdgeBetween(4, 5);
        // when
        Collection<Integer> result = Cutting.findVertexCut(graph);
        // then
        Assertions.assertThat(result).isEmpty();
    }
}
