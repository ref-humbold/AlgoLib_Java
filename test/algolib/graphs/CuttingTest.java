// TESTY DLA ALGORYTMÓW WYSZUKIWANIA MOSTÓW I PUNKTÓW ARTYKULACJI
package algolib.graphs;

import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algolib.tuples.ComparablePair;
import algolib.tuples.ImmutablePair;

public class CuttingTest
{
    private UndirectedGraph graph;

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
        graph = null;
    }

    @Test
    public void testFindBridgesWhenPresentBridges()
    {
        graph = new UndirectedSimpleGraph(12, Arrays.asList(ImmutablePair.make(0, 1),
                                                            ImmutablePair.make(0, 2),
                                                            ImmutablePair.make(0, 7),
                                                            ImmutablePair.make(1, 2),
                                                            ImmutablePair.make(1, 3),
                                                            ImmutablePair.make(1, 4),
                                                            ImmutablePair.make(3, 5),
                                                            ImmutablePair.make(4, 5),
                                                            ImmutablePair.make(5, 6),
                                                            ImmutablePair.make(7, 8),
                                                            ImmutablePair.make(7, 9),
                                                            ImmutablePair.make(7, 11),
                                                            ImmutablePair.make(8, 9),
                                                            ImmutablePair.make(9, 10),
                                                            ImmutablePair.make(9, 11),
                                                            ImmutablePair.make(10, 11)));

        Object[] result = Cutting.findEdgeCut(graph).toArray();

        Arrays.sort(result);

        Assert.assertArrayEquals(new Object[]{ImmutablePair.make(0, 7), ImmutablePair.make(5, 6)},
                                 result);
    }

    @Test
    public void testFindBridgesWhenNoBridges()
    {
        graph = new UndirectedSimpleGraph(6, Arrays.asList(ImmutablePair.make(0, 1),
                                                           ImmutablePair.make(0, 2),
                                                           ImmutablePair.make(1, 2),
                                                           ImmutablePair.make(1, 3),
                                                           ImmutablePair.make(1, 4),
                                                           ImmutablePair.make(3, 5),
                                                           ImmutablePair.make(4, 5)));

        Collection<ComparablePair<Integer, Integer>> result = Cutting.findEdgeCut(graph);

        Assert.assertArrayEquals(new Object[]{}, result.toArray());
    }

    @Test
    public void testFindVertexSeparatorsWhenPresentSeparators()
    {
        graph = new UndirectedSimpleGraph(12, Arrays.asList(ImmutablePair.make(0, 1),
                                                            ImmutablePair.make(0, 2),
                                                            ImmutablePair.make(0, 7),
                                                            ImmutablePair.make(1, 2),
                                                            ImmutablePair.make(1, 3),
                                                            ImmutablePair.make(1, 4),
                                                            ImmutablePair.make(3, 5),
                                                            ImmutablePair.make(4, 5),
                                                            ImmutablePair.make(5, 6),
                                                            ImmutablePair.make(7, 8),
                                                            ImmutablePair.make(7, 9),
                                                            ImmutablePair.make(7, 11),
                                                            ImmutablePair.make(8, 9),
                                                            ImmutablePair.make(9, 10),
                                                            ImmutablePair.make(9, 11),
                                                            ImmutablePair.make(10, 11)));

        Object[] result = Cutting.findVertexCut(graph).toArray();

        Arrays.sort(result);

        Assert.assertArrayEquals(new Integer[]{0, 1, 5, 7}, result);
    }

    @Test
    public void testFindVertexSeparatorsWhenNoSeparators()
    {
        graph = new UndirectedSimpleGraph(6, Arrays.asList(ImmutablePair.make(0, 1),
                                                           ImmutablePair.make(0, 2),
                                                           ImmutablePair.make(1, 2),
                                                           ImmutablePair.make(1, 3),
                                                           ImmutablePair.make(1, 4),
                                                           ImmutablePair.make(2, 3),
                                                           ImmutablePair.make(3, 5),
                                                           ImmutablePair.make(4, 5)));

        Collection<Integer> result = Cutting.findVertexCut(graph);

        Assert.assertArrayEquals(new Object[]{}, result.toArray());
    }
}
