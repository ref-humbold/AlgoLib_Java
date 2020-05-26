// Tests: Structure of directed simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DirectedGraphTest
{
    private DirectedGraph<Void, Void> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new DirectedGraph<>();

        for(int i = 0; i < 10; ++i)
            testObject.addVertex(null);
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
        long result = testObject.getVerticesCount();
        // then
        Assertions.assertThat(result).isEqualTo(10L);
    }

    @Test
    public void getVertices_ThenAllVertices()
    {
        // when
        Collection<Vertex<Void>> result = testObject.getVertices();
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(new Vertex<>(0, null), new Vertex<>(1, null),
                                             new Vertex<>(2, null), new Vertex<>(3, null),
                                             new Vertex<>(4, null), new Vertex<>(5, null),
                                             new Vertex<>(6, null), new Vertex<>(7, null),
                                             new Vertex<>(8, null), new Vertex<>(9, null));
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
        List<Vertex<Void>> vertices =
                testObject.getVertices().stream().sorted().collect(Collectors.toList());

        testObject.addEdge(vertices.get(7), vertices.get(7), null);
        testObject.addEdge(vertices.get(1), vertices.get(5), null);
        testObject.addEdge(vertices.get(2), vertices.get(4), null);
        testObject.addEdge(vertices.get(8), vertices.get(0), null);
        testObject.addEdge(vertices.get(6), vertices.get(3), null);
        testObject.addEdge(vertices.get(3), vertices.get(6), null);
        testObject.addEdge(vertices.get(9), vertices.get(3), null);
        testObject.addEdge(vertices.get(8), vertices.get(0), null);
        // when
        long result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(7L);
    }

    @Test
    public void getEdges_ThenAllEdges()
    {
        // given
        List<Vertex<Void>> vertices =
                testObject.getVertices().stream().sorted().collect(Collectors.toList());

        testObject.addEdge(vertices.get(7), vertices.get(7), null);
        testObject.addEdge(vertices.get(1), vertices.get(5), null);
        testObject.addEdge(vertices.get(2), vertices.get(4), null);
        testObject.addEdge(vertices.get(8), vertices.get(0), null);
        testObject.addEdge(vertices.get(6), vertices.get(3), null);
        testObject.addEdge(vertices.get(3), vertices.get(6), null);
        testObject.addEdge(vertices.get(9), vertices.get(3), null);
        testObject.addEdge(vertices.get(8), vertices.get(0), null);
        // when
        Collection<Edge<Void, Void>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(new Edge<>(vertices.get(1), vertices.get(5), null),
                                             new Edge<>(vertices.get(2), vertices.get(4), null),
                                             new Edge<>(vertices.get(3), vertices.get(6), null),
                                             new Edge<>(vertices.get(6), vertices.get(3), null),
                                             new Edge<>(vertices.get(7), vertices.get(7), null),
                                             new Edge<>(vertices.get(8), vertices.get(0), null),
                                             new Edge<>(vertices.get(9), vertices.get(3), null));
    }

    @Test
    public void addEdge_ThenNewEdge()
    {
        // given
        List<Vertex<Void>> vertices =
                testObject.getVertices().stream().sorted().collect(Collectors.toList());

        // when
        Edge<Void, Void> result = testObject.addEdge(vertices.get(1), vertices.get(5), null);
        testObject.addEdge(vertices.get(1), vertices.get(5), null);
        testObject.addEdge(vertices.get(1), vertices.get(1), null);
        // then
        Assertions.assertThat(result.source).isEqualTo(vertices.get(1));
        Assertions.assertThat(result.destination).isEqualTo(vertices.get(5));
        Assertions.assertThat(result.property).isNull();
        Assertions.assertThat(testObject.getNeighbours(vertices.get(1)))
                  .containsExactlyInAnyOrder(vertices.get(1), vertices.get(5));
        Assertions.assertThat(testObject.getNeighbours(vertices.get(5))).isEmpty();
    }

    @Test
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // given
        List<Vertex<Void>> vertices =
                testObject.getVertices().stream().sorted().collect(Collectors.toList());

        testObject.addEdge(vertices.get(1), vertices.get(1), null);
        testObject.addEdge(vertices.get(1), vertices.get(3), null);
        testObject.addEdge(vertices.get(1), vertices.get(4), null);
        testObject.addEdge(vertices.get(1), vertices.get(7), null);
        testObject.addEdge(vertices.get(1), vertices.get(9), null);
        testObject.addEdge(vertices.get(2), vertices.get(1), null);
        testObject.addEdge(vertices.get(6), vertices.get(1), null);
        // when
        Collection<Vertex<Void>> result = testObject.getNeighbours(vertices.get(1));
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(vertices.get(1), vertices.get(3), vertices.get(4),
                                             vertices.get(7), vertices.get(9));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // given
        List<Vertex<Void>> vertices =
                testObject.getVertices().stream().sorted().collect(Collectors.toList());

        testObject.addEdge(vertices.get(1), vertices.get(1), null);
        testObject.addEdge(vertices.get(1), vertices.get(3), null);
        testObject.addEdge(vertices.get(1), vertices.get(4), null);
        testObject.addEdge(vertices.get(1), vertices.get(7), null);
        testObject.addEdge(vertices.get(1), vertices.get(9), null);
        testObject.addEdge(vertices.get(2), vertices.get(1), null);
        testObject.addEdge(vertices.get(6), vertices.get(1), null);
        // when
        long result = testObject.getOutputDegree(vertices.get(1));
        // then
        Assertions.assertThat(result).isEqualTo(5L);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // given
        List<Vertex<Void>> vertices =
                testObject.getVertices().stream().sorted().collect(Collectors.toList());

        testObject.addEdge(vertices.get(1), vertices.get(1), null);
        testObject.addEdge(vertices.get(3), vertices.get(1), null);
        testObject.addEdge(vertices.get(4), vertices.get(1), null);
        testObject.addEdge(vertices.get(7), vertices.get(1), null);
        testObject.addEdge(vertices.get(9), vertices.get(1), null);
        testObject.addEdge(vertices.get(1), vertices.get(2), null);
        testObject.addEdge(vertices.get(1), vertices.get(6), null);
        // when
        long result = testObject.getInputDegree(vertices.get(1));
        // then
        Assertions.assertThat(result).isEqualTo(5L);
    }

    @Test
    public void reverse_ThenAllEdgesHaveReversedDirection()
    {
        // given
        List<Vertex<Void>> vertices =
                testObject.getVertices().stream().sorted().collect(Collectors.toList());

        testObject.addEdge(vertices.get(1), vertices.get(2), null);
        testObject.addEdge(vertices.get(3), vertices.get(5), null);
        testObject.addEdge(vertices.get(4), vertices.get(9), null);
        testObject.addEdge(vertices.get(5), vertices.get(4), null);
        testObject.addEdge(vertices.get(5), vertices.get(7), null);
        testObject.addEdge(vertices.get(6), vertices.get(2), null);
        testObject.addEdge(vertices.get(6), vertices.get(6), null);
        testObject.addEdge(vertices.get(7), vertices.get(8), null);
        testObject.addEdge(vertices.get(9), vertices.get(1), null);
        testObject.addEdge(vertices.get(9), vertices.get(6), null);
        // when
        testObject.reverse();
        // then
        Assertions.assertThat(testObject.getEdges())
                  .containsExactlyInAnyOrder(new Edge<>(vertices.get(1), vertices.get(9), null),
                                             new Edge<>(vertices.get(2), vertices.get(1), null),
                                             new Edge<>(vertices.get(2), vertices.get(6), null),
                                             new Edge<>(vertices.get(4), vertices.get(5), null),
                                             new Edge<>(vertices.get(5), vertices.get(3), null),
                                             new Edge<>(vertices.get(6), vertices.get(6), null),
                                             new Edge<>(vertices.get(6), vertices.get(9), null),
                                             new Edge<>(vertices.get(7), vertices.get(5), null),
                                             new Edge<>(vertices.get(8), vertices.get(7), null),
                                             new Edge<>(vertices.get(9), vertices.get(4), null));
    }
}
