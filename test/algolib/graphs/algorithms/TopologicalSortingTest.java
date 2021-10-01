package algolib.graphs.algorithms;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedGraph;
import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.Vertex;

public class TopologicalSortingTest
{
    @Test
    public void sortUsingInputs_WhenAcyclicGraph_ThenTopologicalOrder()
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
        List<Vertex<Integer>> result = TopologicalSorting.sortUsingInputs(graph);
        // then
        Assertions.assertThat(result)
                  .containsExactly(graph.getVertex(3), graph.getVertex(5), graph.getVertex(1),
                                   graph.getVertex(0), graph.getVertex(2), graph.getVertex(4));
    }

    @Test
    public void sortUsingInputs_WhenCyclicGraph_ThenDirectedCyclicGraphException()
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
                Assertions.catchThrowable(() -> TopologicalSorting.sortUsingInputs(graph));
        // then
        Assertions.assertThat(throwable).isInstanceOf(DirectedCyclicGraphException.class);
    }

    @Test
    public void sortUsingInputs_WhenEmptyGraph_ThenVertices()
    {
        // given
        DirectedGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        // when
        List<Vertex<Integer>> result = TopologicalSorting.sortUsingInputs(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }

    @Test
    public void sortUsingDFS_WhenAcyclicGraph_ThenTopologicalOrder()
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
        List<Vertex<Integer>> result = TopologicalSorting.sortUsingDFS(graph);
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
    public void sortUsingDFS_WhenCyclicGraph_ThenDirectedCyclicGraphException()
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
                Assertions.catchThrowable(() -> TopologicalSorting.sortUsingDFS(graph));
        // then
        Assertions.assertThat(throwable).isInstanceOf(DirectedCyclicGraphException.class);
    }

    @Test
    public void sortUsingDFS_WhenEmptyGraph_ThenVertices()
    {
        // given
        DirectedGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));
        // when
        List<Vertex<Integer>> result = TopologicalSorting.sortUsingDFS(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }
}
