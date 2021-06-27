package algolib.geometry._2d;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.tuples.Pair;

// Tests: Algorithm for pair of closest points
public class ClosestPointsTest
{
    @Test
    public void findClosestPoints_WhenOnePoint_ThenThisPoint()
    {
        // when
        Pair<Point2D, Point2D> result = ClosestPoints.find(List.of(Point2D.of(2, 2)));
        // then
        Assertions.assertThat(result).isEqualTo(Pair.of(Point2D.of(2, 2), Point2D.of(2, 2)));
    }

    @Test
    public void findClosestPoints_WhenTwoPoints_ThenThesePoints()
    {
        // when
        Pair<Point2D, Point2D> result =
                ClosestPoints.find(List.of(Point2D.of(2, 2), Point2D.of(4, 4)));
        // then
        Assertions.assertThat(result).isEqualTo(Pair.of(Point2D.of(2, 2), Point2D.of(4, 4)));
    }

    @Test
    public void findClosestPoints_WhenThreePoints_ThenPairOfClosestPoints()
    {
        // when
        Pair<Point2D, Point2D> result =
                ClosestPoints.find(List.of(Point2D.of(3, 2), Point2D.of(1, 1), Point2D.of(7, 0)));
        // then
        Assertions.assertThat(result).isEqualTo(Pair.of(Point2D.of(1, 1), Point2D.of(3, 2)));
    }

    @Test
    public void findClosestPoints_WhenMultiplePoints_ThenPairOfClosestPoints()
    {
        // when
        Pair<Point2D, Point2D> result = ClosestPoints.find(
                List.of(Point2D.of(1, 1), Point2D.of(-2, 2), Point2D.of(-4, 4), Point2D.of(3, -3),
                        Point2D.of(0, -5), Point2D.of(1, 0), Point2D.of(-7, 2), Point2D.of(4, 5)));
        // then
        Assertions.assertThat(result).isEqualTo(Pair.of(Point2D.of(1, 1), Point2D.of(1, 0)));
    }
}
