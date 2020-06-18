// Tests: Algorithm for lowest common ancestor
package algolib.graphs.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.graphs.TreeGraph;

public class LowestCommonAncestorTest
{
    private LowestCommonAncestor<Integer, Void, Void> testObject;

    @BeforeEach
    public void setUp()
    {
        TreeGraph<Integer, Void, Void> tree = new TreeGraph<>(0);
        tree.addVertex(1, 0);
        tree.addVertex(2, 0);
        tree.addVertex(3, 1);
        tree.addVertex(4, 1);
        tree.addVertex(5, 1);
        tree.addVertex(6, 2);
        tree.addVertex(7, 4);
        tree.addVertex(8, 6);
        tree.addVertex(9, 6);

        testObject = new LowestCommonAncestor<>(tree, 0);
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void find_WhenSameVertex_ThenVertexIsLCA()
    {
        // given
        Integer vertex = 6;
        // when
        Integer result = testObject.find(vertex, vertex);
        // then
        Assertions.assertThat(result).isEqualTo(vertex);
    }

    @Test
    public void find_WhenVerticesInDifferentSubtrees_ThenLCA()
    {
        // when
        Integer result = testObject.find(5, 7);
        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void find_WhenVerticesSwapped_ThenSameLCA()
    {
        // when
        Integer result1 = testObject.find(5, 7);
        Integer result2 = testObject.find(7, 5);
        // then
        Assertions.assertThat(result1).isEqualTo(1);
        Assertions.assertThat(result2).isEqualTo(result1);
    }

    @Test
    public void find_WhenRootIsCommonAncestor_ThenRoot()
    {
        // when
        Integer result = testObject.find(3, 9);
        // then
        Assertions.assertThat(result).isEqualTo(testObject.root);
    }

    @Test
    public void find_WhenVerticesAreOnSamePathFromRoot_ThenLCAIsCloserToRoot()
    {
        //given
        Integer vertex1 = 8;
        Integer vertex2 = 2;
        // when
        Integer result = testObject.find(vertex1, vertex2);
        // then
        Assertions.assertThat(result).isEqualTo(vertex2);
    }

    @Test
    public void find_WhenRootIsOneOfVertices_ThenRoot()
    {
        // when
        Integer result = testObject.find(4, testObject.root);
        // then
        Assertions.assertThat(result).isEqualTo(testObject.root);
    }
}
