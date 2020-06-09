// Tests: Structure of tree graph
package algolib.graphs;

import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TreeGraphTest
{
    private TreeGraph<Integer, Void, Void> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new TreeGraph<>(0);

        testObject.addVertex(1, 0);
        testObject.addVertex(2, 0);
        testObject.addVertex(3, 0);
        testObject.addVertex(4, 1);
        testObject.addVertex(5, 1);
        testObject.addVertex(6, 2);
        testObject.addVertex(7, 2);
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void getVerticesCount_ThenNumberOfVertices()
    {
        // when
        long result = testObject.getVerticesCount();
        // then
        Assertions.assertThat(result).isEqualTo(8L);
    }

    @Test
    public void getVertices_ThenAllVertices()
    {
        // when
        Collection<Integer> result = testObject.getVertices();
        // then
        Assertions.assertThat(result).containsOnly(0, 1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    public void addVertex_ThenNewVertex()
    {
        // given
        int newVertex = 13;
        // when
        testObject.addVertex(newVertex, 0);
        // then
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(9);
        Assertions.assertThat(testObject.getNeighbours(newVertex)).containsOnly(0);
    }

    @Test
    public void getEdgesCount_ThenNumberOfEdges()
    {
        // when
        long result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(7L);
    }

    @Test
    public void getEdges_ThenAllEdges()
    {
        // when
        Collection<Edge<Integer>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(1, 0), new Edge<>(2, 0), new Edge<>(3, 0),
                                new Edge<>(4, 1), new Edge<>(5, 1), new Edge<>(6, 2),
                                new Edge<>(7, 2));
    }

    @Test
    public void getEdge_WhenExists_ThenEdge()
    {
        // given
        Integer source = 5;
        Integer destination = 1;
        // when
        Edge<Integer> result = testObject.getEdge(source, destination);
        // then
        Assertions.assertThat(result.source).isSameAs(source);
        Assertions.assertThat(result.destination).isSameAs(destination);
    }

    @Test
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // when
        Collection<Integer> result = testObject.getNeighbours(1);
        // then
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result).containsOnly(0, 4, 5);
    }

    @Test
    public void getAdjacentEdges_ThenDestinationVerticesOfOutgoingEdges()
    {
        // when
        Collection<Edge<Integer>> result = testObject.getAdjacentEdges(1);
        // then
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(1, 0), new Edge<>(4, 1), new Edge<>(5, 1));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // when
        long result = testObject.getOutputDegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // when
        long result = testObject.getInputDegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(3);
    }
}
