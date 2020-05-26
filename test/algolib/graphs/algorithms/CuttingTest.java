// Tests: Algorithms for graph cutting (edge cut and vertex cut)
package algolib.graphs.algorithms;

import java.util.Arrays;
import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.UndirectedGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.tuples.ComparablePair;
import algolib.tuples.Pair;

public class CuttingTest
{
    private UndirectedGraph graph;

    @Test
    public void findBridges_WhenPresentBridges_ThenBridges()
    {
        // given
        graph = new UndirectedSimpleGraph(12,
                                          Arrays.asList(Pair.of(0, 1), Pair.of(0, 2), Pair.of(0, 7),
                                                        Pair.of(1, 2), Pair.of(1, 3), Pair.of(1, 4),
                                                        Pair.of(3, 5), Pair.of(4, 5), Pair.of(5, 6),
                                                        Pair.of(7, 8), Pair.of(7, 9),
                                                        Pair.of(7, 11), Pair.of(8, 9),
                                                        Pair.of(9, 10), Pair.of(9, 11),
                                                        Pair.of(10, 11)));
        // when
        Collection<ComparablePair<Integer, Integer>> result = Cutting.findEdgeCut(graph);
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(ComparablePair.of(0, 7), ComparablePair.of(5, 6));
    }

    @Test
    public void findBridges_WhenNoBridges_ThenEmptyList()
    {
        // given
        graph = new UndirectedSimpleGraph(6,
                                          Arrays.asList(Pair.of(0, 1), Pair.of(0, 2), Pair.of(1, 2),
                                                        Pair.of(1, 3), Pair.of(1, 4), Pair.of(3, 5),
                                                        Pair.of(4, 5)));
        // when
        Collection<ComparablePair<Integer, Integer>> result = Cutting.findEdgeCut(graph);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findVertexSeparators_WhenPresentSeparators_ThenSeparators()
    {
        // given
        graph = new UndirectedSimpleGraph(12,
                                          Arrays.asList(Pair.of(0, 1), Pair.of(0, 2), Pair.of(0, 7),
                                                        Pair.of(1, 2), Pair.of(1, 3), Pair.of(1, 4),
                                                        Pair.of(3, 5), Pair.of(4, 5), Pair.of(5, 6),
                                                        Pair.of(7, 8), Pair.of(7, 9),
                                                        Pair.of(7, 11), Pair.of(8, 9),
                                                        Pair.of(9, 10), Pair.of(9, 11),
                                                        Pair.of(10, 11)));
        // when
        Collection<Integer> result = Cutting.findVertexCut(graph);
        // then
        Assertions.assertThat(result).containsExactlyInAnyOrder(0, 1, 5, 7);
    }

    @Test
    public void findVertexSeparators_WhenNoSeparators_ThenEmptyList()
    {
        // given
        graph = new UndirectedSimpleGraph(6,
                                          Arrays.asList(Pair.of(0, 1), Pair.of(0, 2), Pair.of(1, 2),
                                                        Pair.of(1, 3), Pair.of(1, 4), Pair.of(2, 3),
                                                        Pair.of(3, 5), Pair.of(4, 5)));
        // when
        Collection<Integer> result = Cutting.findVertexCut(graph);
        // then
        Assertions.assertThat(result).isEmpty();
    }
}
