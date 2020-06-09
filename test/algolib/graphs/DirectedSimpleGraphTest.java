// Tests: Structure of directed simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DirectedSimpleGraphTest
{
    private DirectedSimpleGraph<Integer, Void, Void> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));
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
        Collection<Integer> result = testObject.getVertices();
        // then
        Assertions.assertThat(result).containsOnly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void addVertex_ThenNewVertex()
    {
        // given
        int newVertex = 13;
        // when
        testObject.addVertex(newVertex);
        // then
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(11);
        Assertions.assertThat(testObject.getNeighbours(newVertex)).isEmpty();
    }

    @Test
    public void getEdgesCount_ThenNumberOfEdges()
    {
        // given
        testObject.addEdge(7, 7);
        testObject.addEdge(1, 5);
        testObject.addEdge(2, 4);
        testObject.addEdge(8, 0);
        testObject.addEdge(6, 3);
        testObject.addEdge(3, 6);
        testObject.addEdge(9, 3);
        testObject.addEdge(8, 0);
        // when
        long result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(7L);
    }

    @Test
    public void getEdges_ThenAllEdges()
    {
        // given
        testObject.addEdge(7, 7);
        testObject.addEdge(1, 5);
        testObject.addEdge(2, 4);
        testObject.addEdge(8, 0);
        testObject.addEdge(6, 3);
        testObject.addEdge(3, 6);
        testObject.addEdge(9, 3);
        testObject.addEdge(8, 0);
        // when
        Collection<Edge<Integer>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(1, 5), new Edge<>(2, 4), new Edge<>(3, 6),
                                new Edge<>(6, 3), new Edge<>(7, 7), new Edge<>(8, 0),
                                new Edge<>(9, 3));
    }

    @Test
    public void getEdge_WhenInDirection_ThenEdge()
    {
        // given
        Integer source = 9;
        Integer destination = 5;

        testObject.addEdge(source, destination);
        // when
        Edge<Integer> result = testObject.getEdge(source, destination);
        // then
        Assertions.assertThat(result.source).isSameAs(source);
        Assertions.assertThat(result.destination).isSameAs(destination);
    }

    @Test
    public void getEdge_WhenNotInDirection_ThenNull()
    {
        // given
        Integer source = 9;
        Integer destination = 5;

        testObject.addEdge(source, destination);
        // when
        Edge<Integer> result = testObject.getEdge(destination, source);
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void getEdge_WhenNotExists_ThenNull()
    {
        // when
        Edge<Integer> result = testObject.getEdge(1, 2);
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void addEdge_ThenNewEdge()
    {
        // when
        Edge<Integer> result = testObject.addEdge(1, 5);
        testObject.addEdge(1, 1);
        // then
        Assertions.assertThat(result.source).isEqualTo(1);
        Assertions.assertThat(result.destination).isEqualTo(5);
        Assertions.assertThat(testObject.getNeighbours(1)).containsOnly(1, 5);
        Assertions.assertThat(testObject.getNeighbours(5)).isEmpty();
    }

    @Test
    public void addEdge_WhenDuplicated_ThenExistingEdge()
    {
        // given
        int source = 3;
        int destination = 7;
        Edge<Integer> expected = testObject.addEdge(source, destination);
        // when
        Edge<Integer> result = testObject.addEdge(source, destination);
        // then
        Assertions.assertThat(result).isSameAs(expected);
    }

    @Test
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // given
        testObject.addEdge(1, 1);
        testObject.addEdge(1, 3);
        testObject.addEdge(1, 4);
        testObject.addEdge(1, 7);
        testObject.addEdge(1, 9);
        testObject.addEdge(2, 1);
        testObject.addEdge(6, 1);
        // when
        Collection<Integer> result = testObject.getNeighbours(1);
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result).containsOnly(1, 3, 4, 7, 9);
    }

    @Test
    public void getAdjacentEdges_ThenDestinationVerticesOfOutgoingEdges()
    {
        // given
        testObject.addEdge(1, 1);
        testObject.addEdge(1, 3);
        testObject.addEdge(1, 4);
        testObject.addEdge(1, 7);
        testObject.addEdge(1, 9);
        testObject.addEdge(2, 1);
        testObject.addEdge(6, 1);
        // when
        Collection<Edge<Integer>> result = testObject.getAdjacentEdges(1);
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(1, 1), new Edge<>(1, 3), new Edge<>(1, 4),
                                new Edge<>(1, 7), new Edge<>(1, 9));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // given
        testObject.addEdge(1, 1);
        testObject.addEdge(1, 3);
        testObject.addEdge(1, 4);
        testObject.addEdge(1, 7);
        testObject.addEdge(1, 9);
        testObject.addEdge(2, 1);
        testObject.addEdge(6, 1);
        // when
        long result = testObject.getOutputDegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(5L);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // given
        testObject.addEdge(1, 1);
        testObject.addEdge(3, 1);
        testObject.addEdge(4, 1);
        testObject.addEdge(7, 1);
        testObject.addEdge(9, 1);
        testObject.addEdge(1, 2);
        testObject.addEdge(1, 6);
        // when
        long result = testObject.getInputDegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(5L);
    }

    @Test
    public void reverse_ThenAllEdgesHaveReversedDirection()
    {
        // given
        testObject.addEdge(1, 2);
        testObject.addEdge(3, 5);
        testObject.addEdge(4, 9);
        testObject.addEdge(5, 4);
        testObject.addEdge(5, 7);
        testObject.addEdge(6, 2);
        testObject.addEdge(6, 6);
        testObject.addEdge(7, 8);
        testObject.addEdge(9, 1);
        testObject.addEdge(9, 6);
        // when
        testObject.reverse();
        // then
        Assertions.assertThat(testObject.getEdges())
                  .containsOnly(new Edge<>(1, 9), new Edge<>(2, 1), new Edge<>(2, 6),
                                new Edge<>(4, 5), new Edge<>(5, 3), new Edge<>(6, 6),
                                new Edge<>(6, 9), new Edge<>(7, 5), new Edge<>(8, 7),
                                new Edge<>(9, 4));
    }

    @Test
    public void reversedCopy_ThenNewGraphWithReversedEdges()
    {
        // given
        testObject.addEdge(1, 2);
        testObject.addEdge(3, 5);
        testObject.addEdge(4, 9);
        testObject.addEdge(5, 4);
        testObject.addEdge(5, 7);
        testObject.addEdge(6, 2);
        testObject.addEdge(6, 6);
        testObject.addEdge(7, 8);
        testObject.addEdge(9, 1);
        testObject.addEdge(9, 6);
        // when
        DirectedGraph<Integer, Void, Void> result = testObject.reversedCopy();
        // then
        Assertions.assertThat(result.getVertices()).hasSameElementsAs(testObject.getVertices());
        Assertions.assertThat(result.getEdges())
                  .containsOnly(new Edge<>(1, 9), new Edge<>(2, 1), new Edge<>(2, 6),
                                new Edge<>(4, 5), new Edge<>(5, 3), new Edge<>(6, 6),
                                new Edge<>(6, 9), new Edge<>(7, 5), new Edge<>(8, 7),
                                new Edge<>(9, 4));
    }
}
