// Tests: Structure of tree graph
package algolib.old.graphs;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algolib.tuples.Pair;

public class TreeGraphTest
{
    private TreeGraph testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new TreeGraph(10, Arrays.asList(Pair.of(0, 6), Pair.of(1, 2), Pair.of(2, 3),
                                                     Pair.of(3, 4), Pair.of(4, 5), Pair.of(6, 4),
                                                     Pair.of(7, 3), Pair.of(8, 3), Pair.of(9, 7)));
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void addVertex_WhenOneNeighbour()
    {
        // when
        int result = testObject.addVertex(List.of(2));
        // then
        Assertions.assertThat(result).isEqualTo(10);
        Assertions.assertThat(testObject.getNeighbours(result)).containsExactly(2);
    }

    @Test
    public void addVertex_WhenNoNeighbours()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.addVertex(List.of()));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NotConnectedException.class);
    }

    @Test
    public void addVertex_WhenManyNeighbours()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> testObject.addVertex(Arrays.asList(2, 5, 9)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(CycleException.class);
    }

    @Test
    public void addEdge()
    {
        // given
        int vertex1 = 1;
        int vertex2 = 5;
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.addEdge(vertex1, vertex2));
        // then
        Assertions.assertThat(throwable).isInstanceOf(CycleException.class);
    }
}
