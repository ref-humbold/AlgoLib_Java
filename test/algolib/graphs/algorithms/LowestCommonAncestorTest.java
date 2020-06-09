// Tests: Algorithm for lowest common ancestor
package algolib.graphs.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.TreeGraph;

public class LowestCommonAncestorTest
{
    private TreeGraph<Integer, Void, Void> tree;
    private Integer root;

    @BeforeEach
    public void setUp()
    {
        tree = new TreeGraph<>(0);
        tree.addVertex(1, 0);
        tree.addVertex(2, 0);
        tree.addVertex(3, 1);
        tree.addVertex(4, 1);
        tree.addVertex(5, 1);
        tree.addVertex(6, 2);
        tree.addVertex(7, 4);
        tree.addVertex(8, 6);
        tree.addVertex(9, 6);
        root = 0;
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
        Integer vertex = 6;
        // when
        Integer result = LowestCommonAncestor.findLCA(tree, vertex, vertex, root);
        // then
        Assertions.assertThat(result).isSameAs(vertex);
    }

    @Test
    public void findLCA_WhenVerticesInDifferentSubtrees_ThenLCA()
    {
        // when
        Integer result = LowestCommonAncestor.findLCA(tree, 5, 7, root);
        // then
        Assertions.assertThat(result).isSameAs(1);
    }

    @Test
    public void findLCA_WhenVerticesSwapped_ThenSameLCA()
    {
        // when
        Integer result1 = LowestCommonAncestor.findLCA(tree, 5, 7, root);
        Integer result2 = LowestCommonAncestor.findLCA(tree, 7, 5, root);
        // then
        Assertions.assertThat(result1).isSameAs(1);
        Assertions.assertThat(result2).isSameAs(result1);
    }

    @Test
    public void findLCA_WhenRootIsCommonAncestor_ThenLCAIsRoot()
    {
        // when
        Integer result = LowestCommonAncestor.findLCA(tree, 3, 9, root);
        // then
        Assertions.assertThat(result).isSameAs(root);
    }

    @Test
    public void findLCA_WhenVerticesAreOnSamePathFromRoot_ThenLCAIsCloserToRoot()
    {
        //given
        Integer vertex1 = 8;
        Integer vertex2 = 2;
        // when
        Integer result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2, root);
        // then
        Assertions.assertThat(result).isSameAs(vertex2);
    }

    @Test
    public void findLCA_WhenRootIsOneOfVertices_ThenRoot()
    {
        // when
        Integer result = LowestCommonAncestor.findLCA(tree, 4, root, root);
        // then
        Assertions.assertThat(result).isSameAs(root);
    }
}
