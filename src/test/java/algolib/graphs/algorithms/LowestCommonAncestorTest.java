package algolib.graphs.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.TreeGraph;
import algolib.graphs.Vertex;

// Tests: Algorithm for lowest common ancestor
public class LowestCommonAncestorTest
{
    private LowestCommonAncestor<Integer, Void, Void> testObject;

    @BeforeEach
    public void setUp()
    {
        TreeGraph<Integer, Void, Void> tree = new TreeGraph<>(0);
        tree.addVertex(1, tree.getVertex(0));
        tree.addVertex(2, tree.getVertex(0));
        tree.addVertex(3, tree.getVertex(1));
        tree.addVertex(4, tree.getVertex(1));
        tree.addVertex(5, tree.getVertex(1));
        tree.addVertex(6, tree.getVertex(2));
        tree.addVertex(7, tree.getVertex(4));
        tree.addVertex(8, tree.getVertex(6));
        tree.addVertex(9, tree.getVertex(6));

        testObject = new LowestCommonAncestor<>(tree, tree.getVertex(0));
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void findLca_WhenSameVertex_ThenVertexIsLowestCommonAncestor()
    {
        // given
        Vertex<Integer> vertex = testObject.graph.getVertex(6);
        // when
        Vertex<Integer> result = testObject.findLca(vertex, vertex);
        // then
        Assertions.assertThat(result).isEqualTo(vertex);
    }

    @Test
    public void findLca_WhenVerticesInDifferentSubtrees_ThenLowestCommonAncestor()
    {
        // when
        Vertex<Integer> result =
                testObject.findLca(testObject.graph.getVertex(5), testObject.graph.getVertex(7));
        // then
        Assertions.assertThat(result).isEqualTo(testObject.graph.getVertex(1));
    }

    @Test
    public void findLca_WhenVerticesSwapped_ThenSameLowestCommonAncestor()
    {
        // given
        Vertex<Integer> vertex1 = testObject.graph.getVertex(5);
        Vertex<Integer> vertex2 = testObject.graph.getVertex(7);
        // when
        Vertex<Integer> result1 = testObject.findLca(vertex1, vertex2);
        Vertex<Integer> result2 = testObject.findLca(vertex2, vertex1);
        // then
        Assertions.assertThat(result1).isEqualTo(testObject.graph.getVertex(1));
        Assertions.assertThat(result2).isEqualTo(result1);
    }

    @Test
    public void findLca_WhenRootIsCommonAncestor_ThenRoot()
    {
        // when
        Vertex<Integer> result =
                testObject.findLca(testObject.graph.getVertex(3), testObject.graph.getVertex(9));
        // then
        Assertions.assertThat(result).isEqualTo(testObject.root);
    }

    @Test
    public void findLca_WhenVerticesAreOnSamePathFromRoot_ThenCloserToRoot()
    {
        //given
        Vertex<Integer> vertex1 = testObject.graph.getVertex(8);
        Vertex<Integer> vertex2 = testObject.graph.getVertex(2);
        // when
        Vertex<Integer> result = testObject.findLca(vertex1, vertex2);
        // then
        Assertions.assertThat(result).isEqualTo(vertex2);
    }

    @Test
    public void findLca_WhenRootIsOneOfVertices_ThenRoot()
    {
        // when
        Vertex<Integer> result = testObject.findLca(testObject.graph.getVertex(4), testObject.root);
        // then
        Assertions.assertThat(result).isEqualTo(testObject.root);
    }
}
