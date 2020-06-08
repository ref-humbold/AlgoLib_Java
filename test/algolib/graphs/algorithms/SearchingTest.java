// Tests: Algorithms for graph searching
package algolib.graphs.algorithms;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.algorithms.strategy.EmptyStrategy;

public class SearchingTest
{
    private DirectedSimpleGraph<Void, Void> directedGraph;
    private UndirectedSimpleGraph<Void, Void> undirectedGraph;

    @BeforeEach
    public void setUp()
    {
        directedGraph = new DirectedSimpleGraph<>(Collections.nCopies(10, null));

        directedGraph.addEdge(directedGraph.getVertex(0), directedGraph.getVertex(1), null);
        directedGraph.addEdge(directedGraph.getVertex(1), directedGraph.getVertex(3), null);
        directedGraph.addEdge(directedGraph.getVertex(1), directedGraph.getVertex(7), null);
        directedGraph.addEdge(directedGraph.getVertex(3), directedGraph.getVertex(4), null);
        directedGraph.addEdge(directedGraph.getVertex(4), directedGraph.getVertex(0), null);
        directedGraph.addEdge(directedGraph.getVertex(5), directedGraph.getVertex(4), null);
        directedGraph.addEdge(directedGraph.getVertex(5), directedGraph.getVertex(8), null);
        directedGraph.addEdge(directedGraph.getVertex(6), directedGraph.getVertex(2), null);
        directedGraph.addEdge(directedGraph.getVertex(6), directedGraph.getVertex(9), null);
        directedGraph.addEdge(directedGraph.getVertex(8), directedGraph.getVertex(5), null);

        undirectedGraph = new UndirectedSimpleGraph<>(Collections.nCopies(10, null));

        undirectedGraph.addEdge(undirectedGraph.getVertex(0), undirectedGraph.getVertex(1), null);
        undirectedGraph.addEdge(undirectedGraph.getVertex(0), undirectedGraph.getVertex(4), null);
        undirectedGraph.addEdge(undirectedGraph.getVertex(1), undirectedGraph.getVertex(3), null);
        undirectedGraph.addEdge(undirectedGraph.getVertex(1), undirectedGraph.getVertex(7), null);
        undirectedGraph.addEdge(undirectedGraph.getVertex(2), undirectedGraph.getVertex(6), null);
        undirectedGraph.addEdge(undirectedGraph.getVertex(3), undirectedGraph.getVertex(4), null);
        undirectedGraph.addEdge(undirectedGraph.getVertex(4), undirectedGraph.getVertex(5), null);
        undirectedGraph.addEdge(undirectedGraph.getVertex(5), undirectedGraph.getVertex(8), null);
        undirectedGraph.addEdge(undirectedGraph.getVertex(6), undirectedGraph.getVertex(9), null);
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
        Collection<Vertex<Void>> result = Searching.bfs(undirectedGraph, new EmptyStrategy<>(),
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
        // when
        Collection<Vertex<Void>> result = Searching.bfs(undirectedGraph, new EmptyStrategy<>(),
                                                        List.of(undirectedGraph.getVertex(0),
                                                                undirectedGraph.getVertex(6)));
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
        // when
        Collection<Vertex<Void>> result = Searching.bfs(directedGraph, new EmptyStrategy<>(),
                                                        List.of(directedGraph.getVertex(8),
                                                                directedGraph.getVertex(6)));
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
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsIterative(undirectedGraph, new EmptyStrategy<>(),
                                       List.of(undirectedGraph.getVertex(0),
                                               undirectedGraph.getVertex(6)));
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
                                       List.of(directedGraph.getVertex(1)));
        // then
        Assertions.assertThat(result).isSubsetOf(directedGraph.getVertices());
        Assertions.assertThat(result).doesNotContain(directedGraph.getVertex(2));
    }

    // endregion
    // region dfsRecursive

    @Test
    public void dfsRecursive_WhenUndirectedGraphAndSingleRoot_ThenVisitedVertices()
    {
        // when
        Collection<Vertex<Void>> result =
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
        // when
        Collection<Vertex<Void>> result =
                Searching.dfsRecursive(undirectedGraph, new EmptyStrategy<>(),
                                       List.of(undirectedGraph.getVertex(0),
                                               undirectedGraph.getVertex(6)));
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
                                       List.of(directedGraph.getVertex(1)));
        // then
        Assertions.assertThat(result).isSubsetOf(directedGraph.getVertices());
        Assertions.assertThat(result).doesNotContain(directedGraph.getVertex(2));
    }

    // endregion
}
