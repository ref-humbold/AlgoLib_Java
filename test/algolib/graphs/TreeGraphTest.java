// Tests: Structure of tree graph
package algolib.graphs;

import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TreeGraphTest
{
    private TreeGraph<Void, Void> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new TreeGraph<>(null);

        testObject.addVertex(null, null, testObject.getVertex(0));
        testObject.addVertex(null, null, testObject.getVertex(0));
        testObject.addVertex(null, null, testObject.getVertex(0));
        testObject.addVertex(null, null, testObject.getVertex(1));
        testObject.addVertex(null, null, testObject.getVertex(1));
        testObject.addVertex(null, null, testObject.getVertex(2));
        testObject.addVertex(null, null, testObject.getVertex(2));
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
        List<Vertex<Void>> result = testObject.getVertices();
        // then
        Assertions.assertThat(result).isSorted();
        Assertions.assertThat(result)
                  .containsExactly(new Vertex<>(0, null), new Vertex<>(1, null),
                                   new Vertex<>(2, null), new Vertex<>(3, null),
                                   new Vertex<>(4, null), new Vertex<>(5, null),
                                   new Vertex<>(6, null), new Vertex<>(7, null));
    }

    @Test
    public void getVertex_WhenIndexInRange_ThenVertex()
    {
        // given
        int index = 7;
        List<Vertex<Void>> vertices = testObject.getVertices();
        // when
        Vertex<Void> result = testObject.getVertex(index);
        // then
        Assertions.assertThat(result).isSameAs(vertices.get(index));
    }

    @Test
    public void addVertex_ThenNewVertex()
    {
        // when
        Vertex<Void> result = testObject.addVertex(null, null, testObject.getVertex(0));
        // then
        Assertions.assertThat(result.index).isEqualTo(8);
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(9);
        Assertions.assertThat(testObject.getNeighbours(result))
                  .containsExactly(testObject.getVertex(0));
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
        List<Edge<Void, Void>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result).isSorted();
        Assertions.assertThat(result)
                  .containsExactly(
                          new Edge<>(testObject.getVertex(0), testObject.getVertex(1), null),
                          new Edge<>(testObject.getVertex(0), testObject.getVertex(2), null),
                          new Edge<>(testObject.getVertex(0), testObject.getVertex(3), null),
                          new Edge<>(testObject.getVertex(1), testObject.getVertex(4), null),
                          new Edge<>(testObject.getVertex(1), testObject.getVertex(5), null),
                          new Edge<>(testObject.getVertex(2), testObject.getVertex(6), null),
                          new Edge<>(testObject.getVertex(2), testObject.getVertex(7), null));
    }

    @Test
    public void getEdge_WhenExists_ThenEdge()
    {
        // given
        Vertex<Void> source = testObject.getVertex(1);
        Vertex<Void> destination = testObject.getVertex(5);
        // when
        Edge<Void, Void> result = testObject.getEdge(source, destination);
        // then
        Assertions.assertThat(result.source).isSameAs(source);
        Assertions.assertThat(result.destination).isSameAs(destination);
    }

    @Test
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // when
        Collection<Vertex<Void>> result = testObject.getNeighbours(testObject.getVertex(1));
        // then
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result)
                  .containsOnly(testObject.getVertex(0), testObject.getVertex(4),
                                testObject.getVertex(5));
    }

    @Test
    public void getAdjacentEdges_ThenDestinationVerticesOfOutgoingEdges()
    {
        // when
        Collection<Edge<Void, Void>> result = testObject.getAdjacentEdges(testObject.getVertex(1));
        // then
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(testObject.getVertex(0), testObject.getVertex(1), null),
                                new Edge<>(testObject.getVertex(1), testObject.getVertex(4), null),
                                new Edge<>(testObject.getVertex(1), testObject.getVertex(5), null));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // when
        long result = testObject.getOutputDegree(testObject.getVertex(1));
        // then
        Assertions.assertThat(result).isEqualTo(3L);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // when
        long result = testObject.getInputDegree(testObject.getVertex(1));
        // then
        Assertions.assertThat(result).isEqualTo(3L);
    }
}
