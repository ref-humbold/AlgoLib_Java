package algolib.geometry.dim2;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Algorithm for convex hull (monotone chain)
public class ConvexHullTest
{
    @Test
    public void findConvexHull_WhenOnePoint_ThenEmpty()
    {
        // when
        List<Point2D> result = ConvexHull.findConvexHull(List.of(Point2D.of(3.0, 2.0)));
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findConvexHull_WhenTwoPoints_ThenEmpty()
    {
        // when
        List<Point2D> result =
                ConvexHull.findConvexHull(List.of(Point2D.of(2.0, 3.0), Point2D.of(3.0, 2.0)));
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findConvexHull_WhenThreePoints_ThenThesePointsInHull()
    {
        // given
        List<Point2D> points =
                List.of(Point2D.of(1.0, -1.0), Point2D.of(5.0, 1.0), Point2D.of(3.0, 4.0));
        // when
        List<Point2D> result = ConvexHull.findConvexHull(points);
        // then
        Assertions.assertThat(result).isEqualTo(points);
    }

    @Test
    public void findConvexHull_ThenPointsInHull()
    {
        // when
        List<Point2D> result = ConvexHull.findConvexHull(
                List.of(Point2D.of(1, -3), Point2D.of(-4, 6), Point2D.of(-5, -7),
                        Point2D.of(-8, -7), Point2D.of(-3, -4), Point2D.of(5, 9),
                        Point2D.of(-1, -8), Point2D.of(-5, 10), Point2D.of(8, 0), Point2D.of(3, -6),
                        Point2D.of(-2, 1), Point2D.of(-2, 8), Point2D.of(10, 2), Point2D.of(6, 3),
                        Point2D.of(-7, 7), Point2D.of(6, -4)));
        // then
        Assertions.assertThat(result)
                  .containsExactly(Point2D.of(-8, -7), Point2D.of(-1, -8), Point2D.of(3, -6),
                                   Point2D.of(6, -4), Point2D.of(10, 2), Point2D.of(5, 9),
                                   Point2D.of(-5, 10), Point2D.of(-7, 7));
    }

    @Test
    public void findConvexHull_WhenMultiplePointsAreCollinear_ThenInnerPointsOmitted()
    {
        // when
        List<Point2D> result = ConvexHull.findConvexHull(
                List.of(Point2D.of(-1, -3), Point2D.of(-3, -3), Point2D.of(-3, -1),
                        Point2D.of(2, -3), Point2D.of(-3, 5), Point2D.of(0, -3), Point2D.of(7, -3),
                        Point2D.of(-3, -2)));
        // then
        Assertions.assertThat(result)
                  .containsExactly(Point2D.of(-3, -3), Point2D.of(7, -3), Point2D.of(-3, 5));
    }
}
