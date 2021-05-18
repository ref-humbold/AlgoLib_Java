package algolib.geometry.plain;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class Geometry2DTest
{
    @Test
    public void sortByX_ThenSortedStablyAscendingByXCoordinate()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(-2.0, -3.0), Point2D.of(-3.0, 2.0),
                              Point2D.of(2.0, 3.0), Point2D.of(3.0, -2.0), Point2D.of(-2.0, 3.0),
                              Point2D.of(3.0, 2.0), Point2D.of(2.0, -3.0), Point2D.of(-3.0, -2.0));
        // when
        Geometry2D.sortByX(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(-3.0, 2.0), Point2D.of(-3.0, -2.0),
                                   Point2D.of(-2.0, -3.0), Point2D.of(-2.0, 3.0),
                                   Point2D.of(0.0, 0.0), Point2D.of(2.0, 3.0),
                                   Point2D.of(2.0, -3.0), Point2D.of(3.0, -2.0),
                                   Point2D.of(3.0, 2.0));
    }

    @Test
    public void sortByY_ThenSortedStablyAscendingByYCoordinate()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(-2.0, -3.0), Point2D.of(-3.0, 2.0),
                              Point2D.of(2.0, 3.0), Point2D.of(3.0, -2.0), Point2D.of(-2.0, 3.0),
                              Point2D.of(3.0, 2.0), Point2D.of(2.0, -3.0), Point2D.of(-3.0, -2.0));
        // when
        Geometry2D.sortByY(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(-2.0, -3.0), Point2D.of(2.0, -3.0),
                                   Point2D.of(3.0, -2.0), Point2D.of(-3.0, -2.0),
                                   Point2D.of(0.0, 0.0), Point2D.of(-3.0, 2.0),
                                   Point2D.of(3.0, 2.0), Point2D.of(2.0, 3.0),
                                   Point2D.of(-2.0, 3.0));
    }

    @Test
    public void sortByAngle_ThenSortedAscendingByAngleInDegrees()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(-2.0, -3.0), Point2D.of(-3.0, 2.0),
                              Point2D.of(2.0, 3.0), Point2D.of(3.0, -2.0), Point2D.of(-2.0, 3.0),
                              Point2D.of(3.0, 2.0), Point2D.of(2.0, -3.0), Point2D.of(-3.0, -2.0));
        // when
        Geometry2D.sortByAngle(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(0.0, 0.0), Point2D.of(3.0, 2.0), Point2D.of(2.0, 3.0),
                                   Point2D.of(-2.0, 3.0), Point2D.of(-3.0, 2.0),
                                   Point2D.of(-3.0, -2.0), Point2D.of(-2.0, -3.0),
                                   Point2D.of(2.0, -3.0), Point2D.of(3.0, -2.0));
    }
}
