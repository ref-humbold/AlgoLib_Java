package algolib.graphs;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algolib.tuples.ImmutablePair;

public class TopologicalSortingTest
{
    private DirectedGraph acyclicGraph;
    private DirectedGraph cyclicGraph;

    @Before
    public void setUp()
    {
        acyclicGraph = new DirectedSimpleGraph(6, Arrays.asList(ImmutablePair.make(0, 2),
                                                                ImmutablePair.make(0, 4),
                                                                ImmutablePair.make(1, 0),
                                                                ImmutablePair.make(1, 4),
                                                                ImmutablePair.make(3, 1),
                                                                ImmutablePair.make(3, 0),
                                                                ImmutablePair.make(3, 2),
                                                                ImmutablePair.make(5, 1),
                                                                ImmutablePair.make(5, 2),
                                                                ImmutablePair.make(5, 4)));
        cyclicGraph = new DirectedSimpleGraph(6, Arrays.asList(ImmutablePair.make(0, 2),
                                                               ImmutablePair.make(0, 4),
                                                               ImmutablePair.make(1, 0),
                                                               ImmutablePair.make(1, 4),
                                                               ImmutablePair.make(2, 1),
                                                               ImmutablePair.make(3, 1),
                                                               ImmutablePair.make(3, 0),
                                                               ImmutablePair.make(3, 2),
                                                               ImmutablePair.make(5, 1),
                                                               ImmutablePair.make(5, 2),
                                                               ImmutablePair.make(5, 4)));
    }

    @After
    public void tearDown()
    {
        acyclicGraph = null;
        cyclicGraph = null;
    }

    @Test
    public void testSortTopological1WhenAcyclicGraph()
    {
        List<Integer> result = TopologicalSorting.sortTopological1(acyclicGraph);

        Assert.assertArrayEquals(new Object[]{3, 5, 1, 0, 2, 4}, result.toArray());
    }

    @Test(expected = DirectedCyclicGraphException.class)
    public void testSortTopological1WhenCyclicGraph()
    {
        TopologicalSorting.sortTopological1(cyclicGraph);
    }

    @Test
    public void testSortTopological1WhenEmptyGraph()
    {
        DirectedGraph graph = new DirectedSimpleGraph(6);

        List<Integer> result = TopologicalSorting.sortTopological1(graph);

        Assert.assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, result.toArray());
    }

    @Test
    public void testSortTopological2WhenAcyclicGraph()
    {
        List<Integer> result = TopologicalSorting.sortTopological2(acyclicGraph);
        Object[][] expecteds =
                {{3, 5, 1, 0, 2, 4}, {5, 3, 1, 0, 2, 4}, {3, 5, 1, 0, 4, 2}, {5, 3, 1, 0, 4, 2}};

        Assert.assertTrue(Arrays.stream(expecteds)
                                .anyMatch(
                                        expected -> Arrays.deepEquals(expected, result.toArray())));
    }

    @Test(expected = DirectedCyclicGraphException.class)
    public void testSortTopological2WhenCyclicGraph()
    {
        TopologicalSorting.sortTopological1(cyclicGraph);
    }

    @Test
    public void testSortTopological2WhenEmptyGraph()
    {
        DirectedGraph graph = new DirectedSimpleGraph(6);

        List<Integer> result = TopologicalSorting.sortTopological2(graph);

        Assert.assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, result.toArray());
    }
}
