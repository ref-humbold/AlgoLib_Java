package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of directed simple graph
public class DirectedSimpleGraphTest
{
    private DirectedSimpleGraph<Integer, String, String> testObject;

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
    public void setProperty_getProperty_WhenSettingProperty_ThenProperty()
    {
        // given
        String vertexProperty = "x";
        String edgeProperty = "y";
        int vertex = 2;
        Edge<Integer> edge = testObject.addEdgeBetween(0, 1);
        // when
        testObject.setProperty(vertex, vertexProperty);
        testObject.setProperty(edge, edgeProperty);

        String resultVertex = testObject.getProperty(vertex);
        String resultEdge = testObject.getProperty(edge);
        // then
        Assertions.assertThat(resultVertex).isEqualTo(vertexProperty);
        Assertions.assertThat(resultEdge).isEqualTo(edgeProperty);
    }

    @Test
    public void getProperty_WhenNoProperty_ThenNull()
    {
        // given
        Edge<Integer> edge = testObject.addEdgeBetween(6, 7);
        // when
        String resultVertex = testObject.getProperty(4);
        String resultEdge = testObject.getProperty(edge);
        // then
        Assertions.assertThat(resultVertex).isNull();
        Assertions.assertThat(resultEdge).isNull();
    }

    @Test
    public void getProperty_WhenNotExisting_ThenIllegalArgumentException()
    {
        // when
        Throwable throwableVertex = Assertions.catchThrowable(() -> testObject.getProperty(14));
        Throwable throwableEdge1 =
                Assertions.catchThrowable(() -> testObject.getProperty(new Edge<>(2, 8)));
        Throwable throwableEdge2 =
                Assertions.catchThrowable(() -> testObject.getProperty(new Edge<>(0, -1)));
        // then
        Assertions.assertThat(throwableVertex).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThat(throwableEdge1).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThat(throwableEdge2).isInstanceOf(IllegalArgumentException.class);
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
    public void addVertex_WhenNewVertex_ThenTrue()
    {
        // given
        int newVertex = 13;
        String property = "qwerty";
        // when
        boolean result = testObject.addVertex(newVertex, property);
        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(11);
        Assertions.assertThat(testObject.getNeighbours(newVertex)).isEmpty();
        Assertions.assertThat(testObject.getProperty(newVertex)).isEqualTo(property);
    }

    @Test
    public void addVertex_WhenExistingVertex_ThenFalse()
    {
        // given
        int vertex = 6;
        String property = "qwerty";

        testObject.setProperty(vertex, property);
        // when
        boolean result = testObject.addVertex(vertex, "abcdefg");
        // then
        Assertions.assertThat(result).isFalse();
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(10);
        Assertions.assertThat(testObject.getProperty(vertex)).isEqualTo(property);
    }

    @Test
    public void getEdgesCount_ThenNumberOfEdges()
    {
        // given
        testObject.addEdgeBetween(7, 7);
        testObject.addEdgeBetween(1, 5);
        testObject.addEdgeBetween(2, 4);
        testObject.addEdgeBetween(8, 0);
        testObject.addEdgeBetween(6, 3);
        testObject.addEdgeBetween(3, 6);
        testObject.addEdgeBetween(9, 3);
        testObject.addEdgeBetween(8, 0);
        // when
        int result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(7L);
    }

    @Test
    public void getEdges_ThenAllEdges()
    {
        // given
        testObject.addEdgeBetween(7, 7);
        testObject.addEdgeBetween(1, 5);
        testObject.addEdgeBetween(2, 4);
        testObject.addEdgeBetween(8, 0);
        testObject.addEdgeBetween(6, 3);
        testObject.addEdgeBetween(3, 6);
        testObject.addEdgeBetween(9, 3);
        testObject.addEdgeBetween(8, 0);
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
        int source = 9;
        int destination = 5;

        testObject.addEdgeBetween(source, destination);
        // when
        Edge<Integer> result = testObject.getEdge(source, destination);
        // then
        Assertions.assertThat(result.source).isEqualTo(source);
        Assertions.assertThat(result.destination).isEqualTo(destination);
    }

    @Test
    public void getEdge_WhenReversedDirection_ThenNull()
    {
        // given
        int source = 9;
        int destination = 5;

        testObject.addEdgeBetween(source, destination);
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
    public void addEdgeBetween_WhenNewEdge_ThenCreatedEdge()
    {
        // given
        int vertex1 = 1;
        int vertex2 = 5;
        String property = "asdfgh";
        // when
        Edge<Integer> result = testObject.addEdgeBetween(vertex1, vertex2, property);
        testObject.addEdgeBetween(vertex1, vertex1);
        // then
        Assertions.assertThat(result.source).isEqualTo(vertex1);
        Assertions.assertThat(result.destination).isEqualTo(vertex2);
        Assertions.assertThat(testObject.getProperty(result)).isEqualTo(property);
        Assertions.assertThat(testObject.getNeighbours(vertex1)).containsOnly(vertex1, vertex2);
        Assertions.assertThat(testObject.getNeighbours(vertex2)).isEmpty();
    }

    @Test
    public void addEdgeBetween_WhenDuplicatedEdge_ThenExistingEdge()
    {
        // given
        int source = 3;
        int destination = 7;
        Edge<Integer> expected = testObject.addEdgeBetween(source, destination);
        // when
        Edge<Integer> result = testObject.addEdgeBetween(source, destination);
        // then
        Assertions.assertThat(result).isSameAs(expected);
    }

    @Test
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // given
        testObject.addEdgeBetween(1, 1);
        testObject.addEdgeBetween(1, 3);
        testObject.addEdgeBetween(1, 4);
        testObject.addEdgeBetween(1, 7);
        testObject.addEdgeBetween(1, 9);
        testObject.addEdgeBetween(2, 1);
        testObject.addEdgeBetween(6, 1);
        // when
        Collection<Integer> result = testObject.getNeighbours(1);
        // then
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result).containsOnly(1, 3, 4, 7, 9);
    }

