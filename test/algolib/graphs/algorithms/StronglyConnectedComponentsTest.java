// Tests: Algorithm for strongly connected components
package algolib.graphs.algorithms;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;

public class StronglyConnectedComponentsTest
{
    @Test
    public void findSCC_WhenManyComponents_ThenAllListed()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(0, 4);
        graph.addEdgeBetween(0, 5);
        graph.addEdgeBetween(1, 0);
        graph.addEdgeBetween(2, 3);
        graph.addEdgeBetween(3, 1);
        graph.addEdgeBetween(4, 1);
        graph.addEdgeBetween(4, 3);
        graph.addEdgeBetween(6, 5);
        graph.addEdgeBetween(6, 9);
        graph.addEdgeBetween(7, 4);
        graph.addEdgeBetween(7, 6);
        graph.addEdgeBetween(8, 3);
        graph.addEdgeBetween(8, 7);
        graph.addEdgeBetween(9, 8);
        // when
        List<Set<Integer>> result = StronglyConnectedComponents.findSCC(graph);
        // then
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result)
                  .containsOnly(Set.of(0, 1, 3, 4), Set.of(2), Set.of(5), Set.of(6, 7, 8, 9));
    }

    @Test
    public void findSCC_WhenSingeleComponent_ThenAllVertices()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 7).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(0, 1);
        graph.addEdgeBetween(1, 2);
        graph.addEdgeBetween(2, 3);
        graph.addEdgeBetween(3, 4);
        graph.addEdgeBetween(4, 5);
        graph.addEdgeBetween(5, 6);
        graph.addEdgeBetween(6, 0);
        // when
        List<Set<Integer>> result = StronglyConnectedComponents.findSCC(graph);
        // then
        Assertions.assertThat(result).containsExactly(new HashSet<>(graph.getVertices()));
    }

    @Test
    public void findSCC_WhenEmptyGraph_ThenEachVertexIsComponent()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 4).boxed().collect(Collectors.toList()));
        // when
        List<Set<Integer>> result = StronglyConnectedComponents.findSCC(graph);
        // then
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result).containsOnly(Set.of(0), Set.of(1), Set.of(2), Set.of(3));
    }
}
