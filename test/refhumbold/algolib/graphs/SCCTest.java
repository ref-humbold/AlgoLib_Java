package refhumbold.algolib.graphs;

import java.util.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import refhumbold.algolib.tuples.Pair;

public class SCCTest
{
    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testFindSCC()
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

        List<Set<Integer>> result = SCC.findSCC(digraph);

        Assert.assertEquals(4, result.size());
        Assert.assertTrue(result.contains(Collections.singleton(5)));
        Assert.assertTrue(result.contains(Collections.singleton(2)));
        Assert.assertTrue(result.contains(new HashSet<>(Arrays.asList(0, 1, 3, 4))));
        Assert.assertTrue(result.contains(new HashSet<>(Arrays.asList(6, 7, 8, 9))));
    }
}
