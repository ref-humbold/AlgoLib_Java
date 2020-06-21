package algolib.graphs;

import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultipartiteGraphTest
{
    MultipartiteGraph<Integer, String, String> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new MultipartiteGraph<>(5, List.of(List.of(0, 1, 2), List.of(3, 4),
                                                        List.of(5, 6, 7, 8), List.of(),
                                                        List.of(9)));
        testObject.addEdge(0, 3);
        testObject.addEdge(1, 5);
        testObject.addEdge(2, 9);
        testObject.addEdge(4, 6);
        testObject.addEdge(7, 9);
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
        Edge<Integer> edge = testObject.getEdge(0, 3);
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
    public void getVerticesFromGroup_WhenValidGroup_ThenVertices()
    {
        // when
        Collection<Integer> result = testObject.getVerticesFromGroup(2);
        // then
        Assertions.assertThat(result).containsOnly(5, 6, 7, 8);
    }

    @Test
    public void getVerticesFromGroup_WhenInvalidGroup_ThenIndexOutOfBoundsException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.getVerticesFromGroup(14));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void addVertex_WhenNewVertex_ThenTrue()
    {
        // given
        int newVertex = 13;
        String property = "qwerty";
        // when
        boolean result = testObject.addVertex(3, newVertex, property);
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
        boolean result = testObject.addVertex(3, vertex, "xyz");
        // then
        Assertions.assertThat(result).isFalse();
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(10);
        Assertions.assertThat(testObject.getProperty(vertex)).isEqualTo(property);
    }

    @Test
    public void addVertex_WhenInvalidGroup_ThenIndexOutOfBoundsException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.addVertex(-3, 19));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void getEdgesCount_ThenNumberOfEdges()
    {
        // when
        int result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(5);
    }

    @Test
    public void getEdges_ThenAllEdges()
    {
        // when
        Collection<Edge<Integer>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(0, 3), new Edge<>(1, 5), new Edge<>(2, 9),
                                new Edge<>(4, 6), new Edge<>(7, 9));
    }

    @Test
    public void getEdge_WhenExists_ThenEdge()
    {
        // given
        int source = 2;
        int destination = 9;
        // when
        Edge<Integer> result = testObject.getEdge(source, destination);
        // then
        Assertions.assertThat(result.source).isEqualTo(source);
        Assertions.assertThat(result.destination).isEqualTo(destination);
    }

    @Test
    public void addEdge_WhenNewEdge_ThenCreatedEdge()
    {
        // given
        int vertex1 = 2;
        int vertex2 = 8;
        String property = "asdfgh";
        // when
        Edge<Integer> result = testObject.addEdge(vertex1, vertex2, property);
        // then
        Assertions.assertThat(result.source).isEqualTo(vertex1);
        Assertions.assertThat(result.destination).isEqualTo(vertex2);
        Assertions.assertThat(testObject.getProperty(result)).isEqualTo(property);
        Assertions.assertThat(testObject.getNeighbours(vertex2)).containsOnly(vertex1);
    }

    @Test
    public void addEdge_WhenDuplicatedEdge_ThenExistingEdge()
    {
        // given
        int source = 8;
        int destination = 3;
        Edge<Integer> expected = testObject.addEdge(source, destination);
        // when
        Edge<Integer> result = testObject.addEdge(source, destination);
        // then
        Assertions.assertThat(result).isSameAs(expected);
    }

    @Test
    public void addEdge_WhenSameGroup_ThenGraphPartitionException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.addEdge(5, 8));
        // then
        Assertions.assertThat(throwable).isInstanceOf(GraphPartitionException.class);
    }

    @Test
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // when
        Collection<Integer> result = testObject.getNeighbours(9);
        // then
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result).containsOnly(2, 7);
    }

    @Test
    public void getAdjacentEdges_ThenDestinationVerticesOfOutgoingEdges()
    {
        // when
        Collection<Edge<Integer>> result = testObject.getAdjacentEdges(9);
        // then
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result).containsOnly(new Edge<>(2, 9), new Edge<>(7, 9));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // when
        int result = testObject.getOutputDegree(9);
        // then
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // when
        int result = testObject.getInputDegree(9);
        // then
        Assertions.assertThat(result).isEqualTo(2);
    }
}
