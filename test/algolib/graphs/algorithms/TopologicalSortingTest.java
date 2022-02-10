package algolib.graphs.algorithms;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedGraph;
import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.Vertex;

// Tests: Algorithms for topological sorting of a directed graph
public class TopologicalSortingTest
{
    // region inputsTopologicalSort

    @Test
    public void inputsTopologicalSort_WhenAcyclicGraph_ThenTopologicalOrder()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(0));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(0));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(4));
        // when
        List<Vertex<Integer>> result = TopologicalSorting.inputsTopologicalSort(graph);
        // then
        Assertions.assertThat(result)
                  .containsExactly(graph.getVertex(3), graph.getVertex(5), graph.getVertex(1),
                                   graph.getVertex(0), graph.getVertex(2), graph.getVertex(4));
    }

    @Test
    public void inputsTopologicalSort_WhenCyclicGraph_ThenDirectedCyclicGraphException()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(0));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(2), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(0));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(4));
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> TopologicalSorting.inputsTopologicalSort(graph));
        // then
        Assertions.assertThat(throwable).isInstanceOf(DirectedCyclicGraphException.class);
    }

    @Test
    public void inputsTopologicalSort_WhenEmptyGraph_ThenVertices()
    {
        // given
        DirectedGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        // when
        List<Vertex<Integer>> result = TopologicalSorting.inputsTopologicalSort(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }

    // endregion
    // region dfsTopologicalSort

    @Test
    public void dfsTopologicalSort_WhenAcyclicGraph_ThenTopologicalOrder()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(0));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(0));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(4));
        // when
        List<Vertex<Integer>> result = TopologicalSorting.dfsTopologicalSort(graph);
        // then
        Assertions.assertThat(result)
                  .isIn(List.of(graph.getVertex(3), graph.getVertex(5), graph.getVertex(1),
                                graph.getVertex(0), graph.getVertex(2), graph.getVertex(4)),
                        List.of(graph.getVertex(5), graph.getVertex(3), graph.getVertex(1),
                                graph.getVertex(0), graph.getVertex(2), graph.getVertex(4)),
                        List.of(graph.getVertex(3), graph.getVertex(5), graph.getVertex(1),
                                graph.getVertex(0), graph.getVertex(4), graph.getVertex(2)),
                        List.of(graph.getVertex(5), graph.getVertex(3), graph.getVertex(1),
                                graph.getVertex(0), graph.getVertex(4), graph.getVertex(2)));
    }

    @Test
    public void dfsTopologicalSort_WhenCyclicGraph_ThenDirectedCyclicGraphException()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(0));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(2), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(0));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(4));
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> TopologicalSorting.dfsTopologicalSort(graph));
        // then
        Assertions.assertThat(throwable).isInstanceOf(DirectedCyclicGraphException.class);
    }

    @Test
    public void dfsTopologicalSort_WhenEmptyGraph_ThenVertices()
    {
        // given
        DirectedGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        // when
        List<Vertex<Integer>> result = TopologicalSorting.dfsTopologicalSort(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }

    // endregion
}
