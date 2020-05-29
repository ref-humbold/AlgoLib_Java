// Tests: Algorithms for graph searching
package algolib.graphs.algorithm;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.Searching;
import algolib.graphs.algorithms.strategy.EmptyStrategy;

public class SearchingTest
{
    private DirectedSimpleGraph<Void, Void> directedGraph;
    private UndirectedSimpleGraph<Void, Void> undirectedGraph;
    private List<Vertex<Void>> verticesDirected;
    private List<Vertex<Void>> verticesUndirected;

    @BeforeEach
    public void setUp()
    {
        directedGraph = new DirectedSimpleGraph<>(Collections.nCopies(10, null));
        verticesDirected = directedGraph.getVertices();
        directedGraph.addEdge(verticesDirected.get(0), verticesDirected.get(1), null);
        directedGraph.addEdge(verticesDirected.get(1), verticesDirected.get(3), null);
        directedGraph.addEdge(verticesDirected.get(1), verticesDirected.get(7), null);
        directedGraph.addEdge(verticesDirected.get(3), verticesDirected.get(4), null);
        directedGraph.addEdge(verticesDirected.get(4), verticesDirected.get(0), null);
        directedGraph.addEdge(verticesDirected.get(5), verticesDirected.get(4), null);
        directedGraph.addEdge(verticesDirected.get(5), verticesDirected.get(8), null);
        directedGraph.addEdge(verticesDirected.get(6), verticesDirected.get(2), null);
        directedGraph.addEdge(verticesDirected.get(6), verticesDirected.get(9), null);
        directedGraph.addEdge(verticesDirected.get(8), verticesDirected.get(5), null);

        undirectedGraph = new UndirectedSimpleGraph<>(Collections.nCopies(10, null));
        verticesUndirected = undirectedGraph.getVertices();
        undirectedGraph.addEdge(verticesUndirected.get(0), verticesUndirected.get(1), null);
        undirectedGraph.addEdge(verticesUndirected.get(0), verticesUndirected.get(4), null);
        undirectedGraph.addEdge(verticesUndirected.get(1), verticesUndirected.get(3), null);
        undirectedGraph.addEdge(verticesUndirected.get(1), verticesUndirected.get(7), null);
        undirectedGraph.addEdge(verticesUndirected.get(2), verticesUndirected.get(6), null);
        undirectedGraph.addEdge(verticesUndirected.get(3), verticesUndirected.get(4), null);
        undirectedGraph.addEdge(verticesUndirected.get(4), verticesUndirected.get(5), null);
        undirectedGraph.addEdge(verticesUndirected.get(5), verticesUndirected.get(8), null);
        undirectedGraph.addEdge(verticesUndirected.get(6), verticesUndirected.get(9), null);
    }

    @AfterEach
    public void tearDown()
    {
        directedGraph = null;
        undirectedGraph = null;
        verticesDirected = null;
        verticesUndirected = null;
    }

    // region bfs

    @Test
    public void bfs_WhenUndirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Void>> result = Searching.bfs(undirectedGraph, new EmptyStrategy<>(),
                                                        List.of(verticesUndirected.get(0)));
        // then
        Assertions.assertThat(result).isSubsetOf(undirectedGraph.getVertices());
        Assertions.assertThat(result)
                  .doesNotContain(verticesUndirected.get(2), verticesUndirected.get(6),
                                  verticesUndirected.get(9));
    }

    @Test
    public void bfs_WhenUndirectedGraphAndManyRoots_ThenAllVertices()
    {
        // when
        Collection<Vertex<Void>> result = Searching.bfs(undirectedGraph, new EmptyStrategy<>(),
                                                        List.of(verticesUndirected.get(0),
                                                                verticesUndirected.get(6)));
        // then
        Assertions.assertThat(result).hasSameElementsAs(undirectedGraph.getVertices());
    }

    @Test
    public void bfs_WhenUndirectedGraphAndNoRoots_ThenEmpty()
    {
        // when
        Collection<Vertex<Void>> result =
                Searching.bfs(undirectedGraph, new EmptyStrategy<>(), List.of());
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void bfs_WhenDirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Void>> result = Searching.bfs(directedGraph, new EmptyStrategy<>(),
                                                        List.of(verticesDirected.get(1)));
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result)
                  .containsOnly(verticesDirected.get(0), verticesDirected.get(1),
                                verticesDirected.get(3), verticesDirected.get(4),
                                verticesDirected.get(7));
    }

    @Test
    public void bfs_WhenDirectedGraphAndMultipleRoots_ThenAllVertices()
    {
        // when
        Collection<Vertex<Void>> result = Searching.bfs(directedGraph, new EmptyStrategy<>(),
                                                        List.of(verticesDirected.get(8),
                                                                verticesDirected.get(6)));
        // then
        Assertions.assertThat(result).hasSameElementsAs(directedGraph.getVertices());
    }

    // endregion
    // region dfsIterative

    @Test
    public void dfsIterative_WhenUndirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsIterative(undirectedGraph, new EmptyStrategy<>(),
                                       List.of(verticesUndirected.get(0)));
        // then
        Assertions.assertThat(result).isSubsetOf(undirectedGraph.getVertices());
        Assertions.assertThat(result)
                  .doesNotContain(verticesUndirected.get(2), verticesUndirected.get(6),
                                  verticesUndirected.get(9));
    }

    @Test
    public void dfsIterative_WhenUndirectedGraphAndManyRoots_ThenAllVertices()
    {
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsIterative(undirectedGraph, new EmptyStrategy<>(),
                                       List.of(verticesUndirected.get(0),
                                               verticesUndirected.get(6)));
        // then
        Assertions.assertThat(result).hasSameElementsAs(undirectedGraph.getVertices());
    }

    @Test
    public void dfsIterative_WhenUndirectedGraphAndNoRoots_ThenEmpty()
    {
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsIterative(undirectedGraph, new EmptyStrategy<>(), List.of());
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void dfsIterative_WhenDirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsIterative(directedGraph, new EmptyStrategy<>(),
                                       List.of(verticesDirected.get(1)));
        // then
        Assertions.assertThat(result).isSubsetOf(directedGraph.getVertices());
        Assertions.assertThat(result).doesNotContain(verticesDirected.get(2));
    }

    // endregion
    // region dfsRecursive

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsRecursive(undirectedGraph, new EmptyStrategy<>(),
                                       List.of(verticesUndirected.get(0)));
        // then
        Assertions.assertThat(result).isSubsetOf(undirectedGraph.getVertices());
        Assertions.assertThat(result)
                  .doesNotContain(verticesUndirected.get(2), verticesUndirected.get(6),
                                  verticesUndirected.get(9));
    }

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndManyRoots_ThenAllVertices()
    {
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsRecursive(undirectedGraph, new EmptyStrategy<>(),
                                       List.of(verticesUndirected.get(0),
                                               verticesUndirected.get(6)));
        // then
        Assertions.assertThat(result).hasSameElementsAs(undirectedGraph.getVertices());
    }

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndNoRoots_ThenEmpty()
    {
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsRecursive(undirectedGraph, new EmptyStrategy<>(), List.of());
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void dfsRecursive_WhenDirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsRecursive(directedGraph, new EmptyStrategy<>(),
                                       List.of(verticesDirected.get(1)));
        // then
        Assertions.assertThat(result).isSubsetOf(directedGraph.getVertices());
        Assertions.assertThat(result).doesNotContain(verticesDirected.get(2));
    }

    // endregion
}
