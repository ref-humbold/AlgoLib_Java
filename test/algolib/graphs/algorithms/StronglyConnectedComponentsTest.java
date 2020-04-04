package algolib.graphs.algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.DirectedGraph;
import algolib.graphs.DirectedSimpleGraph;
import algolib.tuples.Pair;

public class StronglyConnectedComponentsTest
{
    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void findSCC()
    {
        DirectedGraph digraph = new DirectedSimpleGraph(10,
                                                        Arrays.asList(Pair.of(0, 4), Pair.of(0, 5),
                                                                      Pair.of(1, 0), Pair.of(2, 3),
                                                                      Pair.of(3, 1), Pair.of(4, 1),
                                                                      Pair.of(4, 3), Pair.of(6, 5),
                                                                      Pair.of(6, 9), Pair.of(7, 4),
                                                                      Pair.of(7, 6), Pair.of(8, 3),
                                                                      Pair.of(8, 7),
                                                                      Pair.of(9, 8)));

        List<Set<Integer>> result = StronglyConnectedComponents.findSCC(digraph);

        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(result.contains(Collections.singleton(5)));
        Assertions.assertTrue(result.contains(Collections.singleton(2)));
        Assertions.assertTrue(result.contains(new HashSet<>(Arrays.asList(0, 1, 3, 4))));
        Assertions.assertTrue(result.contains(new HashSet<>(Arrays.asList(6, 7, 8, 9))));
    }
}
