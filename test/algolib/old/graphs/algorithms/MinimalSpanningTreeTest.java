// Tests: Algorithms for minimal spanning tree
package algolib.old.graphs.algorithms;

import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.old.graphs.UndirectedWeightedSimpleGraph;
import algolib.tuples.Triple;

public class MinimalSpanningTreeTest
{
    private UndirectedWeightedSimpleGraph graph;

    @BeforeEach
    public void setUp()
    {
        graph = new UndirectedWeightedSimpleGraph(5, Arrays.asList(Triple.of(0, 1, -1.0),
                                                                   Triple.of(0, 2, 4.0),
                                                                   Triple.of(1, 2, 9.0),
                                                                   Triple.of(1, 3, 7.0),
                                                                   Triple.of(1, 4, 12.0),
                                                                   Triple.of(2, 4, 6.0),
                                                                   Triple.of(3, 4, 3.0)));
    }

    @AfterEach
    public void tearDown()
    {
        graph = null;
    }

    @Test
    public void kruskal_WhenGraph_ThenSize()
    {
        // when
        double result = MinimalSpanningTree.kruskal(graph);
        // then
        Assertions.assertThat(result).isCloseTo(12.0, Offset.offset(0.000001));
    }

    @Test
    public void prim_WhenGraph_ThenSize()
    {
        // when
        double result = MinimalSpanningTree.prim(graph, 0);
        // then
        Assertions.assertThat(result).isCloseTo(12.0, Offset.offset(0.000001));
    }

    @Test
    public void prim_WhenDifferentSources_ThenSameSize()
    {
        // when
        double result1 = MinimalSpanningTree.prim(graph, 1);
        double result4 = MinimalSpanningTree.prim(graph, 4);
        // then
        Assertions.assertThat(result1).isCloseTo(12.0, Offset.offset(0.000001));
        Assertions.assertThat(result4).isCloseTo(result1, Offset.offset(0.000001));
    }
}
