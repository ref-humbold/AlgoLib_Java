package algolib.graphs;

import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of tree graph
public class TreeGraphTest
{
    private TreeGraph<Integer, String, String> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new TreeGraph<>(0);
        testObject.addVertex(1, new Vertex<>(0));
        testObject.addVertex(2, new Vertex<>(0));
        testObject.addVertex(3, new Vertex<>(0));
        testObject.addVertex(4, new Vertex<>(1));
        testObject.addVertex(5, new Vertex<>(1));
        testObject.addVertex(6, new Vertex<>(2));
        testObject.addVertex(7, new Vertex<>(2));
    }

    @Test
    public void getProperties_set_get_WhenSettingProperty_ThenProperty()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(2);
        Edge<Integer> edge = testObject.getEdge(6, 2);
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
    public void getVerticesCount_ThenNumberOfVertices()
    {
        // when
        int result = testObject.getVerticesCount();
        // then
        Assertions.assertThat(result).isEqualTo(8);
    }

    @Test
    public void getVertices_ThenAllVertices()
    {
        // when
        Collection<Vertex<Integer>> result = testObject.getVertices();
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Vertex<>(0), new Vertex<>(1), new Vertex<>(2), new Vertex<>(3),
                                new Vertex<>(4), new Vertex<>(5), new Vertex<>(6), new Vertex<>(7));
    }

    @Test
    public void getEdgesCount_ThenNumberOfEdges()
    {
        // when
        int result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(7);
    }

    @Test
    public void getEdges_ThenAllEdges()
    {
        // when
        Collection<Edge<Integer>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(new Vertex<>(1), new Vertex<>(0)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(0)),
                                new Edge<>(new Vertex<>(3), new Vertex<>(0)),
                                new Edge<>(new Vertex<>(4), new Vertex<>(1)),
                                new Edge<>(new Vertex<>(5), new Vertex<>(1)),
                                new Edge<>(new Vertex<>(6), new Vertex<>(2)),
                                new Edge<>(new Vertex<>(7), new Vertex<>(2)));
    }

    @Test
    public void getVertex_WhenExists_ThenVertex()
    {
        // given
        int vertexId = 5;
        // when
        Vertex<Integer> result = testObject.getVertex(vertexId);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.id).isEqualTo(vertexId);
    }

    @Test
    public void getEdge_WhenExists_ThenEdge()
    {
        // given
        Vertex<Integer> source = new Vertex<>(5);
        Vertex<Integer> destination = new Vertex<>(1);
        // when
        Edge<Integer> result = testObject.getEdge(source, destination);
        // then
        Assertions.assertThat(result.source).isEqualTo(source);
        Assertions.assertThat(result.destination).isEqualTo(destination);
    }

    @Test
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // when
        Collection<Vertex<Integer>> result = testObject.getNeighbours(new Vertex<>(1));
        // then
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result)
                  .containsOnly(new Vertex<>(0), new Vertex<>(4), new Vertex<>(5));
    }

    @Test
    public void getAdjacentEdges_ThenDestinationVerticesOfOutgoingEdges()
    {
        // when
        Collection<Edge<Integer>> result = testObject.getAdjacentEdges(new Vertex<>(1));
        // then
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(new Vertex<>(1), new Vertex<>(0)),
                                new Edge<>(new Vertex<>(4), new Vertex<>(1)),
                                new Edge<>(new Vertex<>(5), new Vertex<>(1)));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // when
        int result = testObject.getOutputDegree(new Vertex<>(1));
        // then
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // when
        int result = testObject.getInputDegree(new Vertex<>(1));
        // then
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    public void addVertex_WhenNewVertex_ThenCreatedEdge()
    {
        // given
        int newVertexId = 13;
        Vertex<Integer> neighbour = new Vertex<>(5);
        String vertexProperty = "qwerty";
        String edgeProperty = "asdfgh";
        // when
        Edge<Integer> result =
                testObject.addVertex(newVertexId, neighbour, vertexProperty, edgeProperty);
        // then
        Assertions.assertThat(result.source).isEqualTo(new Vertex<>(newVertexId));
        Assertions.assertThat(result.destination).isEqualTo(neighbour);
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(9);
        Assertions.assertThat(testObject.getNeighbours(result.source)).containsOnly(neighbour);
        Assertions.assertThat(testObject.getProperties().get(result.source))
                  .isEqualTo(vertexProperty);
        Assertions.assertThat(testObject.getProperties().get(result)).isEqualTo(edgeProperty);
    }

    @Test
    public void addVertex_WhenExistingVertex_ThenNull()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(6);
        String property = "qwerty";

        testObject.getProperties().set(vertex, property);
        // when
        Edge<Integer> result = testObject.addVertex(vertex, new Vertex<>(2), "abcdefg", "xyz");
        // then
        Assertions.assertThat(result).isNull();
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(8);
        Assertions.assertThat(testObject.getProperties().get(vertex)).isEqualTo(property);
    }
}
