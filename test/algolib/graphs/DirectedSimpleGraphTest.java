// Tests: Structure of directed simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DirectedSimpleGraphTest
{
    private DirectedSimpleGraph<Void, Void> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DirectedSimpleGraph<>(Collections.nCopies(10, null));
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
        int result = testObject.getVerticesCount();
        // then
        Assertions.assertThat(result).isEqualTo(10L);
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
                                   new Vertex<>(6, null), new Vertex<>(7, null),
                                   new Vertex<>(8, null), new Vertex<>(9, null));
    }

    @Test
    public void getVertex_WhenIndexInRange_ThenVertex()
    {
        // given
        int index = 4;
        List<Vertex<Void>> vertices = testObject.getVertices();
        // when
        Vertex<Void> result = testObject.getVertex(index);
        // then
        Assertions.assertThat(result).isSameAs(vertices.get(index));
    }

    @Test
    public void getVertex_WhenIndexOutOfRange_ThenIndexOutOfBoundsException()
    {
        // given
        int index = 29;
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.getVertex(index));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void addVertex_ThenNewVertex()
    {
        // when
        Vertex<Void> result = testObject.addVertex(null);
        // then
        Assertions.assertThat(result.index).isEqualTo(10);
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(11);
        Assertions.assertThat(testObject.getNeighbours(result)).isEmpty();
    }

    @Test
    public void getEdgesCount_ThenNumberOfEdges()
    {
        // given
        testObject.addEdge(testObject.getVertex(7), testObject.getVertex(7), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(5), null);
        testObject.addEdge(testObject.getVertex(2), testObject.getVertex(4), null);
        testObject.addEdge(testObject.getVertex(8), testObject.getVertex(0), null);
        testObject.addEdge(testObject.getVertex(6), testObject.getVertex(3), null);
        testObject.addEdge(testObject.getVertex(3), testObject.getVertex(6), null);
        testObject.addEdge(testObject.getVertex(9), testObject.getVertex(3), null);
        testObject.addEdge(testObject.getVertex(8), testObject.getVertex(0), null);
        // when
        long result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(7L);
    }

    @Test
    public void getEdges_ThenAllEdges()
    {
        // given
        testObject.addEdge(testObject.getVertex(7), testObject.getVertex(7), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(5), null);
        testObject.addEdge(testObject.getVertex(2), testObject.getVertex(4), null);
        testObject.addEdge(testObject.getVertex(8), testObject.getVertex(0), null);
        testObject.addEdge(testObject.getVertex(6), testObject.getVertex(3), null);
        testObject.addEdge(testObject.getVertex(3), testObject.getVertex(6), null);
        testObject.addEdge(testObject.getVertex(9), testObject.getVertex(3), null);
        testObject.addEdge(testObject.getVertex(8), testObject.getVertex(0), null);
        // when
        List<Edge<Void, Void>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result).isSorted();
        Assertions.assertThat(result)
                  .containsExactly(
                          new Edge<>(testObject.getVertex(1), testObject.getVertex(5), null),
                          new Edge<>(testObject.getVertex(2), testObject.getVertex(4), null),
                          new Edge<>(testObject.getVertex(3), testObject.getVertex(6), null),
                          new Edge<>(testObject.getVertex(6), testObject.getVertex(3), null),
                          new Edge<>(testObject.getVertex(7), testObject.getVertex(7), null),
                          new Edge<>(testObject.getVertex(8), testObject.getVertex(0), null),
                          new Edge<>(testObject.getVertex(9), testObject.getVertex(3), null));
    }

    @Test
    public void getEdge_WhenInDirection_ThenEdge()
    {
        // given
        Vertex<Void> source = testObject.getVertex(9);
        Vertex<Void> destination = testObject.getVertex(5);

        testObject.addEdge(source, destination, null);
        // when
        Edge<Void, Void> result = testObject.getEdge(source, destination);
        // then
        Assertions.assertThat(result.source).isSameAs(source);
        Assertions.assertThat(result.destination).isSameAs(destination);
    }

    @Test
    public void getEdge_WhenNotInDirection_ThenNull()
    {
        // given
        Vertex<Void> source = testObject.getVertex(9);
        Vertex<Void> destination = testObject.getVertex(5);

        testObject.addEdge(source, destination, null);
        // when
        Edge<Void, Void> result = testObject.getEdge(destination, source);
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void getEdge_WhenNotExists_ThenNull()
    {
        // when
        Edge<Void, Void> result =
                testObject.getEdge(testObject.getVertex(1), testObject.getVertex(2));
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void addEdge_ThenNewEdge()
    {
        // when
        Edge<Void, Void> result =
                testObject.addEdge(testObject.getVertex(1), testObject.getVertex(5), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(5), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(1), null);
        // then
        Assertions.assertThat(result.source).isEqualTo(testObject.getVertex(1));
        Assertions.assertThat(result.destination).isEqualTo(testObject.getVertex(5));
        Assertions.assertThat(testObject.getNeighbours(testObject.getVertex(1)))
                  .containsOnly(testObject.getVertex(1), testObject.getVertex(5));
        Assertions.assertThat(testObject.getNeighbours(testObject.getVertex(5))).isEmpty();
    }

    @Test
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // given
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(3), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(4), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(7), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(9), null);
        testObject.addEdge(testObject.getVertex(2), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(6), testObject.getVertex(1), null);
        // when
        Collection<Vertex<Void>> result = testObject.getNeighbours(testObject.getVertex(1));
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result)
                  .containsOnly(testObject.getVertex(1), testObject.getVertex(3),
                                testObject.getVertex(4), testObject.getVertex(7),
                                testObject.getVertex(9));
    }

    @Test
    public void getAdjacentEdges_ThenDestinationVerticesOfOutgoingEdges()
    {
        // given
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(3), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(4), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(7), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(9), null);
        testObject.addEdge(testObject.getVertex(2), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(6), testObject.getVertex(1), null);
        // when
        Collection<Edge<Void, Void>> result = testObject.getAdjacentEdges(testObject.getVertex(1));
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(testObject.getVertex(1), testObject.getVertex(1), null),
                                new Edge<>(testObject.getVertex(1), testObject.getVertex(3), null),
                                new Edge<>(testObject.getVertex(1), testObject.getVertex(4), null),
                                new Edge<>(testObject.getVertex(1), testObject.getVertex(7), null),
                                new Edge<>(testObject.getVertex(1), testObject.getVertex(9), null));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // given
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(3), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(4), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(7), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(9), null);
        testObject.addEdge(testObject.getVertex(2), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(6), testObject.getVertex(1), null);
        // when
        long result = testObject.getOutputDegree(testObject.getVertex(1));
        // then
        Assertions.assertThat(result).isEqualTo(5L);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // given
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(3), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(4), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(7), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(9), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(2), null);
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(6), null);
        // when
        long result = testObject.getInputDegree(testObject.getVertex(1));
        // then
        Assertions.assertThat(result).isEqualTo(5L);
    }

    @Test
    public void reverse_ThenAllEdgesHaveReversedDirection()
    {
        // given
        testObject.addEdge(testObject.getVertex(1), testObject.getVertex(2), null);
        testObject.addEdge(testObject.getVertex(3), testObject.getVertex(5), null);
        testObject.addEdge(testObject.getVertex(4), testObject.getVertex(9), null);
        testObject.addEdge(testObject.getVertex(5), testObject.getVertex(4), null);
        testObject.addEdge(testObject.getVertex(5), testObject.getVertex(7), null);
        testObject.addEdge(testObject.getVertex(6), testObject.getVertex(2), null);
        testObject.addEdge(testObject.getVertex(6), testObject.getVertex(6), null);
        testObject.addEdge(testObject.getVertex(7), testObject.getVertex(8), null);
        testObject.addEdge(testObject.getVertex(9), testObject.getVertex(1), null);
        testObject.addEdge(testObject.getVertex(9), testObject.getVertex(6), null);
        // when
        testObject.reverse();
        // then
        Assertions.assertThat(testObject.getEdges())
                  .containsExactly(
                          new Edge<>(testObject.getVertex(1), testObject.getVertex(9), null),
                          new Edge<>(testObject.getVertex(2), testObject.getVertex(1), null),
                          new Edge<>(testObject.getVertex(2), testObject.getVertex(6), null),
                          new Edge<>(testObject.getVertex(4), testObject.getVertex(5), null),
                          new Edge<>(testObject.getVertex(5), testObject.getVertex(3), null),
                          new Edge<>(testObject.getVertex(6), testObject.getVertex(6), null),
                          new Edge<>(testObject.getVertex(6), testObject.getVertex(9), null),
                          new Edge<>(testObject.getVertex(7), testObject.getVertex(5), null),
                          new Edge<>(testObject.getVertex(8), testObject.getVertex(7), null),
                          new Edge<>(testObject.getVertex(9), testObject.getVertex(4), null));
    }
}
