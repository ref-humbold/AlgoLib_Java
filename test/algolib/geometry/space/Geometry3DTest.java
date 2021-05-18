package algolib.geometry.space;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class Geometry3DTest
{
    @Test
    public void sortByX_ThenSortedStablyAscendingByXCoordinate()
    {
        // given
        List<Point3D> sequence =
                Arrays.asList(Point3D.of(0.0, 0.0, 0.0), Point3D.of(2.0, 3.0, -5.0),
                              Point3D.of(-2.0, -3.0, 5.0), Point3D.of(2.0, -3.0, -5.0),
                              Point3D.of(-2.0, -3.0, -5.0), Point3D.of(3.0, 2.0, 5.0),
                              Point3D.of(-3.0, 2.0, 5.0));
        // when
        Geometry3D.sortByX(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point3D.of(-3.0, 2.0, 5.0), Point3D.of(-2.0, -3.0, 5.0),
                                   Point3D.of(-2.0, -3.0, -5.0), Point3D.of(0.0, 0.0, 0.0),
                                   Point3D.of(2.0, 3.0, -5.0), Point3D.of(2.0, -3.0, -5.0),
                                   Point3D.of(3.0, 2.0, 5.0));
    }

    @Test
    public void sort3DByY_ThenSortedStablyAscendingByYCoordinate()
    {
        // given
        List<Point3D> sequence =
                Arrays.asList(Point3D.of(0.0, 0.0, 0.0), Point3D.of(2.0, 3.0, -5.0),
                              Point3D.of(-2.0, -3.0, 5.0), Point3D.of(2.0, -3.0, -5.0),
                              Point3D.of(-2.0, -3.0, -5.0), Point3D.of(3.0, 2.0, 5.0),
                              Point3D.of(-3.0, 2.0, 5.0));
        // when
        Geometry3D.sortByY(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point3D.of(-2.0, -3.0, 5.0), Point3D.of(2.0, -3.0, -5.0),
                                   Point3D.of(-2.0, -3.0, -5.0), Point3D.of(0.0, 0.0, 0.0),
                                   Point3D.of(3.0, 2.0, 5.0), Point3D.of(-3.0, 2.0, 5.0),
                                   Point3D.of(2.0, 3.0, -5.0));
    }

    @Test
    public void sort3DByZ_ThenSortedStablyAscendingByZCoordinate()
    {
        // given
        List<Point3D> sequence =
                Arrays.asList(Point3D.of(0.0, 0.0, 0.0), Point3D.of(2.0, 3.0, -5.0),
                              Point3D.of(-2.0, -3.0, 5.0), Point3D.of(2.0, -3.0, -5.0),
                              Point3D.of(-2.0, -3.0, -5.0), Point3D.of(3.0, 2.0, 5.0),
                              Point3D.of(-3.0, 2.0, 5.0));
        // when
        Geometry3D.sortByZ(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point3D.of(2.0, 3.0, -5.0), Point3D.of(2.0, -3.0, -5.0),
                                   Point3D.of(-2.0, -3.0, -5.0), Point3D.of(0.0, 0.0, 0.0),
                                   Point3D.of(-2.0, -3.0, 5.0), Point3D.of(3.0, 2.0, 5.0),
                                   Point3D.of(-3.0, 2.0, 5.0));
    }
}
