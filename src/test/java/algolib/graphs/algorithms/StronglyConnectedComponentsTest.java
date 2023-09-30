package algolib.graphs.algorithms;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedSimpleGraph;
import algolib.graphs.Vertex;

// Tests: Algorithm for strongly connected components
public class StronglyConnectedComponentsTest
{
    @Test
    public void findScc_WhenManyComponents_ThenAllListed()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(5));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(0));
        graph.addEdgeBetween(graph.getVertex(2), graph.getVertex(3));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(4), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(4), graph.getVertex(3));
        graph.addEdgeBetween(graph.getVertex(6), graph.getVertex(5));
        graph.addEdgeBetween(graph.getVertex(6), graph.getVertex(9));
        graph.addEdgeBetween(graph.getVertex(7), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(7), graph.getVertex(6));
        graph.addEdgeBetween(graph.getVertex(8), graph.getVertex(3));
        graph.addEdgeBetween(graph.getVertex(8), graph.getVertex(7));
        graph.addEdgeBetween(graph.getVertex(9), graph.getVertex(8));
        // when
        List<Set<Vertex<Integer>>> result = StronglyConnectedComponents.findScc(graph);
        // then
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result)
                  .containsOnly(Set.of(graph.getVertex(0), graph.getVertex(1), graph.getVertex(3),
                                       graph.getVertex(4)), Set.of(graph.getVertex(2)),
                                Set.of(graph.getVertex(5)),
                                Set.of(graph.getVertex(6), graph.getVertex(7), graph.getVertex(8),
                                       graph.getVertex(9)));
    }

    @Test
    public void findScc_WhenSingleComponent_ThenAllVertices()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 7).boxed().collect(Collectors.toList()));

        graph.addEdgeBetween(graph.getVertex(0), graph.getVertex(1));
        graph.addEdgeBetween(graph.getVertex(1), graph.getVertex(2));
        graph.addEdgeBetween(graph.getVertex(2), graph.getVertex(3));
        graph.addEdgeBetween(graph.getVertex(3), graph.getVertex(4));
        graph.addEdgeBetween(graph.getVertex(4), graph.getVertex(5));
        graph.addEdgeBetween(graph.getVertex(5), graph.getVertex(6));
        graph.addEdgeBetween(graph.getVertex(6), graph.getVertex(0));
        // when
        List<Set<Vertex<Integer>>> result = StronglyConnectedComponents.findScc(graph);
        // then
        Assertions.assertThat(result).containsExactly(new HashSet<>(graph.getVertices()));
    }

    @Test
    public void findScc_WhenEmptyGraph_ThenEachVertexIsComponent()
    {
        // given
        DirectedSimpleGraph<Integer, Void, Void> graph = new DirectedSimpleGraph<>(
                IntStream.range(0, 4).boxed().collect(Collectors.toList()));
        // when
        List<Set<Vertex<Integer>>> result = StronglyConnectedComponents.findScc(graph);
        // then
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result)
                  .containsOnly(Set.of(graph.getVertex(0)), Set.of(graph.getVertex(1)),
                                Set.of(graph.getVertex(2)), Set.of(graph.getVertex(3)));
    }
}
