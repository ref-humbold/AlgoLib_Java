package algolib.graphs.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.tuples.Pair;

public class SearchingTest
{
    private SearchingTestStrategy strategy;
    private DirectedSimpleGraph digraph;
    private UndirectedSimpleGraph ugraph;

    @BeforeEach
    public void setUp()
    {
        strategy = new SearchingTestStrategy();

        digraph = new DirectedSimpleGraph(10,
                                          Arrays.asList(Pair.of(0, 1), Pair.of(1, 4), Pair.of(1, 7),
                                                        Pair.of(2, 4), Pair.of(2, 6), Pair.of(3, 0),
                                                        Pair.of(3, 7), Pair.of(4, 5), Pair.of(4, 3),
                                                        Pair.of(5, 6), Pair.of(5, 8), Pair.of(6, 5),
                                                        Pair.of(7, 5), Pair.of(7, 8), Pair.of(8, 9),
                                                        Pair.of(9, 6)));

        ugraph = new UndirectedSimpleGraph(10, Arrays.asList(Pair.of(0, 1), Pair.of(1, 4),
                                                             Pair.of(1, 7), Pair.of(2, 6),
                                                             Pair.of(3, 0), Pair.of(3, 7),
                                                             Pair.of(4, 5), Pair.of(4, 3),
                                                             Pair.of(5, 8), Pair.of(7, 5),
                                                             Pair.of(7, 8), Pair.of(9, 6)));
    }

    @AfterEach
    public void tearDown()
    {
        strategy = null;
        digraph = null;
        ugraph = null;
    }

    @Test
    public void bfs_WhenUndirectedGraphAndSingleRoot_ThenNotAllVisited()
    {
        List<Boolean> result = Searching.bfs(ugraph, strategy, 0);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assertions.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, false, true, true, false},
                result.toArray());
        Assertions.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 7, 8}, visited.toArray());
    }

    @Test
    public void bfs_WhenUndirectedGraphAndManyRoots_ThenAllVisited()
    {
        List<Boolean> result = Searching.bfs(ugraph, strategy, 0, 6);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assertions.assertArrayEquals(
                new Boolean[]{true, true, true, true, true, true, true, true, true, true},
                result.toArray());
        Assertions.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                                     visited.toArray());
    }

    @Test
    public void bfs_WhenUndirectedGraphAndNoRoots_ThenNoVisited()
    {
        List<Boolean> result = Searching.bfs(ugraph, strategy);
        List<Integer> visited = strategy.getVisited();

        Assertions.assertArrayEquals(
                new Boolean[]{false, false, false, false, false, false, false, false, false, false},
                result.toArray());
        Assertions.assertTrue(visited.isEmpty());
    }

    @Test
    public void bfs_WhenDirectedGraphAndSingleRoot_ThenNotAllVisited()
    {
        List<Boolean> result = Searching.bfs(digraph, strategy, 1);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assertions.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, true, true, true, true},
                result.toArray());
        Assertions.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 6, 7, 8, 9}, visited.toArray());
    }

    @Test
    public void dfsi_WhenUndirectedGraphAndSingleRoot_ThenNotAllVisited()
    {
        List<Boolean> result = Searching.dfsi(ugraph, strategy, 0);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assertions.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, false, true, true, false},
                result.toArray());
        Assertions.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 7, 8}, visited.toArray());
    }

    @Test
    public void dfsi_WhenUndirectedGraphAndManyRoots_ThenAllVisited()
    {
        List<Boolean> result = Searching.dfsi(ugraph, strategy, 0, 6);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assertions.assertArrayEquals(
                new Boolean[]{true, true, true, true, true, true, true, true, true, true},
                result.toArray());
        Assertions.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                                     visited.toArray());
    }

    @Test
    public void dfsi_WhenUndirectedGraphAndNoRoots_ThenNoVisited()
    {
        List<Boolean> result = Searching.dfsi(ugraph, strategy);
        List<Integer> visited = strategy.getVisited();

        Assertions.assertArrayEquals(
                new Boolean[]{false, false, false, false, false, false, false, false, false, false},
                result.toArray());
        Assertions.assertTrue(visited.isEmpty());
    }

    @Test
    public void dfsi_WhenDirectedGraphAndSingleRoot_ThenNotAllVisited()
    {
        List<Boolean> result = Searching.dfsi(digraph, strategy, 1);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assertions.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, true, true, true, true},
                result.toArray());
        Assertions.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 6, 7, 8, 9}, visited.toArray());
    }

    @Test
    public void dfsr_WhenUndirectedGraphAndSingleRoot_ThenNotAllVisited()
    {
        List<Boolean> result = Searching.dfsr(ugraph, strategy, 0);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assertions.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, false, true, true, false},
                result.toArray());
        Assertions.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 7, 8}, visited.toArray());
    }

    @Test
    public void dfsr_WhenUndirectedGraphAndManyRoots_ThenAllVisited()
    {
        List<Boolean> result = Searching.dfsr(ugraph, strategy, 0, 6);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assertions.assertArrayEquals(
                new Boolean[]{true, true, true, true, true, true, true, true, true, true},
                result.toArray());
        Assertions.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                                     visited.toArray());
    }

    @Test
    public void dfsr_WhenUndirectedGraphAndNoRoots_ThenNoVisited()
    {
        List<Boolean> result = Searching.dfsr(ugraph, strategy);
        List<Integer> visited = strategy.getVisited();

        Assertions.assertArrayEquals(
                new Boolean[]{false, false, false, false, false, false, false, false, false, false},
                result.toArray());
        Assertions.assertTrue(visited.isEmpty());
    }

    @Test
    public void dfsr_WhenDirectedGraphAndSingleRoot_ThenNotAllVisited()
    {
        List<Boolean> result = Searching.dfsr(digraph, strategy, 1);
        List<Integer> visited = strategy.getVisited();

        visited.sort(Integer::compare);

        Assertions.assertArrayEquals(
                new Boolean[]{true, true, false, true, true, true, true, true, true, true},
                result.toArray());
        Assertions.assertArrayEquals(new Integer[]{0, 1, 3, 4, 5, 6, 7, 8, 9}, visited.toArray());
    }

    private static class SearchingTestStrategy
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
