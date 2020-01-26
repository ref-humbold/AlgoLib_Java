package algolib.graphs;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.tuples.ImmutablePair;

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
        DirectedGraph digraph = new DirectedSimpleGraph(10, Arrays.asList(ImmutablePair.make(0, 4),
                                                                          ImmutablePair.make(0, 5),
                                                                          ImmutablePair.make(1, 0),
                                                                          ImmutablePair.make(2, 3),
                                                                          ImmutablePair.make(3, 1),
                                                                          ImmutablePair.make(4, 1),
                                                                          ImmutablePair.make(4, 3),
                                                                          ImmutablePair.make(6, 5),
                                                                          ImmutablePair.make(6, 9),
                                                                          ImmutablePair.make(7, 4),
                                                                          ImmutablePair.make(7, 6),
                                                                          ImmutablePair.make(8, 3),
                                                                          ImmutablePair.make(8, 7),
                                                                          ImmutablePair.make(9,
                                                                                             8)));

        List<Set<Integer>> result = StronglyConnectedComponents.findSCC(digraph);

        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(result.contains(Collections.singleton(5)));
        Assertions.assertTrue(result.contains(Collections.singleton(2)));
        Assertions.assertTrue(result.contains(new HashSet<>(Arrays.asList(0, 1, 3, 4))));
        Assertions.assertTrue(result.contains(new HashSet<>(Arrays.asList(6, 7, 8, 9))));
    }
}
