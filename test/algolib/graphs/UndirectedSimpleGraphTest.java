// Tests: Structure of undirected graph
package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UndirectedSimpleGraphTest
{
    private UndirectedSimpleGraph<Integer, String, String> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new UndirectedSimpleGraph<>(
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
        Edge<Integer> edge = testObject.addEdge(0, 1);
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
        Edge<Integer> edge = testObject.addEdge(6, 7);
        // when
        String resultVertex = testObject.getProperty(4);
        String resultEdge = testObject.getProperty(edge);
        // then
        Assertions.assertThat(resultVertex).isNull();
        Assertions.assertThat(resultEdge).isNull();
    }

    @Test
    public void getVerticesCount_ThenNumberOfVertices()
    {
        // when
        long result = testObject.getVerticesCount();
        // then
        Assertions.assertThat(result).isEqualTo(10);
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
    public void addVertex_WhenNewVertex_ThenVertexAdded()
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
    public void addVertex_WhenExistingVertex_ThenNoChanges()
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
        Assertions.assertThat(result).isEqualTo(6);
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
                  .containsOnly(new Edge<>(7, 7), new Edge<>(1, 5), new Edge<>(2, 4),
                                new Edge<>(8, 0), new Edge<>(6, 3), new Edge<>(9, 3));
    }

    @Test
    public void getEdge_WhenInDirection_ThenEdge()
    {
        // given
        int source = 9;
        int destination = 5;

        testObject.addEdge(source, destination);
        // when
        Edge<Integer> result = testObject.getEdge(source, destination);
        // then
        Assertions.assertThat(result.source).isEqualTo(source);
        Assertions.assertThat(result.destination).isEqualTo(destination);
    }

    @Test
    public void getEdge_WhenReversedDirection_ThenEdge()
    {
        // given
        int source = 9;
        int destination = 5;

        testObject.addEdge(source, destination);
        // when
        Edge<Integer> result = testObject.getEdge(destination, source);
        // then
        Assertions.assertThat(result.source).isEqualTo(source);
        Assertions.assertThat(result.destination).isEqualTo(destination);
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
    public void addEdge_WhenNewEdge_ThenCreatedEdge()
    {
        // given
        String property = "asdfgh";
        // when
        Edge<Integer> result = testObject.addEdge(1, 5, property);
        testObject.addEdge(1, 1);
        // then
        Assertions.assertThat(result.source).isEqualTo(1);
        Assertions.assertThat(result.destination).isEqualTo(5);
        Assertions.assertThat(testObject.getProperty(result)).isEqualTo(property);
        Assertions.assertThat(testObject.getNeighbours(1)).containsOnly(1, 5);
        Assertions.assertThat(testObject.getNeighbours(5)).containsOnly(1);
    }

    @Test
    public void addEdge_WhenDuplicatedEdge_ThenExistingEdge()
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
    public void addEdge_WhenReversed_ThenExistingEdge()
    {
        // given
        int source = 3;
        int destination = 7;
        Edge<Integer> expected = testObject.addEdge(source, destination);
        // when
        Edge<Integer> result = testObject.addEdge(destination, source);
        // then
        Assertions.assertThat(result).isEqualTo(expected);
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
        Assertions.assertThat(result).hasSize(7);
        Assertions.assertThat(result).containsOnly(1, 2, 3, 4, 6, 7, 9);
    }

    @Test
    public void getAdjacentEdges_ThenOutgoingEdges()
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
        Assertions.assertThat(result).hasSize(7);
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(1, 1), new Edge<>(2, 1), new Edge<>(1, 3),
                                new Edge<>(1, 4), new Edge<>(6, 1), new Edge<>(1, 7),
                                new Edge<>(1, 9));
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
        Assertions.assertThat(result).isEqualTo(7);
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
        Assertions.assertThat(result).isEqualTo(7);
    }

    @Test
    public void asDirected_ThenDirectedGraph()
    {
        // given
        int vertex = 5;
        String vertexProperty = "123456";
        String edgeProperty = "zxcvb";
        Edge<Integer> edge = testObject.addEdge(1, 5);
        testObject.addEdge(7, 7);
        testObject.addEdge(2, 4);
        testObject.addEdge(8, 0);
        testObject.addEdge(6, 3);
        testObject.addEdge(3, 6);
        testObject.addEdge(9, 3);
        testObject.addEdge(8, 0);
        testObject.setProperty(vertex, vertexProperty);
        testObject.setProperty(edge, edgeProperty);
        // when
        DirectedSimpleGraph<Integer, String, String> result = testObject.asDirected();
        // then
        Assertions.assertThat(result.getVertices()).hasSameSizeAs(testObject.getVertices());
        Assertions.assertThat(result.getEdges())
                  .containsOnly(new Edge<>(0, 8), new Edge<>(1, 5), new Edge<>(2, 4),
                                new Edge<>(3, 6), new Edge<>(3, 9), new Edge<>(4, 2),
                                new Edge<>(5, 1), new Edge<>(6, 3), new Edge<>(7, 7),
                                new Edge<>(8, 0), new Edge<>(9, 3));
        Assertions.assertThat(result.getProperty(vertex)).isEqualTo(vertexProperty);
        Assertions.assertThat(result.getProperty(9)).isNull();
        Assertions.assertThat(result.getProperty(result.getEdge(1, 5))).isEqualTo(edgeProperty);
        Assertions.assertThat(result.getProperty(result.getEdge(5, 1))).isEqualTo(edgeProperty);
        Assertions.assertThat(result.getProperty(result.getEdge(8, 0))).isNull();
    }
}
