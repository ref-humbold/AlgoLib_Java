package ref_humbold.algolib.graphs;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ref_humbold.algolib.structures.Pair;

public class CuttingTest
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
    public void testFindBridgesWhenPresentBridges()
    {
        UndirectedGraph graph = new UndirectedGraph(12,
                                                    Arrays.asList(Pair.make(0, 1), Pair.make(0, 2),
                                                                  Pair.make(0, 7), Pair.make(1, 2),
                                                                  Pair.make(1, 3), Pair.make(1, 4),
                                                                  Pair.make(3, 5), Pair.make(4, 5),
                                                                  Pair.make(5, 6), Pair.make(7, 8),
                                                                  Pair.make(7, 9), Pair.make(7, 11),
                                                                  Pair.make(8, 9), Pair.make(9, 10),
                                                                  Pair.make(9, 11),
                                                                  Pair.make(10, 11)));

        Object[] result = Cutting.findBridges(graph).toArray();

        Arrays.sort(result);

        Assert.assertArrayEquals(new Object[]{Pair.make(0, 7), Pair.make(5, 6)}, result);
    }

    @Test
    public void testFindBridgesWhenNoBridges()
    {
        UndirectedGraph graph = new UndirectedGraph(6,
                                                    Arrays.asList(Pair.make(0, 1), Pair.make(0, 2),
                                                                  Pair.make(1, 2), Pair.make(1, 3),
                                                                  Pair.make(1, 4), Pair.make(3, 5),
                                                                  Pair.make(4, 5)));

        Collection<Pair<Integer, Integer>> result = Cutting.findBridges(graph);

        Assert.assertArrayEquals(new Object[]{}, result.toArray());
    }

    @Test
    public void testFindVertexSeparatorsWhenPresentSeparators()
    {
        UndirectedGraph graph = new UndirectedGraph(12,
                                                    Arrays.asList(Pair.make(0, 1), Pair.make(0, 2),
                                                                  Pair.make(0, 7), Pair.make(1, 2),
                                                                  Pair.make(1, 3), Pair.make(1, 4),
                                                                  Pair.make(3, 5), Pair.make(4, 5),
                                                                  Pair.make(5, 6), Pair.make(7, 8),
                                                                  Pair.make(7, 9), Pair.make(7, 11),
                                                                  Pair.make(8, 9), Pair.make(9, 10),
                                                                  Pair.make(9, 11),
                                                                  Pair.make(10, 11)));

        Object[] result = Cutting.findVertexSeparators(graph).toArray();

        Arrays.sort(result);

        Assert.assertArrayEquals(new Integer[]{0, 1, 5, 7}, result);
    }

    @Test
    public void testFindVertexSeparatorsWhenNoSeparators()
    {
        UndirectedGraph graph = new UndirectedGraph(6,
                                                    Arrays.asList(Pair.make(0, 1), Pair.make(0, 2),
                                                                  Pair.make(1, 2), Pair.make(1, 3),
                                                                  Pair.make(1, 4), Pair.make(2, 3),
                                                                  Pair.make(3, 5),
                                                                  Pair.make(4, 5)));

        Collection<Integer> result = Cutting.findVertexSeparators(graph);

        Assert.assertArrayEquals(new Object[]{}, result.toArray());
    }
}
