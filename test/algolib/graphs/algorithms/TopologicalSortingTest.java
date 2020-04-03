package algolib.graphs.algorithms;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedGraph;
import algolib.graphs.DirectedSimpleGraph;
import algolib.tuples.Pair;

public class TopologicalSortingTest
{
    private DirectedGraph acyclicGraph;
    private DirectedGraph cyclicGraph;

    @BeforeEach
    public void setUp()
    {
        acyclicGraph = new DirectedSimpleGraph(6, Arrays.asList(Pair.make(0, 2), Pair.make(0, 4),
                                                                Pair.make(1, 0), Pair.make(1, 4),
                                                                Pair.make(3, 1), Pair.make(3, 0),
                                                                Pair.make(3, 2), Pair.make(5, 1),
                                                                Pair.make(5, 2), Pair.make(5, 4)));
        cyclicGraph = new DirectedSimpleGraph(6, Arrays.asList(Pair.make(0, 2), Pair.make(0, 4),
                                                               Pair.make(1, 0), Pair.make(1, 4),
                                                               Pair.make(2, 1), Pair.make(3, 1),
                                                               Pair.make(3, 0), Pair.make(3, 2),
                                                               Pair.make(5, 1), Pair.make(5, 2),
                                                               Pair.make(5, 4)));
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
        List<Integer> result = TopologicalSorting.sortTopological1(acyclicGraph);

        Assertions.assertArrayEquals(new Object[]{3, 5, 1, 0, 2, 4}, result.toArray());
    }

    @Test
    public void sortTopological1_WhenCyclicGraph_ThenDirectedCyclicGraphException()
    {
        Assertions.assertThrows(DirectedCyclicGraphException.class,
                                () -> TopologicalSorting.sortTopological1(cyclicGraph));
    }

    @Test
    public void sortTopological1_WhenEmptyGraph()
    {
        DirectedGraph graph = new DirectedSimpleGraph(6);

        List<Integer> result = TopologicalSorting.sortTopological1(graph);

        Assertions.assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, result.toArray());
    }

    @Test
    public void sortTopological2_WhenAcyclicGraph()
    {
        List<Integer> result = TopologicalSorting.sortTopological2(acyclicGraph);
        Object[][] expecteds =
                {{3, 5, 1, 0, 2, 4}, {5, 3, 1, 0, 2, 4}, {3, 5, 1, 0, 4, 2}, {5, 3, 1, 0, 4, 2}};

        Assertions.assertTrue(Arrays.stream(expecteds)
                                    .anyMatch(expected -> Arrays.deepEquals(expected,
                                                                            result.toArray())));
    }

    @Test
    public void sortTopological2_WhenCyclicGraph_ThenDirectedCyclicGraphException()
    {
        Assertions.assertThrows(DirectedCyclicGraphException.class,
                                () -> TopologicalSorting.sortTopological1(cyclicGraph));
    }

    @Test
    public void sortTopological2_WhenEmptyGraph()
    {
        DirectedGraph graph = new DirectedSimpleGraph(6);

        List<Integer> result = TopologicalSorting.sortTopological2(graph);

        Assertions.assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, result.toArray());
    }
}
