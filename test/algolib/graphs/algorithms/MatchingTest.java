package algolib.graphs.algorithms;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.MultipartiteGraph;

// Tests: Hopcroft-Karp algorithm for matching in bipartite graph
public class MatchingTest
{
    @Test
    public void match_WhenMatchingExists_ThenMaximalMatching()
    {
        // given
        MultipartiteGraph<Integer, Void, Void> graph =
                new MultipartiteGraph<>(2, List.of(List.of(0, 2, 4, 6), List.of(1, 3, 5, 7)));
        graph.addEdgeBetween(0, 3);
        graph.addEdgeBetween(0, 5);
        graph.addEdgeBetween(1, 2);
        graph.addEdgeBetween(3, 4);
        graph.addEdgeBetween(3, 6);
        graph.addEdgeBetween(6, 7);

        int[] matches = new int[]{5, 2, 1, 4, 3, 0, 7, 6};
        Map<Integer, Integer> expected = IntStream.range(0, matches.length)
                                                  .boxed()
                                                  .collect(Collectors.toMap(Function.identity(),
                                                                            i -> matches[i]));
        // when
        Map<Integer, Integer> result = Matching.match(graph);
        // then
        Assertions.assertThat(result).containsOnlyKeys(graph.getVertices());
        Assertions.assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void match_WhenVerticesOnlyInGroup0_ThenEmpty()
    {
        // given
        MultipartiteGraph<Integer, Void, Void> graph =
                new MultipartiteGraph<>(2, List.of(List.of(0, 1, 2, 3, 4)));
        // when
        Map<Integer, Integer> result = Matching.match(graph);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void match_WhenVerticesOnlyInGroup1_ThenEmpty()
    {
        // given
        MultipartiteGraph<Integer, Void, Void> graph =
                new MultipartiteGraph<>(2, List.of(List.of(), List.of(0, 1, 2, 3, 4)));
        // when
        Map<Integer, Integer> result = Matching.match(graph);
        // then
        Assertions.assertThat(result).isEmpty();
    }
}
