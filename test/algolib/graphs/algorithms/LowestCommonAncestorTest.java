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
    public void findLCA_WhenSameVertex_ThenVertexIsLCA()
    {
        // given
        Vertex<Integer> vertex = testObject.graph.getVertex(6);
        // when
        Vertex<Integer> result = testObject.findLCA(vertex, vertex);
        // then
        Assertions.assertThat(result).isEqualTo(vertex);
    }

    @Test
    public void findLCA_WhenVerticesInDifferentSubtrees_ThenLCA()
    {
        // when
        Vertex<Integer> result =
                testObject.findLCA(testObject.graph.getVertex(5), testObject.graph.getVertex(7));
        // then
        Assertions.assertThat(result).isEqualTo(testObject.graph.getVertex(1));
    }

    @Test
    public void findLCA_WhenVerticesSwapped_ThenSameLCA()
    {
        // given
        Vertex<Integer> vertex1 = testObject.graph.getVertex(5);
        Vertex<Integer> vertex2 = testObject.graph.getVertex(7);
        // when
        Vertex<Integer> result1 = testObject.findLCA(vertex1, vertex2);
        Vertex<Integer> result2 = testObject.findLCA(vertex2, vertex1);
        // then
        Assertions.assertThat(result1).isEqualTo(testObject.graph.getVertex(1));
        Assertions.assertThat(result2).isEqualTo(result1);
    }

    @Test
    public void findLCA_WhenRootIsCommonAncestor_ThenRoot()
    {
        // when
        Vertex<Integer> result =
                testObject.findLCA(testObject.graph.getVertex(3), testObject.graph.getVertex(9));
        // then
        Assertions.assertThat(result).isEqualTo(testObject.root);
    }

    @Test
    public void findLCA_WhenVerticesAreOnSamePathFromRoot_ThenLCAIsCloserToRoot()
    {
        //given
        Vertex<Integer> vertex1 = testObject.graph.getVertex(8);
        Vertex<Integer> vertex2 = testObject.graph.getVertex(2);
        // when
        Vertex<Integer> result = testObject.findLCA(vertex1, vertex2);
        // then
        Assertions.assertThat(result).isEqualTo(vertex2);
    }

    @Test
    public void findLCA_WhenRootIsOneOfVertices_ThenRoot()
    {
        // when
        Vertex<Integer> result = testObject.findLCA(testObject.graph.getVertex(4), testObject.root);
        // then
        Assertions.assertThat(result).isEqualTo(testObject.root);
    }
}
