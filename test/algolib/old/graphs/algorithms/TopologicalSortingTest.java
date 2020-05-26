package algolib.old.graphs.algorithms;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.old.graphs.DirectedGraph;
import algolib.old.graphs.DirectedSimpleGraph;
import algolib.tuples.Pair;

public class TopologicalSortingTest
{
    private DirectedGraph acyclicGraph;
    private DirectedGraph cyclicGraph;

    @BeforeEach
    public void setUp()
    {
        acyclicGraph = new DirectedSimpleGraph(6, Arrays.asList(Pair.of(0, 2), Pair.of(0, 4),
                                                                Pair.of(1, 0), Pair.of(1, 4),
                                                                Pair.of(3, 1), Pair.of(3, 0),
                                                                Pair.of(3, 2), Pair.of(5, 1),
                                                                Pair.of(5, 2), Pair.of(5, 4)));
        cyclicGraph = new DirectedSimpleGraph(6, Arrays.asList(Pair.of(0, 2), Pair.of(0, 4),
                                                               Pair.of(1, 0), Pair.of(1, 4),
                                                               Pair.of(2, 1), Pair.of(3, 1),
                                                               Pair.of(3, 0), Pair.of(3, 2),
                                                               Pair.of(5, 1), Pair.of(5, 2),
                                                               Pair.of(5, 4)));
    }

    @AfterEach
    public void tearDown()
    {
        acyclicGraph = null;
        cyclicGraph = null;
    }

    @Test
    public void sortTopological1_WhenAcyclicGraph()
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
    public void sortTopological1_WhenEmptyGraph()
    {
        // given
        DirectedGraph graph = new DirectedSimpleGraph(6);
        // when
        List<Integer> result = TopologicalSorting.sortTopological1(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }

    @Test
    public void sortTopological2_WhenAcyclicGraph()
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
                Assertions.catchThrowable(() -> TopologicalSorting.sortTopological1(cyclicGraph));
        // then
        Assertions.assertThat(throwable).isInstanceOf(DirectedCyclicGraphException.class);
    }

    @Test
    public void sortTopological2_WhenEmptyGraph()
    {
        // given
        DirectedGraph graph = new DirectedSimpleGraph(6);
        // when
        List<Integer> result = TopologicalSorting.sortTopological2(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }
}
