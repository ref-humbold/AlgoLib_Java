// TESTY DLA ALGORYTMU NAJNIŻSZEGO WSPÓLNEGO PRZODKA
package algolib.graphs;

import java.util.Arrays;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algolib.tuples.ImmutablePair;

public class LowestCommonAncestorTest
{
    private TreeGraph tree;

    @Before
    public void setUp()
    {
        tree = new TreeGraph(10, Arrays.asList(ImmutablePair.make(0, 1), ImmutablePair.make(0, 2),
                                               ImmutablePair.make(1, 3), ImmutablePair.make(1, 4),
                                               ImmutablePair.make(1, 5), ImmutablePair.make(2, 6),
                                               ImmutablePair.make(4, 7), ImmutablePair.make(6, 8),
                                               ImmutablePair.make(6, 9)));
    }

    @After
    public void tearDown()
    {
        tree = null;
    }

    @Test
    public void findLCAWhenSameVertex()
    {
        int vertex = 6;

        int result = LowestCommonAncestor.findLCA(tree, vertex, vertex);

        Assert.assertEquals(vertex, result);
    }

    @Test
    public void findLCAWhenVerticesChanged()
    {
        int vertex1 = 5;
        int vertex2 = 7;

        int result1 = LowestCommonAncestor.findLCA(tree, vertex1, vertex2);
        int result2 = LowestCommonAncestor.findLCA(tree, vertex2, vertex1);

        Assert.assertEquals(1, result1);
        Assert.assertEquals(1, result2);
    }

    @Test
    public void findLCAWhenVerticesInDifferentSubtrees()
    {
        int vertex1 = 5;
        int vertex2 = 7;

        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2);

        Assert.assertEquals(1, result);
    }

    @Test
    public void findLCAWhenRootIsLCA()
    {
        int vertex1 = 3;
        int vertex2 = 9;
        int root = 0;

        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2, root);

        Assert.assertEquals(root, result);
    }

    @Test
    public void findLCAWhenVerticesAreOffsprings()
    {
        int vertex1 = 8;
        int vertex2 = 2;

        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2);

        Assert.assertEquals(vertex2, result);
    }

    @Test
    public void findLCAWhenRootIsOneOfVertices()
    {
        int vertex1 = 4;
        int vertex2 = 0;

        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2, vertex2);

        Assert.assertEquals(vertex2, result);
    }
}
