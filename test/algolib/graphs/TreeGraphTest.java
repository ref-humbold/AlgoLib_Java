// Tests: Structure of tree graph
package algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TreeGraphTest
{
    private TreeGraph<Void, Void> testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new TreeGraph<>(null);

        List<Vertex<Void>> vertices = new ArrayList<>(testObject.getVertices());

        vertices.add(testObject.addVertex(null, null, vertices.get(0)));
        vertices.add(testObject.addVertex(null, null, vertices.get(0)));
        vertices.add(testObject.addVertex(null, null, vertices.get(0)));
        vertices.add(testObject.addVertex(null, null, vertices.get(1)));
        vertices.add(testObject.addVertex(null, null, vertices.get(1)));
        vertices.add(testObject.addVertex(null, null, vertices.get(2)));
        vertices.add(testObject.addVertex(null, null, vertices.get(2)));
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
        Assertions.assertThat(result).isEqualTo(8L);
    }

    @Test
    public void getVertices_ThenAllVertices()
    {
        // when
        List<Vertex<Void>> result = testObject.getVertices();
        // then
        Assertions.assertThat(result).isSorted();
        Assertions.assertThat(result)
                  .containsExactly(new Vertex<>(0, null), new Vertex<>(1, null),
                                   new Vertex<>(2, null), new Vertex<>(3, null),
                                   new Vertex<>(4, null), new Vertex<>(5, null),
                                   new Vertex<>(6, null), new Vertex<>(7, null));
    }

    @Test
    public void addVertex_ThenNewVertex()
    {
        // given
        List<Vertex<Void>> vertices = testObject.getVertices();
        // when
        Vertex<Void> result = testObject.addVertex(null, null, vertices.get(0));
        // then
        Assertions.assertThat(result.index).isEqualTo(8);
        Assertions.assertThat(testObject.getVerticesCount()).isEqualTo(9);
        Assertions.assertThat(testObject.getNeighbours(result)).containsExactly(vertices.get(0));
    }

    @Test
    public void getEdgesCount_ThenNumberOfEdges()
    {
        // when
        long result = testObject.getEdgesCount();
        // then
        Assertions.assertThat(result).isEqualTo(7L);
    }

    @Test
    public void getEdges_ThenAllEdges()
    {
        // given
        List<Vertex<Void>> vertices = testObject.getVertices();
        // when
        List<Edge<Void, Void>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result).isSorted();
        Assertions.assertThat(result)
                  .containsExactly(new Edge<>(vertices.get(0), vertices.get(1), null),
                                   new Edge<>(vertices.get(0), vertices.get(2), null),
                                   new Edge<>(vertices.get(0), vertices.get(3), null),
                                   new Edge<>(vertices.get(1), vertices.get(4), null),
                                   new Edge<>(vertices.get(1), vertices.get(5), null),
                                   new Edge<>(vertices.get(2), vertices.get(6), null),
                                   new Edge<>(vertices.get(2), vertices.get(7), null));
    }

    @Test
    public void getNeighbours_ThenDestinationVerticesOfOutgoingEdges()
    {
        // given
        List<Vertex<Void>> vertices = testObject.getVertices();
        // when
        Collection<Vertex<Void>> result = testObject.getNeighbours(vertices.get(1));
        // then
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result)
                  .containsOnly(vertices.get(0), vertices.get(4), vertices.get(5));
    }

    @Test
    public void getAdjacentEdges_ThenDestinationVerticesOfOutgoingEdges()
    {
        // given
        List<Vertex<Void>> vertices = testObject.getVertices();
        // when
        Collection<Edge<Void, Void>> result = testObject.getAdjacentEdges(vertices.get(1));
        // then
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result)
                  .containsOnly(new Edge<>(vertices.get(0), vertices.get(1), null),
                                new Edge<>(vertices.get(1), vertices.get(4), null),
                                new Edge<>(vertices.get(1), vertices.get(5), null));
    }

    @Test
    public void getOutputDegree_ThenNumberOfOutgoingEdges()
    {
        // given
        List<Vertex<Void>> vertices = testObject.getVertices();
        // when
        long result = testObject.getOutputDegree(vertices.get(1));
        // then
        Assertions.assertThat(result).isEqualTo(3L);
    }

    @Test
    public void getInputDegree_ThenNumberOfIncomingEdges()
    {
        // given
        List<Vertex<Void>> vertices = testObject.getVertices();
        // when
        long result = testObject.getInputDegree(vertices.get(1));
        // then
        Assertions.assertThat(result).isEqualTo(3L);
    }
}
