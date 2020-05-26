// Tests: Structure of undirected simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.tuples.ComparablePair;

public class UndirectedSimpleGraphTest
{
    private UndirectedSimpleGraph testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new UndirectedSimpleGraph(10);
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void getVerticesNumber()
    {
        // when
        int result = testObject.getVerticesNumber();
        // then
        Assertions.assertThat(result).isEqualTo(10);
    }

    @Test
    public void getVertices()
    {
        // when
        Collection<Integer> result = testObject.getVertices();
        // then
        Assertions.assertThat(result).containsExactlyInAnyOrder(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void addVertex()
    {
        // given
        List<Integer> neighbours = List.of(2, 6);
        // when
        int result = testObject.addVertex(neighbours);
        // then
        Assertions.assertThat(result).isEqualTo(10);
        Assertions.assertThat(testObject.getVerticesNumber()).isEqualTo(11);
        Assertions.assertThat(testObject.getNeighbours(result)).hasSameElementsAs(neighbours);
    }

    @Test
    public void getEdgesNumber()
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
        int result = testObject.getEdgesNumber();
        // then
        Assertions.assertThat(result).isEqualTo(6);
    }

    @Test
    public void getEdges()
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
        Collection<ComparablePair<Integer, Integer>> result = testObject.getEdges();
        // then
        Assertions.assertThat(result)
                  .containsExactlyInAnyOrder(ComparablePair.of(0, 8), ComparablePair.of(1, 5),
                                             ComparablePair.of(2, 4), ComparablePair.of(3, 6),
                                             ComparablePair.of(3, 9), ComparablePair.of(7, 7));
    }

    @Test
    public void addEdge()
    {
        // when
        testObject.addEdge(1, 5);
        testObject.addEdge(1, 5);
        testObject.addEdge(5, 1);
        testObject.addEdge(1, 1);
        // then
        Assertions.assertThat(testObject.getEdgesNumber()).isEqualTo(2);
        Assertions.assertThat(testObject.getNeighbours(1)).containsExactlyInAnyOrder(1, 5);
        Assertions.assertThat(testObject.getNeighbours(5)).containsExactly(1);
    }

    @Test
    public void getNeighbours()
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
        Assertions.assertThat(result).containsExactlyInAnyOrder(1, 2, 3, 4, 6, 7, 9);
    }

    @Test
    public void getOutdegree()
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
        int result = testObject.getOutdegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(7);
    }

    @Test
    public void getIndegree()
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
        int result = testObject.getIndegree(1);
        // then
        Assertions.assertThat(result).isEqualTo(7);
    }

    @Test
    public void asDirected()
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
        DirectedSimpleGraph result = testObject.asDirected();
        // then
        Assertions.assertThat(result.getVertices()).hasSameElementsAs(testObject.getVertices());
        Assertions.assertThat(result.getEdges())
                  .containsExactlyInAnyOrder(ComparablePair.of(0, 8), ComparablePair.of(1, 5),
                                             ComparablePair.of(2, 4), ComparablePair.of(3, 6),
                                             ComparablePair.of(3, 9), ComparablePair.of(4, 2),
                                             ComparablePair.of(5, 1), ComparablePair.of(6, 3),
                                             ComparablePair.of(7, 7), ComparablePair.of(8, 0),
                                             ComparablePair.of(9, 3));
    }
}