    @Test
    public void getAdjacentEdges_ThenOutgoingEdges()
    {
        // given
        testObject.addEdgeBetween(1, 1);
        testObject.addEdgeBetween(1, 3);
        testObject.addEdgeBetween(1, 4);
        testObject.addEdgeBetween(1, 7);
        testObject.addEdgeBetween(1, 9);
        testObject.addEdgeBetween(2, 1);
        testObject.addEdgeBetween(6, 1);
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
        testObject.addEdgeBetween(1, 1);
        testObject.addEdgeBetween(1, 3);
        testObject.addEdgeBetween(1, 4);
        testObject.addEdgeBetween(1, 7);
        testObject.addEdgeBetween(1, 9);
        testObject.addEdgeBetween(2, 1);
        testObject.addEdgeBetween(6, 1);
        // when
        int result = testObject.getOutputDegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(5);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // given
        testObject.addEdgeBetween(1, 1);
        testObject.addEdgeBetween(3, 1);
        testObject.addEdgeBetween(4, 1);
        testObject.addEdgeBetween(7, 1);
        testObject.addEdgeBetween(9, 1);
        testObject.addEdgeBetween(1, 2);
        testObject.addEdgeBetween(1, 6);
        // when
        int result = testObject.getInputDegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(5);
    }

    @Test
    public void reverse_ThenAllEdgesHaveReversedDirection()
    {
        // given
        int vertex = 5;
        String vertexProperty = "123456";
        String edgeProperty = "zxcvb";
        Edge<Integer> edge = testObject.addEdgeBetween(1, 2);
        testObject.addEdgeBetween(3, 5);
        testObject.addEdgeBetween(4, 9);
        testObject.addEdgeBetween(5, 4);
        testObject.addEdgeBetween(5, 7);
        testObject.addEdgeBetween(6, 2);
        testObject.addEdgeBetween(6, 6);
        testObject.addEdgeBetween(7, 8);
        testObject.addEdgeBetween(9, 1);
        testObject.addEdgeBetween(9, 6);
        testObject.setProperty(vertex, vertexProperty);
        testObject.setProperty(edge, edgeProperty);
        // when
        testObject.reverse();
        // then
        Assertions.assertThat(testObject.getEdges())
                  .containsOnly(new Edge<>(1, 9), new Edge<>(2, 1), new Edge<>(2, 6),
                                new Edge<>(4, 5), new Edge<>(5, 3), new Edge<>(6, 6),
                                new Edge<>(6, 9), new Edge<>(7, 5), new Edge<>(8, 7),
                                new Edge<>(9, 4));
        Assertions.assertThat(testObject.getProperty(vertex)).isEqualTo(vertexProperty);
        Assertions.assertThat(testObject.getProperty(9)).isNull();
        Assertions.assertThat(testObject.getProperty(testObject.getEdge(2, 1)))
                  .isEqualTo(edgeProperty);
        Assertions.assertThat(testObject.getProperty(testObject.getEdge(5, 3))).isNull();
    }

    @Test
    public void reversedCopy_ThenNewGraphWithReversedEdges()
    {
        // given
        int vertex = 5;
        String vertexProperty = "123456";
        String edgeProperty = "zxcvb";
        Edge<Integer> edge = testObject.addEdgeBetween(1, 2);
        testObject.addEdgeBetween(3, 5);
        testObject.addEdgeBetween(4, 9);
        testObject.addEdgeBetween(5, 4);
        testObject.addEdgeBetween(5, 7);
        testObject.addEdgeBetween(6, 2);
        testObject.addEdgeBetween(6, 6);
        testObject.addEdgeBetween(7, 8);
        testObject.addEdgeBetween(9, 1);
        testObject.addEdgeBetween(9, 6);
        testObject.setProperty(vertex, vertexProperty);
        testObject.setProperty(edge, edgeProperty);
        // when
        DirectedGraph<Integer, String, String> result = testObject.reversedCopy();
        // then
        Assertions.assertThat(result.getVertices()).hasSameElementsAs(testObject.getVertices());
        Assertions.assertThat(result.getEdges())
                  .containsOnly(new Edge<>(1, 9), new Edge<>(2, 1), new Edge<>(2, 6),
                                new Edge<>(4, 5), new Edge<>(5, 3), new Edge<>(6, 6),
                                new Edge<>(6, 9), new Edge<>(7, 5), new Edge<>(8, 7),
                                new Edge<>(9, 4));
        Assertions.assertThat(result.getProperty(vertex)).isEqualTo(vertexProperty);
        Assertions.assertThat(result.getProperty(9)).isNull();
        Assertions.assertThat(result.getProperty(result.getEdge(2, 1))).isEqualTo(edgeProperty);
        Assertions.assertThat(result.getProperty(result.getEdge(5, 3))).isNull();
    }
}
