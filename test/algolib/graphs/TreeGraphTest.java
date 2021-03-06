package algolib.graphs;

import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
    public void setProperty_getProperty_WhenSettingProperty_ThenProperty()
    {
        // given
        String vertexProperty = "x";
        String edgeProperty = "y";
        int vertex = 2;
        Edge<Integer> edge = testObject.getEdge(6, 2);
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
        Collection<Integer> result = testObject.getVertices();
        // then
        Assertions.assertThat(result).containsOnly(0, 1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    public void addVertex_WhenNewVertex_ThenCreatedEdge()
    {
        // given
        int newVertex = 13;
        int neighbour = 5;
        String vertexProperty = "qwerty";
        String edgeProperty = "asdfgh";
        // when
        Edge<Integer> result =
                testObject.addVertex(newVertex, neighbour, vertexProperty, edgeProperty);
        // then
        Assertions.assertThat(result.source).isEqualTo(newVertex);
        Assertions.assertThat(result.destination).isEqualTo(neighbour);
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(9);
        Assertions.assertThat(testObject.getNeighbours(newVertex)).containsOnly(neighbour);
        Assertions.assertThat(testObject.getProperty(newVertex)).isEqualTo(vertexProperty);
        Assertions.assertThat(testObject.getProperty(result)).isEqualTo(edgeProperty);
    }

    @Test
    public void addVertex_WhenExistingVertex_ThenNull()
    {
        // given
        int vertex = 6;
        String property = "qwerty";

        testObject.setProperty(vertex, property);
        // when
        Edge<Integer> result = testObject.addVertex(vertex, 2, "abcdefg", "xyz");
        // then
        Assertions.assertThat(result).isNull();
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(8);
        Assertions.assertThat(testObject.getProperty(vertex)).isEqualTo(property);
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
                  .containsOnly(new Edge<>(1, 0), new Edge<>(2, 0), new Edge<>(3, 0),
                                new Edge<>(4, 1), new Edge<>(5, 1), new Edge<>(6, 2),
                                new Edge<>(7, 2));
    }

    @Test
    public void getEdge_WhenExists_ThenEdge()
    {
        // given
        int source = 5;
        int destination = 1;
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
        int result = testObject.getOutputDegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // when
        int result = testObject.getInputDegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(3);
    }
}
