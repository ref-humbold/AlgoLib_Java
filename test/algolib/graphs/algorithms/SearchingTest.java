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
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.strategy.DFSStrategy;
import algolib.graphs.algorithms.strategy.EmptyStrategy;

// Tests: Algorithms for graph searching
public class SearchingTest
{
    private DirectedSimpleGraph<Integer, Void, Void> directedGraph;
    private UndirectedSimpleGraph<Integer, Void, Void> undirectedGraph;

    @BeforeEach
    public void setUp()
    {
        directedGraph = new DirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));
        directedGraph.addEdgeBetween(directedGraph.getVertex(0), directedGraph.getVertex(1));
        directedGraph.addEdgeBetween(directedGraph.getVertex(1), directedGraph.getVertex(3));
        directedGraph.addEdgeBetween(directedGraph.getVertex(1), directedGraph.getVertex(7));
        directedGraph.addEdgeBetween(directedGraph.getVertex(3), directedGraph.getVertex(4));
        directedGraph.addEdgeBetween(directedGraph.getVertex(4), directedGraph.getVertex(0));
        directedGraph.addEdgeBetween(directedGraph.getVertex(5), directedGraph.getVertex(4));
        directedGraph.addEdgeBetween(directedGraph.getVertex(5), directedGraph.getVertex(8));
        directedGraph.addEdgeBetween(directedGraph.getVertex(6), directedGraph.getVertex(2));
        directedGraph.addEdgeBetween(directedGraph.getVertex(6), directedGraph.getVertex(9));
        directedGraph.addEdgeBetween(directedGraph.getVertex(8), directedGraph.getVertex(5));

        undirectedGraph = new UndirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(0), undirectedGraph.getVertex(1));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(0), undirectedGraph.getVertex(4));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(1), undirectedGraph.getVertex(3));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(1), undirectedGraph.getVertex(7));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(2), undirectedGraph.getVertex(6));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(3), undirectedGraph.getVertex(4));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(4), undirectedGraph.getVertex(5));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(5), undirectedGraph.getVertex(8));
        undirectedGraph.addEdgeBetween(undirectedGraph.getVertex(6), undirectedGraph.getVertex(9));
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
        Collection<Vertex<Integer>> result = Searching.bfs(undirectedGraph, new EmptyStrategy<>(),
                                                           List.of(undirectedGraph.getVertex(0)));
        // then
        Assertions.assertThat(result).isSubsetOf(undirectedGraph.getVertices());
        Assertions.assertThat(result)
                  .doesNotContain(undirectedGraph.getVertex(2), undirectedGraph.getVertex(6),
                                  undirectedGraph.getVertex(9));
    }

    @Test
    public void bfs_WhenUndirectedGraphAndManyRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Vertex<Integer>> result = Searching.bfs(undirectedGraph, strategy,
                                                           List.of(undirectedGraph.getVertex(0),
                                                                   undirectedGraph.getVertex(6)));
        // then
        Assertions.assertThat(result).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(undirectedGraph.getVertices());
    }

    @Test
    public void bfs_WhenUndirectedGraphAndNoRoots_ThenEmpty()
    {
        // when
        Collection<Vertex<Integer>> result =
                Searching.bfs(undirectedGraph, new EmptyStrategy<>(), List.of());
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void bfs_WhenDirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Integer>> result = Searching.bfs(directedGraph, new EmptyStrategy<>(),
                                                           List.of(directedGraph.getVertex(1)));
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result)
                  .containsOnly(directedGraph.getVertex(0), directedGraph.getVertex(1),
                                directedGraph.getVertex(3), directedGraph.getVertex(4),
                                directedGraph.getVertex(7));
    }

    @Test
    public void bfs_WhenDirectedGraphAndMultipleRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Vertex<Integer>> result = Searching.bfs(directedGraph, strategy,
                                                           List.of(directedGraph.getVertex(8),
                                                                   directedGraph.getVertex(6)));
        // then
        Assertions.assertThat(result).hasSameElementsAs(directedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(directedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(directedGraph.getVertices());
    }

    // endregion
    // region dfsIterative

    @Test
    public void dfsIterative_WhenUndirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Integer>> result =
                Searching.dfsIterative(undirectedGraph, new EmptyStrategy<>(),
                                       List.of(undirectedGraph.getVertex(0)));
        // then
        Assertions.assertThat(result).isSubsetOf(undirectedGraph.getVertices());
        Assertions.assertThat(result)
                  .doesNotContain(undirectedGraph.getVertex(2), undirectedGraph.getVertex(6),
                                  undirectedGraph.getVertex(9));
    }

    @Test
    public void dfsIterative_WhenUndirectedGraphAndManyRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Vertex<Integer>> result = Searching.dfsIterative(undirectedGraph, strategy,
                                                                    List.of(undirectedGraph.getVertex(
                                                                                    0),
                                                                            undirectedGraph.getVertex(
                                                                                    6)));
        // then
        Assertions.assertThat(result).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(undirectedGraph.getVertices());
    }

    @Test
    public void dfsIterative_WhenUndirectedGraphAndNoRoots_ThenEmpty()
    {
        // when
        Collection<Vertex<Integer>> result =
                Searching.dfsIterative(undirectedGraph, new EmptyStrategy<>(), List.of());
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void dfsIterative_WhenDirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Integer>> result =
                Searching.dfsIterative(directedGraph, new EmptyStrategy<>(),
                                       List.of(directedGraph.getVertex(1)));
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result)
                  .containsOnly(directedGraph.getVertex(0), directedGraph.getVertex(1),
                                directedGraph.getVertex(3), directedGraph.getVertex(4),
                                directedGraph.getVertex(7));
    }

    @Test
    public void dfsIterative_WhenDirectedGraphAndMultipleRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        // when
        Collection<Vertex<Integer>> result = Searching.dfsIterative(directedGraph, strategy,
                                                                    List.of(directedGraph.getVertex(
                                                                                    8),
                                                                            directedGraph.getVertex(
                                                                                    6)));
        // then
        Assertions.assertThat(result).hasSameElementsAs(directedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(directedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(directedGraph.getVertices());
    }

    // endregion
    // region dfsRecursive

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Integer>> result =
                Searching.dfsRecursive(undirectedGraph, new EmptyStrategy<>(),
                                       List.of(undirectedGraph.getVertex(0)));
        // then
        Assertions.assertThat(result).isSubsetOf(undirectedGraph.getVertices());
        Assertions.assertThat(result)
                  .doesNotContain(undirectedGraph.getVertex(2), undirectedGraph.getVertex(6),
                                  undirectedGraph.getVertex(9));
    }

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndManyRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        List<Vertex<Integer>> roots =
                List.of(undirectedGraph.getVertex(0), undirectedGraph.getVertex(6));
        // when
        Collection<Vertex<Integer>> result =
                Searching.dfsRecursive(undirectedGraph, strategy, roots);
        // then
        Assertions.assertThat(result).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(undirectedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(undirectedGraph.getVertices());
    }

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndNoRoots_ThenEmpty()
    {
        // when
        Collection<Vertex<Integer>> result =
                Searching.dfsRecursive(undirectedGraph, new EmptyStrategy<>(), List.of());
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void dfsRecursive_WhenDirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Integer>> result =
                Searching.dfsRecursive(directedGraph, new EmptyStrategy<>(),
                                       List.of(directedGraph.getVertex(1)));
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result)
                  .containsOnly(directedGraph.getVertex(0), directedGraph.getVertex(1),
                                directedGraph.getVertex(3), directedGraph.getVertex(4),
                                directedGraph.getVertex(7));
    }

    @Test
    public void dfsRecursive_WhenDirectedGraphAndMultipleRoots_ThenAllVertices()
    {
        // given
        TestingStrategy<Integer> strategy = new TestingStrategy<>();
        List<Vertex<Integer>> roots =
                List.of(directedGraph.getVertex(8), directedGraph.getVertex(6));
        // when
        Collection<Vertex<Integer>> result = Searching.dfsRecursive(directedGraph, strategy, roots);
        // then
        Assertions.assertThat(result).hasSameElementsAs(directedGraph.getVertices());
        Assertions.assertThat(strategy.entries).hasSameElementsAs(directedGraph.getVertices());
        Assertions.assertThat(strategy.exits).hasSameElementsAs(directedGraph.getVertices());
    }

    // endregion

    private static class TestingStrategy<VertexId>
            implements DFSStrategy<VertexId>
    {
        HashSet<Vertex<VertexId>> entries = new HashSet<>();
        HashSet<Vertex<VertexId>> exits = new HashSet<>();

        @Override
        public void forRoot(Vertex<VertexId> root)
        {
        }

        @Override
        public void onEntry(Vertex<VertexId> vertex)
        {
            entries.add(vertex);
        }

        @Override
        public void onNextVertex(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
        }

        @Override
        public void onExit(Vertex<VertexId> vertex)
        {
            exits.add(vertex);
        }

        @Override
        public void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
        }
    }
}
