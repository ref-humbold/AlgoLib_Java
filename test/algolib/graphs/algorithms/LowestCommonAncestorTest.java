// Tests: Algorithm for lowest common ancestor
package algolib.graphs.algorithms;

import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
        tree = new TreeGraph(10, Arrays.asList(Pair.of(0, 1), Pair.of(0, 2), Pair.of(1, 3),
                                               Pair.of(1, 4), Pair.of(1, 5), Pair.of(2, 6),
                                               Pair.of(4, 7), Pair.of(6, 8), Pair.of(6, 9)));
    }

    @AfterEach
    public void tearDown()
    {
        tree = null;
    }

    @Test
    public void findLCA_WhenSameVertex_ThenVertexIsLCA()
    {
        // given
        int vertex = 6;
        // when
        int result = LowestCommonAncestor.findLCA(tree, vertex, vertex);
        // then
        Assertions.assertThat(result).isEqualTo(vertex);
    }

    @Test
    public void findLCA_WhenVerticesSwapped_ThenSameLCA()
    {
        // when
        int result1 = LowestCommonAncestor.findLCA(tree, 5, 7);
        int result2 = LowestCommonAncestor.findLCA(tree, 7, 5);
        // then
        Assertions.assertThat(result1).isEqualTo(1);
        Assertions.assertThat(result2).isEqualTo(result1);
    }

    @Test
    public void findLCA_WhenVerticesInDifferentSubtrees_ThenLCA()
    {
        // when
        int result = LowestCommonAncestor.findLCA(tree, 5, 7);
        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void findLCA_WhenRootIsCommonAncestor_ThenLCAIsRoot()
    {
        // when
        int result = LowestCommonAncestor.findLCA(tree, 3, 9);
        // then
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void findLCA_WhenVerticesAreOnSamePathFromRoot_ThenLCAIsCloserToRoot()
    {
        //given
        int vertex1 = 8;
        int vertex2 = 2;
        // when
        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2);
        // then
        Assertions.assertThat(result).isEqualTo(vertex2);
    }

    @Test
    public void findLCA_WhenRootIsOneOfVertices_ThenRoot()
    {
        // given
        int vertex1 = 4;
        int vertex2 = 0;
        // when
        int result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2, vertex2);
        // then
        Assertions.assertThat(result).isEqualTo(vertex2);
    }
}
