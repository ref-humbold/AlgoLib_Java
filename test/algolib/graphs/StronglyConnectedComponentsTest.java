package algolib.graphs;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algolib.tuples.ImmutablePair;

public class StronglyConnectedComponentsTest
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

        Assert.assertEquals(4, result.size());
        Assert.assertTrue(result.contains(Collections.singleton(5)));
        Assert.assertTrue(result.contains(Collections.singleton(2)));
        Assert.assertTrue(result.contains(new HashSet<>(Arrays.asList(0, 1, 3, 4))));
        Assert.assertTrue(result.contains(new HashSet<>(Arrays.asList(6, 7, 8, 9))));
    }
}
