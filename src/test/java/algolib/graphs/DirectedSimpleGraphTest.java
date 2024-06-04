package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of directed simple graph.
public class DirectedSimpleGraphTest
{
    private DirectedSimpleGraph<Integer, String, String> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));
    }

    @Test
    public void getProperties_set_get_WhenSettingProperty_ThenProperty()
    {
        // given
        Vertex<Integer> vertex = testObject.getVertex(2);
        Edge<Integer> edge =
                testObject.addEdgeBetween(testObject.getVertex(0), testObject.getVertex(1));
        String vertexProperty = "x";
        String edgeProperty = "y";
        // when
        testObject.getProperties().set(vertex, vertexProperty);
        testObject.getProperties().set(edge, edgeProperty);

        String resultVertex = testObject.getProperties().get(vertex);
        String resultEdge = testObject.getProperties().get(edge);
        // then
        Assertions.assertThat(resultVertex).isEqualTo(vertexProperty);
        Assertions.assertThat(resultEdge).isEqualTo(edgeProperty);
    }

    @Test
    public void getProperties_get_WhenNoProperty_ThenNull()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(4);
        Edge<Integer> edge = testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(7));
        // when
        String resultVertex = testObject.getProperties().get(vertex);
        String resultEdge = testObject.getProperties().get(edge);
        // then
        Assertions.assertThat(resultVertex).isNull();
        Assertions.assertThat(resultEdge).isNull();
    }

    @Test
    public void getProperties_get_WhenNotExisting_ThenIllegalArgumentException()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(14);
        Edge<Integer> edge1 = new Edge<>(new Vertex<>(2), new Vertex<>(8));
        Edge<Integer> edge2 = new Edge<>(new Vertex<>(0), new Vertex<>(-1));
        // then
        Assertions.assertThatThrownBy(() -> testObject.getProperties().get(vertex))
                  .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> testObject.getProperties().get(edge1))
                  .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> testObject.getProperties().get(edge2))
                  .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getVerticesCount_ThenNumberOfVertices()
    {
        // when
        int result = testObject.getVerticesCount();
        // then
        Assertions.assertThat(result).isEqualTo(10);
    }

    @Test
    public void getEdgesCount_ThenNumberOfEdges()
    {
        // given
        testObject.addEdgeBetween(new Vertex<>(7), new Vertex<>(7));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(5));
        testObject.addEdgeBetween(new Vertex<>(2), new Vertex<>(4));
        testObject.addEdgeBetween(new Vertex<>(8), new Vertex<>(0));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(3));
        testObject.addEdgeBetween(new Vertex<>(3), new Vertex<>(6));
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(3));
        // when
        int result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(7);
    }

    @Test
    public void getVertices_ThenAllVertices()
    {
        // when
        Collection<Vertex<Integer>> result = testObject.getVertices();
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Vertex<>(0), new Vertex<>(1), new Vertex<>(2), new Vertex<>(3),
                                new Vertex<>(4), new Vertex<>(5), new Vertex<>(6), new Vertex<>(7),
                                new Vertex<>(8), new Vertex<>(9));
    }

    @Test
    public void getEdges_ThenAllEdges()
    {
        // given
        testObject.addEdgeBetween(new Vertex<>(7), new Vertex<>(7));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(5));
        testObject.addEdgeBetween(new Vertex<>(2), new Vertex<>(4));
        testObject.addEdgeBetween(new Vertex<>(8), new Vertex<>(0));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(3));
        testObject.addEdgeBetween(new Vertex<>(3), new Vertex<>(6));
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(3));
        // when
        Collection<Edge<Integer>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(new Vertex<>(1), new Vertex<>(5)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(4)),
                                new Edge<>(new Vertex<>(3), new Vertex<>(6)),
                                new Edge<>(new Vertex<>(6), new Vertex<>(3)),
                                new Edge<>(new Vertex<>(7), new Vertex<>(7)),
                                new Edge<>(new Vertex<>(8), new Vertex<>(0)),
                                new Edge<>(new Vertex<>(9), new Vertex<>(3)));
    }

    @Test
    public void getVertex_WhenExists_ThenVertex()
    {
        // given
        int vertexId = 4;
        // when
        Vertex<Integer> result = testObject.getVertex(vertexId);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.id).isEqualTo(vertexId);
    }

    @Test
    public void getVertex_WhenNotExists_ThenNull()
    {
        // when
        Vertex<Integer> result = testObject.getVertex(16);
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void getEdge_WhenInDirection_ThenEdge()
    {
        // given
        Vertex<Integer> source = new Vertex<>(9);
        Vertex<Integer> destination = new Vertex<>(5);

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
        Vertex<Integer> source = new Vertex<>(9);
        Vertex<Integer> destination = new Vertex<>(5);

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
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // given
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(3));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(4));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(7));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(9));
        testObject.addEdgeBetween(new Vertex<>(2), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(1));
        // when
        Collection<Vertex<Integer>> result = testObject.getNeighbours(new Vertex<>(1));
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(new Vertex<>(1), new Vertex<>(3), new Vertex<>(4),
                                             new Vertex<>(7), new Vertex<>(9));
    }

    @Test
    public void getAdjacentEdges_ThenOutgoingEdges()
    {
        // given
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(3));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(4));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(7));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(9));
        testObject.addEdgeBetween(new Vertex<>(2), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(1));
        // when
        Collection<Edge<Integer>> result = testObject.getAdjacentEdges(new Vertex<>(1));
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(new Edge<>(new Vertex<>(1), new Vertex<>(1)),
                                             new Edge<>(new Vertex<>(1), new Vertex<>(3)),
                                             new Edge<>(new Vertex<>(1), new Vertex<>(4)),
                                             new Edge<>(new Vertex<>(1), new Vertex<>(7)),
                                             new Edge<>(new Vertex<>(1), new Vertex<>(9)));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // given
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(3));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(4));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(7));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(9));
        testObject.addEdgeBetween(new Vertex<>(2), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(1));
        // when
        int result = testObject.getOutputDegree(new Vertex<>(1));
        // then
        Assertions.assertThat(result).isEqualTo(5);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // given
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(3), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(4), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(7), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(2));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(6));
        // when
        int result = testObject.getInputDegree(new Vertex<>(1));
        // then
        Assertions.assertThat(result).isEqualTo(5);
    }

    @Test
    public void addVertex_WhenNewVertex_ThenCreatedVertex()
    {
        // given
        int newVertexId = 13;
        String property = "qwerty";
        // when
        Vertex<Integer> result = testObject.addVertex(newVertexId, property);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.id).isEqualTo(newVertexId);
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(11);
        Assertions.assertThat(testObject.getNeighbours(result)).isEmpty();
        Assertions.assertThat(testObject.getProperties().get(result)).isEqualTo(property);
    }

    @Test
    public void addVertex_WhenExistingVertex_ThenNull()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(6);
        String property = "qwerty";

        testObject.getProperties().set(vertex, property);
        // then
        Assertions.assertThatThrownBy(() -> testObject.addVertex(vertex, "abcdefg"))
                  .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(10);
        Assertions.assertThat(testObject.getProperties().get(vertex)).isEqualTo(property);
    }

    @Test
    public void addEdgeBetween_WhenNewEdge_ThenCreatedEdge()
    {
        // given
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(5);
        String property = "asdfgh";
        // when
        Edge<Integer> result = testObject.addEdgeBetween(vertex1, vertex2, property);
        testObject.addEdgeBetween(vertex1, vertex1);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.source).isEqualTo(vertex1);
        Assertions.assertThat(result.destination).isEqualTo(vertex2);
        Assertions.assertThat(testObject.getProperties().get(result)).isEqualTo(property);
        Assertions.assertThat(testObject.getNeighbours(vertex1)).containsOnly(vertex1, vertex2);
        Assertions.assertThat(testObject.getNeighbours(vertex2)).isEmpty();
    }

    @Test
    public void addEdgeBetween_WhenExistingEdge_ThenIllegalArgumentException()
    {
        // given
        Vertex<Integer> source = new Vertex<>(3);
        Vertex<Integer> destination = new Vertex<>(7);
        testObject.addEdgeBetween(source, destination);
        // then
        Assertions.assertThatThrownBy(() -> testObject.addEdgeBetween(source, destination))
                  .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void reverse_ThenAllEdgesHaveReversedDirection()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(5);
        String vertexProperty = "123456";
        String edgeProperty = "zxcvb";
        Edge<Integer> edge = testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(2));
        testObject.addEdgeBetween(new Vertex<>(3), new Vertex<>(5));
        testObject.addEdgeBetween(new Vertex<>(4), new Vertex<>(9));
        testObject.addEdgeBetween(new Vertex<>(5), new Vertex<>(4));
        testObject.addEdgeBetween(new Vertex<>(5), new Vertex<>(7));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(2));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(6));
        testObject.addEdgeBetween(new Vertex<>(7), new Vertex<>(8));
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(6));
        testObject.getProperties().set(vertex, vertexProperty);
        testObject.getProperties().set(edge, edgeProperty);
        // when
        testObject.reverse();
        // then
        Assertions.assertThat(testObject.getEdges())
                  .containsOnly(new Edge<>(new Vertex<>(1), new Vertex<>(9)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(1)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(6)),
                                new Edge<>(new Vertex<>(4), new Vertex<>(5)),
                                new Edge<>(new Vertex<>(5), new Vertex<>(3)),
                                new Edge<>(new Vertex<>(6), new Vertex<>(6)),
                                new Edge<>(new Vertex<>(6), new Vertex<>(9)),
                                new Edge<>(new Vertex<>(7), new Vertex<>(5)),
                                new Edge<>(new Vertex<>(8), new Vertex<>(7)),
                                new Edge<>(new Vertex<>(9), new Vertex<>(4)));
        Assertions.assertThat(testObject.getProperties().get(vertex)).isEqualTo(vertexProperty);
        Assertions.assertThat(testObject.getProperties().get(new Vertex<>(9))).isNull();
        Assertions.assertThat(testObject.getProperties().get(testObject.getEdge(2, 1)))
                  .isEqualTo(edgeProperty);
        Assertions.assertThat(testObject.getProperties().get(testObject.getEdge(5, 3))).isNull();
    }

    @Test
    public void reversedCopy_ThenNewGraphWithReversedEdges()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(5);
        String vertexProperty = "123456";
        String edgeProperty = "zxcvb";
        Edge<Integer> edge = testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(2));
        testObject.addEdgeBetween(new Vertex<>(3), new Vertex<>(5));
        testObject.addEdgeBetween(new Vertex<>(4), new Vertex<>(9));
        testObject.addEdgeBetween(new Vertex<>(5), new Vertex<>(4));
        testObject.addEdgeBetween(new Vertex<>(5), new Vertex<>(7));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(2));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(6));
        testObject.addEdgeBetween(new Vertex<>(7), new Vertex<>(8));
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(1));
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(6));
        testObject.getProperties().set(vertex, vertexProperty);
        testObject.getProperties().set(edge, edgeProperty);
        // when
        DirectedGraph<Integer, String, String> result = testObject.reversedCopy();
        // then
        Assertions.assertThat(result.getVertices()).hasSameElementsAs(testObject.getVertices());
        Assertions.assertThat(result.getEdges())
                  .containsOnly(new Edge<>(new Vertex<>(1), new Vertex<>(9)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(1)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(6)),
                                new Edge<>(new Vertex<>(4), new Vertex<>(5)),
                                new Edge<>(new Vertex<>(5), new Vertex<>(3)),
                                new Edge<>(new Vertex<>(6), new Vertex<>(6)),
                                new Edge<>(new Vertex<>(6), new Vertex<>(9)),
                                new Edge<>(new Vertex<>(7), new Vertex<>(5)),
                                new Edge<>(new Vertex<>(8), new Vertex<>(7)),
                                new Edge<>(new Vertex<>(9), new Vertex<>(4)));
        Assertions.assertThat(result.getProperties().get(vertex)).isEqualTo(vertexProperty);
        Assertions.assertThat(result.getProperties().get(new Vertex<>(9))).isNull();
        Assertions.assertThat(result.getProperties().get(result.getEdge(2, 1)))
                  .isEqualTo(edgeProperty);
        Assertions.assertThat(result.getProperties().get(result.getEdge(5, 3))).isNull();
    }
}
