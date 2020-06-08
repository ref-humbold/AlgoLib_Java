// Tests: Algorithm for lowest common ancestor
package algolib.graphs.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.TreeGraph;

public class LowestCommonAncestorTest
{
    private TreeGraph<Void, Void> tree;
    private Vertex<Void> root;

    @BeforeEach
    public void setUp()
    {
        tree = new TreeGraph<>(null);
        tree.addVertex(null, null, tree.getVertex(0));
        tree.addVertex(null, null, tree.getVertex(0));
        tree.addVertex(null, null, tree.getVertex(1));
        tree.addVertex(null, null, tree.getVertex(1));
        tree.addVertex(null, null, tree.getVertex(1));
        tree.addVertex(null, null, tree.getVertex(2));
        tree.addVertex(null, null, tree.getVertex(4));
        tree.addVertex(null, null, tree.getVertex(6));
        tree.addVertex(null, null, tree.getVertex(6));
        root = tree.getVertex(0);
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
        Vertex<Void> vertex = tree.getVertex(6);
        // when
        Vertex<Void> result = LowestCommonAncestor.findLCA(tree, vertex, vertex, root);
        // then
        Assertions.assertThat(result).isSameAs(vertex);
    }

    @Test
    public void findLCA_WhenVerticesInDifferentSubtrees_ThenLCA()
    {
        // when
        Vertex<Void> result =
                LowestCommonAncestor.findLCA(tree, tree.getVertex(5), tree.getVertex(7), root);
        // then
        Assertions.assertThat(result).isSameAs(tree.getVertex(1));
    }

    @Test
    public void findLCA_WhenVerticesSwapped_ThenSameLCA()
    {
        // when
        Vertex<Void> result1 =
                LowestCommonAncestor.findLCA(tree, tree.getVertex(5), tree.getVertex(7), root);
        Vertex<Void> result2 =
                LowestCommonAncestor.findLCA(tree, tree.getVertex(7), tree.getVertex(5), root);
        // then
        Assertions.assertThat(result1).isSameAs(tree.getVertex(1));
        Assertions.assertThat(result2).isSameAs(result1);
    }

    @Test
    public void findLCA_WhenRootIsCommonAncestor_ThenLCAIsRoot()
    {
        // when
        Vertex<Void> result =
                LowestCommonAncestor.findLCA(tree, tree.getVertex(3), tree.getVertex(9), root);
        // then
        Assertions.assertThat(result).isSameAs(root);
    }

    @Test
    public void findLCA_WhenVerticesAreOnSamePathFromRoot_ThenLCAIsCloserToRoot()
    {
        //given
        Vertex<Void> vertex1 = tree.getVertex(8);
        Vertex<Void> vertex2 = tree.getVertex(2);
        // when
        Vertex<Void> result = LowestCommonAncestor.findLCA(tree, vertex1, vertex2, root);
        // then
        Assertions.assertThat(result).isSameAs(vertex2);
    }

    @Test
    public void findLCA_WhenRootIsOneOfVertices_ThenRoot()
    {
        // when
        Vertex<Void> result = LowestCommonAncestor.findLCA(tree, tree.getVertex(4), root, root);
        // then
        Assertions.assertThat(result).isSameAs(root);
    }
}
