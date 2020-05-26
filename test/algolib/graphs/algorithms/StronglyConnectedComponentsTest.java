// Tests: Algorithms for strongly connected components
package algolib.graphs.algorithms;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedGraph;
import algolib.graphs.DirectedSimpleGraph;
import algolib.tuples.Pair;

public class StronglyConnectedComponentsTest
{
    @Test
    public void findSCC()
    {
        // given
        DirectedGraph digraph = new DirectedSimpleGraph(10,
                                                        Arrays.asList(Pair.of(0, 4), Pair.of(0, 5),
                                                                      Pair.of(1, 0), Pair.of(2, 3),
                                                                      Pair.of(3, 1), Pair.of(4, 1),
                                                                      Pair.of(4, 3), Pair.of(6, 5),
                                                                      Pair.of(6, 9), Pair.of(7, 4),
                                                                      Pair.of(7, 6), Pair.of(8, 3),
                                                                      Pair.of(8, 7),
                                                                      Pair.of(9, 8)));
        // when
        List<Set<Integer>> result = StronglyConnectedComponents.findSCC(digraph);
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(Set.of(0, 1, 3, 4), Set.of(2), Set.of(5),
                                             Set.of(6, 7, 8, 9));
    }
}
