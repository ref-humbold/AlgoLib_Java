package algolib.graphs.algorithms;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedGraph;
import algolib.graphs.DirectedSimpleGraph;

public class TopologicalSortingTest
{
    private DirectedSimpleGraph<Void, Void> acyclicGraph;
    private DirectedSimpleGraph<Void, Void> cyclicGraph;

    @BeforeEach
    public void setUp()
    {
        acyclicGraph = new DirectedSimpleGraph<>(Collections.nCopies(6, null));

        acyclicGraph.addEdge(acyclicGraph.getVertex(0), acyclicGraph.getVertex(2), null);
        acyclicGraph.addEdge(acyclicGraph.getVertex(0), acyclicGraph.getVertex(4), null);
        acyclicGraph.addEdge(acyclicGraph.getVertex(1), acyclicGraph.getVertex(0), null);
        acyclicGraph.addEdge(acyclicGraph.getVertex(1), acyclicGraph.getVertex(4), null);
        acyclicGraph.addEdge(acyclicGraph.getVertex(3), acyclicGraph.getVertex(1), null);
        acyclicGraph.addEdge(acyclicGraph.getVertex(3), acyclicGraph.getVertex(0), null);
        acyclicGraph.addEdge(acyclicGraph.getVertex(3), acyclicGraph.getVertex(2), null);
        acyclicGraph.addEdge(acyclicGraph.getVertex(5), acyclicGraph.getVertex(1), null);
        acyclicGraph.addEdge(acyclicGraph.getVertex(5), acyclicGraph.getVertex(2), null);
        acyclicGraph.addEdge(acyclicGraph.getVertex(5), acyclicGraph.getVertex(4), null);

        cyclicGraph = new DirectedSimpleGraph<>(Collections.nCopies(6, null));

        cyclicGraph.addEdge(cyclicGraph.getVertex(0), cyclicGraph.getVertex(2), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(0), cyclicGraph.getVertex(4), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(1), cyclicGraph.getVertex(0), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(1), cyclicGraph.getVertex(4), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(2), cyclicGraph.getVertex(1), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(3), cyclicGraph.getVertex(1), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(3), cyclicGraph.getVertex(0), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(3), cyclicGraph.getVertex(2), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(5), cyclicGraph.getVertex(1), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(5), cyclicGraph.getVertex(2), null);
        cyclicGraph.addEdge(cyclicGraph.getVertex(5), cyclicGraph.getVertex(4), null);
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
        List<Vertex<Void>> result = TopologicalSorting.sortTopological1(acyclicGraph);
        // then
        Assertions.assertThat(result)
                  .containsExactly(acyclicGraph.getVertex(3), acyclicGraph.getVertex(5),
                                   acyclicGraph.getVertex(1), acyclicGraph.getVertex(0),
                                   acyclicGraph.getVertex(2), acyclicGraph.getVertex(4));
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
        DirectedGraph<Void, Void> graph = new DirectedSimpleGraph<>(Collections.nCopies(6, null));
        // when
        List<Vertex<Void>> result = TopologicalSorting.sortTopological1(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }

    @Test
    public void sortTopological2_WhenAcyclicGraph_ThenTopologicalOrder()
    {
        // when
        List<Vertex<Void>> result = TopologicalSorting.sortTopological2(acyclicGraph);
        // then
        Assertions.assertThat(result)
                  .isIn(List.of(acyclicGraph.getVertex(3), acyclicGraph.getVertex(5),
                                acyclicGraph.getVertex(1), acyclicGraph.getVertex(0),
                                acyclicGraph.getVertex(2), acyclicGraph.getVertex(4)),
                        List.of(acyclicGraph.getVertex(5), acyclicGraph.getVertex(3),
                                acyclicGraph.getVertex(1), acyclicGraph.getVertex(0),
                                acyclicGraph.getVertex(2), acyclicGraph.getVertex(4)),
                        List.of(acyclicGraph.getVertex(3), acyclicGraph.getVertex(5),
                                acyclicGraph.getVertex(1), acyclicGraph.getVertex(0),
                                acyclicGraph.getVertex(4), acyclicGraph.getVertex(2)),
                        List.of(acyclicGraph.getVertex(5), acyclicGraph.getVertex(3),
                                acyclicGraph.getVertex(1), acyclicGraph.getVertex(0),
                                acyclicGraph.getVertex(4), acyclicGraph.getVertex(2)));
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
        DirectedGraph<Void, Void> graph = new DirectedSimpleGraph<>(Collections.nCopies(6, null));
        // when
        List<Vertex<Void>> result = TopologicalSorting.sortTopological2(graph);
        // then
        Assertions.assertThat(result).isEqualTo(graph.getVertices());
    }
}
