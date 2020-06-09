// Tests: Algorithms for strongly connected components
package algolib.graphs.algorithms;

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
    public void findSCC_ThenStronglyConnectedComponents()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));

        graph.addEdge(0, 4);
        graph.addEdge(0, 5);
        graph.addEdge(1, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        graph.addEdge(4, 1);
        graph.addEdge(4, 3);
        graph.addEdge(6, 5);
        graph.addEdge(6, 9);
        graph.addEdge(7, 4);
        graph.addEdge(7, 6);
        graph.addEdge(8, 3);
        graph.addEdge(8, 7);
        graph.addEdge(9, 8);
        // when
        List<Set<Integer>> result = StronglyConnectedComponents.findSCC(graph);
        // then
        Assertions.assertThat(result)
                  .containsOnly(Set.of(0, 1, 3, 4), Set.of(2), Set.of(5), Set.of(6, 7, 8, 9));
    }
}
