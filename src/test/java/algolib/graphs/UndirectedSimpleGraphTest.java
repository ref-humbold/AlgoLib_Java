package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of undirected simple graph.
public class UndirectedSimpleGraphTest
{
    private UndirectedSimpleGraph<Integer, String, String> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new UndirectedSimpleGraph<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toList()));
    }

    @Test
    public void getProperties_set_get_WhenSettingProperty_ThenProperty()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(2);
        Edge<Integer> edge = testObject.addEdgeBetween(new Vertex<>(0), new Vertex<>(1));
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
        // when
        Throwable throwableVertex =
                Assertions.catchThrowable(() -> testObject.getProperties().get(vertex));
        Throwable throwableEdge1 =
                Assertions.catchThrowable(() -> testObject.getProperties().get(edge1));
        Throwable throwableEdge2 =
                Assertions.catchThrowable(() -> testObject.getProperties().get(edge2));
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
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(3));
        // when
        int result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(6);
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
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(3));
        // when
        Collection<Edge<Integer>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(new Vertex<>(7), new Vertex<>(7)),
                                new Edge<>(new Vertex<>(1), new Vertex<>(5)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(4)),
                                new Edge<>(new Vertex<>(8), new Vertex<>(0)),
                                new Edge<>(new Vertex<>(6), new Vertex<>(3)),
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
    public void getEdge_WhenReversedDirection_ThenEdge()
    {
        // given
        Vertex<Integer> source = new Vertex<>(9);
        Vertex<Integer> destination = new Vertex<>(5);

        testObject.addEdgeBetween(source, destination);
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
        Assertions.assertThat(result).hasSize(7);
        Assertions.assertThat(result)
                  .containsOnly(new Vertex<>(1), new Vertex<>(2), new Vertex<>(3), new Vertex<>(4),
                                new Vertex<>(6), new Vertex<>(7), new Vertex<>(9));
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
        Assertions.assertThat(result).hasSize(7);
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(new Vertex<>(1), new Vertex<>(1)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(1)),
                                new Edge<>(new Vertex<>(1), new Vertex<>(3)),
                                new Edge<>(new Vertex<>(1), new Vertex<>(4)),
                                new Edge<>(new Vertex<>(6), new Vertex<>(1)),
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
        Assertions.assertThat(result).isEqualTo(7);
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
        Assertions.assertThat(result).isEqualTo(7);
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
        // when
        Vertex<Integer> result = testObject.addVertex(vertex, "abcdefg");
        // then
        Assertions.assertThat(result).isNull();
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(10);
        Assertions.assertThat(testObject.getProperties().get(vertex)).isEqualTo(property);
    }

    @Test
    public void addEdgeBetween_WhenNewEdge_ThenCreatedEdge()
    {
        // given
        Vertex<Integer> source = new Vertex<>(1);
        Vertex<Integer> destination = new Vertex<>(5);
        String property = "asdfgh";
        // when
        Edge<Integer> result = testObject.addEdgeBetween(source, destination, property);
        testObject.addEdgeBetween(source, source);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.source).isEqualTo(source);
        Assertions.assertThat(result.destination).isEqualTo(destination);
        Assertions.assertThat(testObject.getProperties().get(result)).isEqualTo(property);
        Assertions.assertThat(testObject.getNeighbours(source)).containsOnly(source, destination);
        Assertions.assertThat(testObject.getNeighbours(destination)).containsOnly(source);
    }

    @Test
    public void addEdgeBetween_WhenExistingEdge_ThenNull()
    {
        // given
        Vertex<Integer> source = new Vertex<>(3);
        Vertex<Integer> destination = new Vertex<>(7);

        testObject.addEdgeBetween(source, destination);
        // when
        Edge<Integer> result = testObject.addEdgeBetween(source, destination);
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void addEdgeBetween_WhenReversedEdge_ThenNull()
    {
        // given
        Vertex<Integer> source = new Vertex<>(3);
        Vertex<Integer> destination = new Vertex<>(7);

        testObject.addEdgeBetween(source, destination);
        // when
        Edge<Integer> result = testObject.addEdgeBetween(destination, source);
        // then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void asDirected_ThenDirectedGraph()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(5);
        String vertexProperty = "123456";
        String edgeProperty = "zxcvb";
        Edge<Integer> edge = testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(5));
        testObject.addEdgeBetween(new Vertex<>(7), new Vertex<>(7));
        testObject.addEdgeBetween(new Vertex<>(2), new Vertex<>(4));
        testObject.addEdgeBetween(new Vertex<>(8), new Vertex<>(0));
        testObject.addEdgeBetween(new Vertex<>(6), new Vertex<>(3));
        testObject.addEdgeBetween(new Vertex<>(9), new Vertex<>(3));
        testObject.getProperties().set(vertex, vertexProperty);
        testObject.getProperties().set(edge, edgeProperty);
        // when
        DirectedSimpleGraph<Integer, String, String> result = testObject.asDirected();
        // then
        Assertions.assertThat(result.getVertices()).hasSameSizeAs(testObject.getVertices());
        Assertions.assertThat(result.getEdges())
                  .containsOnly(new Edge<>(new Vertex<>(0), new Vertex<>(8)),
                                new Edge<>(new Vertex<>(1), new Vertex<>(5)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(4)),
                                new Edge<>(new Vertex<>(3), new Vertex<>(6)),
                                new Edge<>(new Vertex<>(3), new Vertex<>(9)),
                                new Edge<>(new Vertex<>(4), new Vertex<>(2)),
                                new Edge<>(new Vertex<>(5), new Vertex<>(1)),
                                new Edge<>(new Vertex<>(6), new Vertex<>(3)),
                                new Edge<>(new Vertex<>(7), new Vertex<>(7)),
                                new Edge<>(new Vertex<>(8), new Vertex<>(0)),
                                new Edge<>(new Vertex<>(9), new Vertex<>(3)));
        Assertions.assertThat(result.getProperties().get(vertex)).isEqualTo(vertexProperty);
        Assertions.assertThat(result.getProperties().get(new Vertex<>(9))).isNull();
        Assertions.assertThat(result.getProperties().get(result.getEdge(1, 5)))
                  .isEqualTo(edgeProperty);
        Assertions.assertThat(result.getProperties().get(result.getEdge(5, 1)))
                  .isEqualTo(edgeProperty);
        Assertions.assertThat(result.getProperties().get(result.getEdge(8, 0))).isNull();
    }
}
