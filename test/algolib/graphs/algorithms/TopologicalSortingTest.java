package algolib.graphs.algorithms;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedGraph;
import algolib.graphs.DirectedSimpleGraph;

public class TopologicalSortingTest
{
    private DirectedSimpleGraph<Integer, Void, Void> acyclicGraph;
    private DirectedSimpleGraph<Integer, Void, Void> cyclicGraph;

    @BeforeEach
    public void setUp()
    {
        acyclicGraph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        acyclicGraph.addEdgeBetween(0, 2);
        acyclicGraph.addEdgeBetween(0, 4);
        acyclicGraph.addEdgeBetween(1, 0);
        acyclicGraph.addEdgeBetween(1, 4);
        acyclicGraph.addEdgeBetween(3, 1);
        acyclicGraph.addEdgeBetween(3, 0);
        acyclicGraph.addEdgeBetween(3, 2);
        acyclicGraph.addEdgeBetween(5, 1);
        acyclicGraph.addEdgeBetween(5, 2);
        acyclicGraph.addEdgeBetween(5, 4);

        cyclicGraph = new DirectedSimpleGraph<>(
                IntStream.range(0, 6).boxed().collect(Collectors.toList()));

        cyclicGraph.addEdgeBetween(0, 2);
        cyclicGraph.addEdgeBetween(0, 4);
        cyclicGraph.addEdgeBetween(1, 0);
        cyclicGraph.addEdgeBetween(1, 4);
        cyclicGraph.addEdgeBetween(2, 1);
        cyclicGraph.addEdgeBetween(3, 1);
        cyclicGraph.addEdgeBetween(3, 0);
        cyclicGraph.addEdgeBetween(3, 2);
        cyclicGraph.addEdgeBetween(5, 1);
        cyclicGraph.addEdgeBetween(5, 2);
        cyclicGraph.addEdgeBetween(5, 4);
    }

    @AfterEach
    public void tearDown()
    {
        acyclicGraph = null;
        cyclicGraph = null;
    }

    @Test
    public void sortTopological1_WhenAcyclicGraph_ThenTopologicalOrder()
    {
        // when
        List<Integer> result = TopologicalSorting.sortTopological1(acyclicGraph);
        // then
        Assertions.assertThat(result).containsExactly(3, 5, 1, 0, 2, 4);
    }

    @Test
    public void sortTopological1_WhenCyclicGraph_ThenDirectedCyclicGraphException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> TopologicalSorting.sortTopological1(cyclicGraph));
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
        // when
        List<Integer> result = TopologicalSorting.sortTopological2(acyclicGraph);
        // then
        Assertions.assertThat(result)
                  .isIn(List.of(3, 5, 1, 0, 2, 4), List.of(5, 3, 1, 0, 2, 4),
                        List.of(3, 5, 1, 0, 4, 2), List.of(5, 3, 1, 0, 4, 2));
    }

    @Test
    public void sortTopological2_WhenCyclicGraph_ThenDirectedCyclicGraphException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> TopologicalSorting.sortTopological2(cyclicGraph));
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
