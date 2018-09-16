// TESTY DLA ALGORYTMÓW MINIMALNEGO DRZEWA SPINAJĄCEGO
package refhumbold.algolib.graphs;

import java.util.Arrays;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import refhumbold.algolib.tuples.ImmutableTriple;

public class MSTTest
{
    private UndirectedWeightedSimpleGraph graph;

    @Before
    public void setUp()
    {
        graph = new UndirectedWeightedSimpleGraph(5, Arrays.asList(ImmutableTriple.make(0, 1, -1.0),
                                                                   ImmutableTriple.make(0, 2, 4.0),
                                                                   ImmutableTriple.make(1, 2, 9.0),
                                                                   ImmutableTriple.make(1, 3, 7.0),
                                                                   ImmutableTriple.make(1, 4, 12.0),
                                                                   ImmutableTriple.make(2, 4, 6.0),
                                                                   ImmutableTriple.make(3, 4,
                                                                                        3.0)));
    }

    @After
    public void tearDown()
    {
        graph = null;
    }

    @Test
    public void testKruskal()
    {
        double result = MST.kruskal(graph);

        Assert.assertEquals(12.0, result, 0.000001);
    }

    @Test
    public void testPrim()
    {
        double result = MST.prim(graph, 0);

        Assert.assertEquals(12.0, result, 0.000001);
    }

    @Test
    public void testPrimWhenDifferentSources()
    {
        double result1 = MST.prim(graph, 1);
        double result4 = MST.prim(graph, 4);

        Assert.assertEquals(12.0, result1, 0.000001);
        Assert.assertEquals(12.0, result4, 0.000001);
    }
}
