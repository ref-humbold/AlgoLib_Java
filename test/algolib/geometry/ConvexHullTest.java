package algolib.geometry;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConvexHullTest
{
    @Test
    public void find_WhenOnePoint_ThenEmptyConvexHull()
    {
        // when
        List<Point2D> result = ConvexHull.find(List.of(new Point2D(3.0, 2.0)));
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void find_WhenTwoPoints_ThenEmptyConvexHull()
    {
        // given
        List<Point2D> points = List.of(new Point2D(2.0, 3.0), new Point2D(3.0, 2.0));
        // when
        List<Point2D> result = ConvexHull.find(points);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void find_WhenThreePoints_ThenThesePointsInConvexHull()
    {
        // given
        List<Point2D> points =
                List.of(new Point2D(1.0, -1.0), new Point2D(5.0, 1.0), new Point2D(3.0, 4.0));
        // when
        List<Point2D> result = ConvexHull.find(points);
        // then
        Assertions.assertThat(result).isEqualTo(points);
    }

    @Test
    public void find_ThenPointsInConvexHull()
    {
        // given
        List<Point2D> points = List.of(new Point2D(1, -3), new Point2D(-4, 6), new Point2D(-5, -7),
                                       new Point2D(-8, -7), new Point2D(-3, -4), new Point2D(5, 9),
                                       new Point2D(-1, -8), new Point2D(-5, 10), new Point2D(8, 0),
                                       new Point2D(3, -6), new Point2D(-2, 1), new Point2D(-2, 8),
                                       new Point2D(10, 2), new Point2D(6, 3), new Point2D(-7, 7),
                                       new Point2D(6, -4));
        // when
        List<Point2D> result = ConvexHull.find(points);
        // then
        Assertions.assertThat(result)
                  .containsExactly(new Point2D(-8, -7), new Point2D(-1, -8), new Point2D(3, -6),
                                   new Point2D(6, -4), new Point2D(10, 2), new Point2D(5, 9),
                                   new Point2D(-5, 10), new Point2D(-7, 7));
    }

    @Test
    public void find_WhenMultiplePointsAreCollinear_ThenConvexHullOmitsInnerPoints()
    {
        // given
        List<Point2D> points =
                List.of(new Point2D(-1, -3), new Point2D(-3, -3), new Point2D(-3, -1),
                        new Point2D(2, -3), new Point2D(-3, 5), new Point2D(0, -3),
                        new Point2D(7, -3), new Point2D(-3, -2));
        // when
        List<Point2D> result = ConvexHull.find(points);
        // then
        Assertions.assertThat(result)
                  .containsExactly(new Point2D(-3, -3), new Point2D(7, -3), new Point2D(-3, 5));
    }
}
