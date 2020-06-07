// Tests: Algorithms for strongly connected components
package algolib.graphs.algorithms;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.Vertex;

public class StronglyConnectedComponentsTest
{
    @Test
    public void find_ThenStronglyConnectedComponents()
    {
        // given
        DirectedSimpleGraph<Void, Void> graph =
                new DirectedSimpleGraph<>(Collections.nCopies(10, null));

        graph.addEdge(graph.getVertex(0), graph.getVertex(4), null);
        graph.addEdge(graph.getVertex(0), graph.getVertex(5), null);
        graph.addEdge(graph.getVertex(1), graph.getVertex(0), null);
        graph.addEdge(graph.getVertex(2), graph.getVertex(3), null);
        graph.addEdge(graph.getVertex(3), graph.getVertex(1), null);
        graph.addEdge(graph.getVertex(4), graph.getVertex(1), null);
        graph.addEdge(graph.getVertex(4), graph.getVertex(3), null);
        graph.addEdge(graph.getVertex(6), graph.getVertex(5), null);
        graph.addEdge(graph.getVertex(6), graph.getVertex(9), null);
        graph.addEdge(graph.getVertex(7), graph.getVertex(4), null);
        graph.addEdge(graph.getVertex(7), graph.getVertex(6), null);
        graph.addEdge(graph.getVertex(8), graph.getVertex(3), null);
        graph.addEdge(graph.getVertex(8), graph.getVertex(7), null);
        graph.addEdge(graph.getVertex(9), graph.getVertex(8), null);
        // when
        List<Set<Vertex<Void>>> result = StronglyConnectedComponents.find(graph);
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(
                          Set.of(graph.getVertex(0), graph.getVertex(1), graph.getVertex(3),
                                 graph.getVertex(4)), Set.of(graph.getVertex(2)),
                          Set.of(graph.getVertex(5)),
                          Set.of(graph.getVertex(6), graph.getVertex(7), graph.getVertex(8),
                                 graph.getVertex(9)));
    }
}
