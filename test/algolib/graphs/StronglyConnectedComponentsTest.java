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
        DirectedGraph digraph = new DirectedSimpleGraph(10, Arrays.asList(Pair.make(0, 4),
                                                                          Pair.make(0, 5),
                                                                          Pair.make(1, 0),
                                                                          Pair.make(2, 3),
                                                                          Pair.make(3, 1),
                                                                          Pair.make(4, 1),
                                                                          Pair.make(4, 3),
                                                                          Pair.make(6, 5),
                                                                          Pair.make(6, 9),
                                                                          Pair.make(7, 4),
                                                                          Pair.make(7, 6),
                                                                          Pair.make(8, 3),
                                                                          Pair.make(8, 7),
                                                                          Pair.make(9, 8)));

        List<Set<Integer>> result = StronglyConnectedComponents.findSCC(digraph);

        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(result.contains(Collections.singleton(5)));
        Assertions.assertTrue(result.contains(Collections.singleton(2)));
        Assertions.assertTrue(result.contains(new HashSet<>(Arrays.asList(0, 1, 3, 4))));
        Assertions.assertTrue(result.contains(new HashSet<>(Arrays.asList(6, 7, 8, 9))));
    }
}
