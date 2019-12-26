package algolib.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algolib.graphs.searching.SearchingStrategy;
import algolib.tuples.ImmutablePair;

public class SearchingTest
{
    private SearchingTestStrategy strategy;
    private DirectedSimpleGraph digraph;
    private UndirectedSimpleGraph ugraph;

    @Before
    public void setUp()
    {
        strategy = new SearchingTestStrategy();

        digraph = new DirectedSimpleGraph(10, Arrays.asList(ImmutablePair.make(0, 1),
                                                            ImmutablePair.make(1, 4),
                                                            ImmutablePair.make(1, 7),
                                                            ImmutablePair.make(2, 4),
                                                            ImmutablePair.make(2, 6),
                                                            ImmutablePair.make(3, 0),
                                                            ImmutablePair.make(3, 7),
                                                            ImmutablePair.make(4, 5),
                                                            ImmutablePair.make(4, 3),
                                                            ImmutablePair.make(5, 6),
                                                            ImmutablePair.make(5, 8),
                                                            ImmutablePair.make(6, 5),
                                                            ImmutablePair.make(7, 5),
                                                            ImmutablePair.make(7, 8),
                                                            ImmutablePair.make(8, 9),
                                                            ImmutablePair.make(9, 6)));

        ugraph = new UndirectedSimpleGraph(10, Arrays.asList(ImmutablePair.make(0, 1),
                                                             ImmutablePair.make(1, 4),
                                                             ImmutablePair.make(1, 7),
                                                             ImmutablePair.make(2, 6),
                                                             ImmutablePair.make(3, 0),
                                                             ImmutablePair.make(3, 7),
                                                             ImmutablePair.make(4, 5),
                                                             ImmutablePair.make(4, 3),
                                                             ImmutablePair.make(5, 8),
                                                             ImmutablePair.make(7, 5),
                                                             ImmutablePair.make(7, 8),
                                                             ImmutablePair.make(9, 6)));
    }

    @After
    public void tearDown()
    {
        strategy = null;
        digraph = null;
        ugraph = null;
    }

    @Test
    public void bfs_WhenUndirectedGraphAndSingleRootThenNotAllVisited()
    {
        List<Boolean> result = Searching.bfs(ugraph, strategy, 0);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assert.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, false, true, true, false},
                result.toArray());
        Assert.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 7, 8}, visited.toArray());
    }

    @Test
    public void bfs_WhenUndirectedGraphAndManyRootsThenAllVisited()
    {
        List<Boolean> result = Searching.bfs(ugraph, strategy, 0, 6);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assert.assertArrayEquals(
                new Boolean[]{true, true, true, true, true, true, true, true, true, true},
                result.toArray());
        Assert.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, visited.toArray());
    }

    @Test
    public void bfs_WhenUndirectedGraphAndNoRootsThenNoVisited()
    {
        List<Boolean> result = Searching.bfs(ugraph, strategy);
        List<Integer> visited = strategy.getVisited();

        Assert.assertArrayEquals(
                new Boolean[]{false, false, false, false, false, false, false, false, false, false},
                result.toArray());
        Assert.assertTrue(visited.isEmpty());
    }

    @Test
    public void bfs_WhenDirectedGraphAndSingleRootThenNotAllVisited()
    {
        List<Boolean> result = Searching.bfs(digraph, strategy, 1);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assert.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, true, true, true, true},
                result.toArray());
        Assert.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 6, 7, 8, 9}, visited.toArray());
    }

    @Test
    public void dfsi_WhenUndirectedGraphAndSingleRootThenNotAllVisited()
    {
        List<Boolean> result = Searching.dfsi(ugraph, strategy, 0);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assert.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, false, true, true, false},
                result.toArray());
        Assert.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 7, 8}, visited.toArray());
    }

    @Test
    public void dfsi_WhenUndirectedGraphAndManyRootsThenAllVisited()
    {
        List<Boolean> result = Searching.dfsi(ugraph, strategy, 0, 6);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assert.assertArrayEquals(
                new Boolean[]{true, true, true, true, true, true, true, true, true, true},
                result.toArray());
        Assert.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, visited.toArray());
    }

    @Test
    public void dfsi_WhenUndirectedGraphAndNoRootsThenNoVisited()
    {
        List<Boolean> result = Searching.dfsi(ugraph, strategy);
        List<Integer> visited = strategy.getVisited();

        Assert.assertArrayEquals(
                new Boolean[]{false, false, false, false, false, false, false, false, false, false},
                result.toArray());
        Assert.assertTrue(visited.isEmpty());
    }

    @Test
    public void dfsi_WhenDirectedGraphAndSingleRootThenNotAllVisited()
    {
        List<Boolean> result = Searching.dfsi(digraph, strategy, 1);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assert.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, true, true, true, true},
                result.toArray());
        Assert.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 6, 7, 8, 9}, visited.toArray());
    }

    @Test
    public void dfsr_WhenUndirectedGraphAndSingleRootThenNotAllVisited()
    {
        List<Boolean> result = Searching.dfsr(ugraph, strategy, 0);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assert.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, false, true, true, false},
                result.toArray());
        Assert.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 7, 8}, visited.toArray());
    }

    @Test
    public void dfsr_WhenUndirectedGraphAndManyRootsThenAllVisited()
    {
        List<Boolean> result = Searching.dfsr(ugraph, strategy, 0, 6);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assert.assertArrayEquals(
                new Boolean[]{true, true, true, true, true, true, true, true, true, true},
                result.toArray());
        Assert.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, visited.toArray());
    }

    @Test
    public void dfsr_WhenUndirectedGraphAndNoRootsThenNoVisited()
    {
        List<Boolean> result = Searching.dfsr(ugraph, strategy);
        List<Integer> visited = strategy.getVisited();

        Assert.assertArrayEquals(
                new Boolean[]{false, false, false, false, false, false, false, false, false, false},
                result.toArray());
        Assert.assertTrue(visited.isEmpty());
    }

    @Test
    public void dfsr_WhenDirectedGraphAndSingleRootThenNotAllVisited()
    {
        List<Boolean> result = Searching.dfsr(digraph, strategy, 1);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assert.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, true, true, true, true},
                result.toArray());
        Assert.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 6, 7, 8, 9}, visited.toArray());
    }

    private class SearchingTestStrategy
            implements SearchingStrategy
    {
        private List<Integer> visited = new ArrayList<>();

        public List<Integer> getVisited()
        {
            return visited;
        }

        @Override
        public void preprocess(int vertex)
        {
            visited.add(vertex);
        }

        @Override
        public void forNeighbour(int vertex, int neighbour)
        {
        }

        @Override
        public void postprocess(int vertex)
        {
        }

        @Override
        public void onCycle(int vertex, int neighbour)
        {

        }
    }
}
