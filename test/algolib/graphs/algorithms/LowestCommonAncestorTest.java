// TESTY DLA ALGORYTMU NAJNIŻSZEGO WSPÓLNEGO PRZODKA
package algolib.graphs.algorithms;

import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.TreeGraph;
import algolib.tuples.Pair;

public class LowestCommonAncestorTest
{
    private TreeGraph tree;

    @BeforeEach
    public void setUp()
    {
        tree = new TreeGraph(10, Arrays.asList(Pair.make(0, 1), Pair.make(0, 2), Pair.make(1, 3),
                                               Pair.make(1, 4), Pair.make(1, 5), Pair.make(2, 6),
                                               Pair.make(4, 7), Pair.make(6, 8), Pair.make(6, 9)));
    }

    @AfterEach
    public void tearDown()
    {
        tree = null;
    }

    @Test
    public void findLCA_WhenSameVertex()
    {
        int vertex = 6;

        int result = LowestCommonAncestor.findLCA(tree, vertex, vertex);

        Assertions.assertEquals(vertex, result);
    }

    @Test
    public void findLCA_WhenVerticesChanged()
    {
        int vertex1 = 5;
        int vertex2 = 7;

        int result1 = LowestCommonAncestor.findLCA(tree, vertex1, vertex2);
        int result2 = LowestCommonAncestor.findLCA(tree, vertex2, vertex1);

        Assertions.assertEquals(1, result1);
        Assertions.assertEquals(1, result2);
    }

    @Test
    public void findLCA_WhenVerticesInDifferentSubtrees()
    {
        int vertex1 = 5;
        int vertex2 = 7;

        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void findLCA_WhenRootIsLCA()
    {
        int vertex1 = 3;
        int vertex2 = 9;
        int root = 0;

        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2, root);

        Assertions.assertEquals(root, result);
    }

    @Test
    public void findLCA_WhenVerticesAreOffsprings()
    {
        int vertex1 = 8;
        int vertex2 = 2;

        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2);

        Assertions.assertEquals(vertex2, result);
    }

    @Test
    public void findLCA_WhenRootIsOneOfVertices()
    {
        int vertex1 = 4;
        int vertex2 = 0;

        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2, vertex2);

        Assertions.assertEquals(vertex2, result);
    }
}
