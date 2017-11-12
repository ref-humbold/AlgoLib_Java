// TESTY DLA ALGORYTMÓW WYSZUKIWANIA MOSTÓW I PUNKTÓW ARTYKULACJI
package ref_humbold.algolib.graphs;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ref_humbold.algolib.tuples.ComparablePair;
import ref_humbold.algolib.tuples.Pair;

public class CuttingTest
{
    @Before
    public void setUp()
        throws Exception
    {
    }

    @After
    public void tearDown()
        throws Exception
    {
    }

    @Test
    public void testFindBridgesWhenPresentBridges()
    {
        UndirectedGraph graph = new UndirectedSimpleGraph(12, Arrays.asList(Pair.make(0, 1),
                                                                            Pair.make(0, 2),
                                                                            Pair.make(0, 7),
                                                                            Pair.make(1, 2),
                                                                            Pair.make(1, 3),
                                                                            Pair.make(1, 4),
                                                                            Pair.make(3, 5),
                                                                            Pair.make(4, 5),
                                                                            Pair.make(5, 6),
                                                                            Pair.make(7, 8),
                                                                            Pair.make(7, 9),
                                                                            Pair.make(7, 11),
                                                                            Pair.make(8, 9),
                                                                            Pair.make(9, 10),
                                                                            Pair.make(9, 11),
                                                                            Pair.make(10, 11)));

        Object[] result = Cutting.findEdgeCut(graph).toArray();

        Arrays.sort(result);

        Assert.assertArrayEquals(new Object[]{Pair.make(0, 7), Pair.make(5, 6)}, result);
    }

    @Test
    public void testFindBridgesWhenNoBridges()
    {
        UndirectedGraph graph = new UndirectedSimpleGraph(6, Arrays.asList(Pair.make(0, 1),
                                                                           Pair.make(0, 2),
                                                                           Pair.make(1, 2),
                                                                           Pair.make(1, 3),
                                                                           Pair.make(1, 4),
                                                                           Pair.make(3, 5),
                                                                           Pair.make(4, 5)));

        Collection<ComparablePair<Integer, Integer>> result = Cutting.findEdgeCut(graph);

        Assert.assertArrayEquals(new Object[]{}, result.toArray());
    }

    @Test
    public void testFindVertexSeparatorsWhenPresentSeparators()
    {
        UndirectedGraph graph = new UndirectedSimpleGraph(12, Arrays.asList(Pair.make(0, 1),
                                                                            Pair.make(0, 2),
                                                                            Pair.make(0, 7),
                                                                            Pair.make(1, 2),
                                                                            Pair.make(1, 3),
                                                                            Pair.make(1, 4),
                                                                            Pair.make(3, 5),
                                                                            Pair.make(4, 5),
                                                                            Pair.make(5, 6),
                                                                            Pair.make(7, 8),
                                                                            Pair.make(7, 9),
                                                                            Pair.make(7, 11),
                                                                            Pair.make(8, 9),
                                                                            Pair.make(9, 10),
                                                                            Pair.make(9, 11),
                                                                            Pair.make(10, 11)));

        Object[] result = Cutting.findVertexCut(graph).toArray();

        Arrays.sort(result);

        Assert.assertArrayEquals(new Integer[]{0, 1, 5, 7}, result);
    }

    @Test
    public void testFindVertexSeparatorsWhenNoSeparators()
    {
        UndirectedGraph graph = new UndirectedSimpleGraph(6, Arrays.asList(Pair.make(0, 1),
                                                                           Pair.make(0, 2),
                                                                           Pair.make(1, 2),
                                                                           Pair.make(1, 3),
                                                                           Pair.make(1, 4),
                                                                           Pair.make(2, 3),
                                                                           Pair.make(3, 5),
                                                                           Pair.make(4, 5)));

        Collection<Integer> result = Cutting.findVertexCut(graph);

        Assert.assertArrayEquals(new Object[]{}, result.toArray());
    }
}
