package algolib.graphs;

import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of multipartite graph.
public class MultipartiteGraphTest
{
    private MultipartiteGraph<Integer, String, String> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new MultipartiteGraph<>(5, List.of(List.of(0, 1, 2), List.of(3, 4),
                                                        List.of(5, 6, 7, 8), List.of(9)));
        testObject.addEdgeBetween(new Vertex<>(0), new Vertex<>(3));
        testObject.addEdgeBetween(new Vertex<>(1), new Vertex<>(5));
        testObject.addEdgeBetween(new Vertex<>(2), new Vertex<>(9));
        testObject.addEdgeBetween(new Vertex<>(4), new Vertex<>(6));
        testObject.addEdgeBetween(new Vertex<>(7), new Vertex<>(9));
    }

    @Test
    public void getProperties_set_get_WhenSettingProperty_ThenProperty()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(2);
        Edge<Integer> edge = testObject.getEdge(new Vertex<>(0), new Vertex<>(3));
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
        Assertions.assertThat(result).isEqualTo(10);
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
        // when
        Collection<Edge<Integer>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(new Vertex<>(0), new Vertex<>(3)),
                                new Edge<>(new Vertex<>(1), new Vertex<>(5)),
                                new Edge<>(new Vertex<>(2), new Vertex<>(9)),
                                new Edge<>(new Vertex<>(4), new Vertex<>(6)),
                                new Edge<>(new Vertex<>(7), new Vertex<>(9)));
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
        Vertex<Integer> source = new Vertex<>(2);
        Vertex<Integer> destination = new Vertex<>(9);
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
        Collection<Vertex<Integer>> result = testObject.getNeighbours(new Vertex<>(9));
        // then
        Assertions.assertThat(result).containsExactlyInAnyOrder(new Vertex<>(2), new Vertex<>(7));
    }

    @Test
    public void getAdjacentEdges_ThenDestinationVerticesOfOutgoingEdges()
    {
        // when
        Collection<Edge<Integer>> result = testObject.getAdjacentEdges(new Vertex<>(9));
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(new Edge<>(new Vertex<>(2), new Vertex<>(9)),
                                             new Edge<>(new Vertex<>(7), new Vertex<>(9)));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // when
        int result = testObject.getOutputDegree(new Vertex<>(9));
        // then
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // when
        int result = testObject.getInputDegree(new Vertex<>(9));
        // then
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    public void getVerticesFromGroup_WhenValidGroup_ThenVertices()
    {
        // when
        Collection<Vertex<Integer>> result = testObject.getVerticesFromGroup(2);
        // then
        Assertions.assertThat(result)
                  .containsOnly(new Vertex<>(5), new Vertex<>(6), new Vertex<>(7), new Vertex<>(8));
    }

    @Test
    public void getVerticesFromGroup_WhenInvalidGroup_ThenIndexOutOfBoundsException()
    {
        Assertions.assertThatThrownBy(() -> testObject.getVerticesFromGroup(14))
                  .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void addVertex_WhenNewVertex_ThenCreatedVertex()
    {
        // given
        int newVertex = 13;
        String property = "qwerty";
        // when
        Vertex<Integer> result = testObject.addVertex(4, newVertex, property);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(11);
        Assertions.assertThat(testObject.getNeighbours(result)).isEmpty();
        Assertions.assertThat(testObject.getProperties().get(result)).isEqualTo(property);
    }

    @Test
    public void addVertex_WhenExistingVertex_ThenIllegalArgumentException()
    {
        // given
        Vertex<Integer> vertex = new Vertex<>(6);
        String property = "qwerty";

        testObject.getProperties().set(vertex, property);

        Assertions.assertThatThrownBy(() -> testObject.addVertex(3, vertex, "xyz"))
                  .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(10);
        Assertions.assertThat(testObject.getProperties().get(vertex)).isEqualTo(property);
    }

    @Test
    public void addVertex_WhenInvalidGroup_ThenIndexOutOfBoundsException()
    {
        Assertions.assertThatThrownBy(() -> testObject.addVertex(-3, 19))
                  .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void addEdgeBetween_WhenNewEdge_ThenCreatedEdge()
    {
        // given
        Vertex<Integer> source = new Vertex<>(2);
        Vertex<Integer> destination = new Vertex<>(8);
        String property = "asdfgh";
        // when
        Edge<Integer> result = testObject.addEdgeBetween(source, destination, property);
        // then
        Assertions.assertThat(result.source).isEqualTo(source);
        Assertions.assertThat(result.destination).isEqualTo(destination);
        Assertions.assertThat(testObject.getProperties().get(result)).isEqualTo(property);
        Assertions.assertThat(testObject.getNeighbours(destination)).containsOnly(source);
    }

    @Test
    public void addEdgeBetween_WhenExistingEdge_ThenIllegalArgumentException()
    {
        // given
        Vertex<Integer> source = new Vertex<>(8);
        Vertex<Integer> destination = new Vertex<>(3);

        testObject.addEdgeBetween(source, destination);

        Assertions.assertThatThrownBy(() -> testObject.addEdgeBetween(source, destination))
                  .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void addEdgeBetween_WhenSameGroup_ThenGraphPartitionException()
    {
        Assertions.assertThatThrownBy(
                          () -> testObject.addEdgeBetween(new Vertex<>(5), new Vertex<>(8)))
                  .isInstanceOf(GraphPartitionException.class);
    }

    @Test
    public void addEdgeBetween_WhenInvalidVertex_ThenIllegalArgumentException()
    {
        Assertions.assertThatThrownBy(
                          () -> testObject.addEdgeBetween(new Vertex<>(15), new Vertex<>(18)))
                  .isInstanceOf(IllegalArgumentException.class);
    }
}
