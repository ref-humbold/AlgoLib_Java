// Tests: Algorithms for graph searching
package algolib.graphs.algorithms;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.algorithms.strategy.DFSStrategy;
import algolib.graphs.algorithms.strategy.EmptyStrategy;

public class SearchingTest
{
    private DirectedSimpleGraph<Integer, Void, Void> directedGraph;
    private UndirectedSimpleGraph<Integer, Void, Void> undirectedGraph;

    @BeforeEach
    public void setUp()
    {
        directedGraph = new DirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));
        directedGraph.addEdgeBetween(0, 1);
        directedGraph.addEdgeBetween(1, 3);
        directedGraph.addEdgeBetween(1, 7);
        directedGraph.addEdgeBetween(3, 4);
        directedGraph.addEdgeBetween(4, 0);
        directedGraph.addEdgeBetween(5, 4);
        directedGraph.addEdgeBetween(5, 8);
        directedGraph.addEdgeBetween(6, 2);
        directedGraph.addEdgeBetween(6, 9);
        directedGraph.addEdgeBetween(8, 5);

        undirectedGraph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));
        undirectedGraph.addEdgeBetween(0, 1);
        undirectedGraph.addEdgeBetween(0, 4);
        undirectedGraph.addEdgeBetween(1, 3);
        undirectedGraph.addEdgeBetween(1, 7);
        undirectedGraph.addEdgeBetween(2, 6);
        undirectedGraph.addEdgeBetween(3, 4);
        undirectedGraph.addEdgeBetween(4, 5);
        undirectedGraph.addEdgeBetween(5, 8);
        undirectedGraph.addEdgeBetween(6, 9);
    }

    @AfterEach
    public void tearDown()
    {
        directedGraph = null;
        undirectedGraph = null;
    }

    // region bfs

    @Test
    public void bfs_WhenUndirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Integer> result =
                Searching.bfs(undirectedGraph, new EmptyStrategy<>(), List.of(0));
        // then
        Assertions.assertThat(result).isSubsetOf(undirectedGraph.getVertices());
        Assertions.assertThat(result).doesNotContain(2, 6, 9);
    }

    @Test
    public void bfs_WhenUndirectedGraphAndManyRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Integer> result = Searching.bfs(undirectedGraph, strategy, List.of(0, 6));
        // then
        Assertions.assertThat(result).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(undirectedGraph.getVertices());
    }

    @Test
    public void bfs_WhenUndirectedGraphAndNoRoots_ThenEmpty()
    {
        // when
        Collection<Integer> result =
                Searching.bfs(undirectedGraph, new EmptyStrategy<>(), List.of());
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void bfs_WhenDirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Integer> result =
                Searching.bfs(directedGraph, new EmptyStrategy<>(), List.of(1));
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result).containsOnly(0, 1, 3, 4, 7);
    }

    @Test
    public void bfs_WhenDirectedGraphAndMultipleRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Integer> result = Searching.bfs(directedGraph, strategy, List.of(8, 6));
        // then
        Assertions.assertThat(result).hasSameElementsAs(directedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(undirectedGraph.getVertices());
    }

    // endregion
    // region dfsIterative

    @Test
    public void dfsIterative_WhenUndirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Integer> result =
                Searching.dfsIterative(undirectedGraph, new EmptyStrategy<>(), List.of(0));
        // then
        Assertions.assertThat(result).isSubsetOf(undirectedGraph.getVertices());
        Assertions.assertThat(result).doesNotContain(2, 6, 9);
    }

    @Test
    public void dfsIterative_WhenUndirectedGraphAndManyRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Integer> result =
                Searching.dfsIterative(undirectedGraph, strategy, List.of(0, 6));
        // then
        Assertions.assertThat(result).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(undirectedGraph.getVertices());
    }

    @Test
    public void dfsIterative_WhenUndirectedGraphAndNoRoots_ThenEmpty()
    {
        // when
        Collection<Integer> result =
                Searching.dfsIterative(undirectedGraph, new EmptyStrategy<>(), List.of());
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void dfsIterative_WhenDirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Integer> result =
                Searching.dfsIterative(directedGraph, new EmptyStrategy<>(), List.of(1));
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result).containsOnly(0, 1, 3, 4, 7);
    }

    @Test
    public void dfsIterative_WhenDirectedGraphAndMultipleRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Integer> result = Searching.dfsIterative(directedGraph, strategy, List.of(8, 6));
        // then
        Assertions.assertThat(result).hasSameElementsAs(directedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(undirectedGraph.getVertices());
    }

    // endregion
    // region dfsRecursive

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Integer> result =
                Searching.dfsRecursive(undirectedGraph, new EmptyStrategy<>(), List.of(0));
        // then
        Assertions.assertThat(result).isSubsetOf(undirectedGraph.getVertices());
        Assertions.assertThat(result).doesNotContain(2, 6, 9);
    }

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndManyRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Integer> result =
                Searching.dfsRecursive(undirectedGraph, strategy, List.of(0, 6));
        // then
        Assertions.assertThat(result).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(undirectedGraph.getVertices());
    }

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndNoRoots_ThenEmpty()
    {
        // when
        Collection<Integer> result =
                Searching.dfsRecursive(undirectedGraph, new EmptyStrategy<>(), List.of());
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void dfsRecursive_WhenDirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Integer> result =
                Searching.dfsRecursive(directedGraph, new EmptyStrategy<>(), List.of(1));
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result).containsOnly(0, 1, 3, 4, 7);
    }

    @Test
    public void dfsRecursive_WhenDirectedGraphAndMultipleRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Integer> result = Searching.dfsRecursive(directedGraph, strategy, List.of(8, 6));
        // then
        Assertions.assertThat(result).hasSameElementsAs(directedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(undirectedGraph.getVertices());
    }

    // endregion

    private static class TestingStrategy<V>
            implements DFSStrategy<V>
    {
        HashSet<V> entries = new HashSet<>();
        HashSet<V> exits = new HashSet<>();

        @Override
        public void forRoot(V root)
        {
        }

        @Override
        public void onEntry(V vertex)
        {
            entries.add(vertex);
        }

        @Override
        public void onNextVertex(V vertex, V neighbour)
        {
        }

        @Override
        public void onExit(V vertex)
        {
            exits.add(vertex);
        }

        @Override
        public void onEdgeToVisited(V vertex, V neighbour)
        {
        }
    }
}
