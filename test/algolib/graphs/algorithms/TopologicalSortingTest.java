package algolib.graphs.algorithms;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedGraph;
import algolib.graphs.DirectedSimpleGraph;

public class TopologicalSortingTest
{
    @Test
    public void sortTopological1_WhenAcyclicGraph_ThenTopologicalOrder()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(0, 2);
        graph.addEdgeBetween(0, 4);
        graph.addEdgeBetween(1, 0);
        graph.addEdgeBetween(1, 4);
        graph.addEdgeBetween(3, 1);
        graph.addEdgeBetween(3, 0);
        graph.addEdgeBetween(3, 2);
        graph.addEdgeBetween(5, 1);
        graph.addEdgeBetween(5, 2);
        graph.addEdgeBetween(5, 4);
        // when
        List<Integer> result = TopologicalSorting.sortTopological1(graph);
        // then
        Assertions.assertThat(result).containsExactly(3, 5, 1, 0, 2, 4);
    }

    @Test
    public void sortTopological1_WhenCyclicGraph_ThenDirectedCyclicGraphException()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(0, 2);
        graph.addEdgeBetween(0, 4);
        graph.addEdgeBetween(1, 0);
        graph.addEdgeBetween(1, 4);
        graph.addEdgeBetween(2, 1);
        graph.addEdgeBetween(3, 1);
        graph.addEdgeBetween(3, 0);
        graph.addEdgeBetween(3, 2);
        graph.addEdgeBetween(5, 1);
        graph.addEdgeBetween(5, 2);
        graph.addEdgeBetween(5, 4);
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> TopologicalSorting.sortTopological1(graph));
        // then
        Assertions.assertThat(throwable).isInstanceOf(DirectedCyclicGraphException.class);
    }

    @Test
    public void sortTopological1_WhenEmptyGraph_ThenNaturalOrder()
    {
        // given
        DirectedGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        // when
        List<Integer> result = TopologicalSorting.sortTopological1(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }

    @Test
    public void sortTopological2_WhenAcyclicGraph_ThenTopologicalOrder()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(0, 2);
        graph.addEdgeBetween(0, 4);
        graph.addEdgeBetween(1, 0);
        graph.addEdgeBetween(1, 4);
        graph.addEdgeBetween(3, 1);
        graph.addEdgeBetween(3, 0);
        graph.addEdgeBetween(3, 2);
        graph.addEdgeBetween(5, 1);
        graph.addEdgeBetween(5, 2);
        graph.addEdgeBetween(5, 4);
        // when
        List<Integer> result = TopologicalSorting.sortTopological2(graph);
        // then
        Assertions.assertThat(result)
                  .isIn(List.of(3, 5, 1, 0, 2, 4), List.of(5, 3, 1, 0, 2, 4),
                        List.of(3, 5, 1, 0, 4, 2), List.of(5, 3, 1, 0, 4, 2));
    }

    @Test
    public void sortTopological2_WhenCyclicGraph_ThenDirectedCyclicGraphException()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(0, 2);
        graph.addEdgeBetween(0, 4);
        graph.addEdgeBetween(1, 0);
        graph.addEdgeBetween(1, 4);
        graph.addEdgeBetween(2, 1);
        graph.addEdgeBetween(3, 1);
        graph.addEdgeBetween(3, 0);
        graph.addEdgeBetween(3, 2);
        graph.addEdgeBetween(5, 1);
        graph.addEdgeBetween(5, 2);
        graph.addEdgeBetween(5, 4);
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> TopologicalSorting.sortTopological2(graph));
        // then
        Assertions.assertThat(throwable).isInstanceOf(DirectedCyclicGraphException.class);
    }

    @Test
    public void sortTopological2_WhenEmptyGraph_ThenNaturalOrder()
    {
        // given
        DirectedGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        // when
        List<Integer> result = TopologicalSorting.sortTopological2(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }
}
