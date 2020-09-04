package algolib.geometry;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointsSortingTest
{
    @Test
    public void sort2DByAngle_ThenSortedAscendingByAngleInDegrees()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(-2.0, -3.0), Point2D.of(-3.0, 2.0),
                              Point2D.of(2.0, 3.0), Point2D.of(3.0, -2.0), Point2D.of(-2.0, 3.0),
                              Point2D.of(3.0, 2.0), Point2D.of(2.0, -3.0), Point2D.of(-3.0, -2.0));
        // when
        PointsSorting.sort2DByAngle(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(0.0, 0.0), Point2D.of(3.0, 2.0), Point2D.of(2.0, 3.0),
                                   Point2D.of(-2.0, 3.0), Point2D.of(-3.0, 2.0),
                                   Point2D.of(-3.0, -2.0), Point2D.of(-2.0, -3.0),
                                   Point2D.of(2.0, -3.0), Point2D.of(3.0, -2.0));
    }

    @Test
    public void sort2DByX_ThenSortedStablyAscendingByFirstCoordinate()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(-2.0, -3.0), Point2D.of(-3.0, 2.0),
                              Point2D.of(2.0, 3.0), Point2D.of(3.0, -2.0), Point2D.of(-2.0, 3.0),
                              Point2D.of(3.0, 2.0), Point2D.of(2.0, -3.0), Point2D.of(-3.0, -2.0));
        // when
        PointsSorting.sort2DByX(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(-3.0, 2.0), Point2D.of(-3.0, -2.0),
                                   Point2D.of(-2.0, -3.0), Point2D.of(-2.0, 3.0),
                                   Point2D.of(0.0, 0.0), Point2D.of(2.0, 3.0),
                                   Point2D.of(2.0, -3.0), Point2D.of(3.0, -2.0),
                                   Point2D.of(3.0, 2.0));
    }

    @Test
    public void sort2DByY_ThenSortedStablyAscendingBySecondCoordinate()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(-2.0, -3.0), Point2D.of(-3.0, 2.0),
                              Point2D.of(2.0, 3.0), Point2D.of(3.0, -2.0), Point2D.of(-2.0, 3.0),
                              Point2D.of(3.0, 2.0), Point2D.of(2.0, -3.0), Point2D.of(-3.0, -2.0));
        // when
        PointsSorting.sort2DByY(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(-2.0, -3.0), Point2D.of(2.0, -3.0),
                                   Point2D.of(3.0, -2.0), Point2D.of(-3.0, -2.0),
                                   Point2D.of(0.0, 0.0), Point2D.of(-3.0, 2.0),
                                   Point2D.of(3.0, 2.0), Point2D.of(2.0, 3.0),
                                   Point2D.of(-2.0, 3.0));
    }

    @Test
    public void sort3DByX_ThenSortedStablyAscendingByFirstCoordinate()
    {
        // given
        List<Point3D> sequence =
                Arrays.asList(Point3D.of(0.0, 0.0, 0.0), Point3D.of(2.0, 3.0, -5.0),
                              Point3D.of(-2.0, -3.0, 5.0), Point3D.of(2.0, -3.0, -5.0),
                              Point3D.of(-2.0, -3.0, -5.0), Point3D.of(3.0, 2.0, 5.0),
                              Point3D.of(-3.0, 2.0, 5.0));
        // when
        PointsSorting.sort3DByX(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point3D.of(-3.0, 2.0, 5.0), Point3D.of(-2.0, -3.0, 5.0),
                                   Point3D.of(-2.0, -3.0, -5.0), Point3D.of(0.0, 0.0, 0.0),
                                   Point3D.of(2.0, 3.0, -5.0), Point3D.of(2.0, -3.0, -5.0),
                                   Point3D.of(3.0, 2.0, 5.0));
    }

    @Test
    public void sort3DByY_ThenSortedStablyAscendingBySecondCoordinate()
    {
        // given
        List<Point3D> sequence =
                Arrays.asList(Point3D.of(0.0, 0.0, 0.0), Point3D.of(2.0, 3.0, -5.0),
                              Point3D.of(-2.0, -3.0, 5.0), Point3D.of(2.0, -3.0, -5.0),
                              Point3D.of(-2.0, -3.0, -5.0), Point3D.of(3.0, 2.0, 5.0),
                              Point3D.of(-3.0, 2.0, 5.0));
        // when
        PointsSorting.sort3DByY(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point3D.of(-2.0, -3.0, 5.0), Point3D.of(2.0, -3.0, -5.0),
                                   Point3D.of(-2.0, -3.0, -5.0), Point3D.of(0.0, 0.0, 0.0),
                                   Point3D.of(3.0, 2.0, 5.0), Point3D.of(-3.0, 2.0, 5.0),
                                   Point3D.of(2.0, 3.0, -5.0));
    }

    @Test
    public void sort3DByZ_ThenSortedStablyAscendingByThirdCoordinate()
    {
        // given
        List<Point3D> sequence =
                Arrays.asList(Point3D.of(0.0, 0.0, 0.0), Point3D.of(2.0, 3.0, -5.0),
                              Point3D.of(-2.0, -3.0, 5.0), Point3D.of(2.0, -3.0, -5.0),
                              Point3D.of(-2.0, -3.0, -5.0), Point3D.of(3.0, 2.0, 5.0),
                              Point3D.of(-3.0, 2.0, 5.0));
        // when
        PointsSorting.sort3DByZ(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point3D.of(2.0, 3.0, -5.0), Point3D.of(2.0, -3.0, -5.0),
                                   Point3D.of(-2.0, -3.0, -5.0), Point3D.of(0.0, 0.0, 0.0),
                                   Point3D.of(-2.0, -3.0, 5.0), Point3D.of(3.0, 2.0, 5.0),
                                   Point3D.of(-3.0, 2.0, 5.0));
    }
}
