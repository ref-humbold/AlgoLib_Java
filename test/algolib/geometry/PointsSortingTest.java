package algolib.geometry;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointsSortingTest
{
    @Test
    public void sortByAngle_ThenSortedAscendingByAngleInDegrees()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(-2.0, -3.0), Point2D.of(-3.0, -2.0),
                              Point2D.of(3.0, -2.0), Point2D.of(-2.0, 3.0), Point2D.of(3.0, 2.0),
                              Point2D.of(2.0, -3.0), Point2D.of(2.0, 3.0), Point2D.of(-3.0, 2.0));
        // when
        PointsSorting.sortByAngle(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(0.0, 0.0), Point2D.of(3.0, 2.0), Point2D.of(2.0, 3.0),
                                   Point2D.of(-2.0, 3.0), Point2D.of(-3.0, 2.0),
                                   Point2D.of(-3.0, -2.0), Point2D.of(-2.0, -3.0),
                                   Point2D.of(2.0, -3.0), Point2D.of(3.0, -2.0));
    }

    @Test
    public void sortByX_ThenSortedAscendingByFirstCoordinate()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(-2.0, -3.0), Point2D.of(-3.0, -2.0),
                              Point2D.of(3.0, -2.0), Point2D.of(-2.0, 3.0), Point2D.of(3.0, 2.0),
                              Point2D.of(2.0, -3.0), Point2D.of(2.0, 3.0), Point2D.of(-3.0, 2.0));
        // when
        PointsSorting.sortByX(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(-3.0, -2.0), Point2D.of(-3.0, 2.0),
                                   Point2D.of(-2.0, -3.0), Point2D.of(-2.0, 3.0),
                                   Point2D.of(0.0, 0.0), Point2D.of(2.0, -3.0),
                                   Point2D.of(2.0, 3.0), Point2D.of(3.0, -2.0),
                                   Point2D.of(3.0, 2.0));
    }

    @Test
    public void sortByY_ThenSortedAscendingBySecondCoordinate()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(-2.0, -3.0), Point2D.of(-3.0, -2.0),
                              Point2D.of(3.0, -2.0), Point2D.of(-2.0, 3.0), Point2D.of(3.0, 2.0),
                              Point2D.of(2.0, -3.0), Point2D.of(2.0, 3.0), Point2D.of(-3.0, 2.0));
        // when
        PointsSorting.sortByY(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(-2.0, -3.0), Point2D.of(2.0, -3.0),
                                   Point2D.of(-3.0, -2.0), Point2D.of(3.0, -2.0),
                                   Point2D.of(0.0, 0.0), Point2D.of(-3.0, 2.0),
                                   Point2D.of(3.0, 2.0), Point2D.of(-2.0, 3.0),
                                   Point2D.of(2.0, 3.0));
    }
}
