package algolib.geometry.dim2;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Algorithms for basic geometrical computations in 2D
public class Geometry2DTest
{
    @Test
    public void sortByX_ThenSortedStablyAscending()
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
    public void sortByY_ThenSortedStablyAscending()
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
    public void sortByAngle_ThenSortedAscending()
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

    @Test
    public void sortByAngle_WhenEqualAngles_ThenCompareRadius()
    {
        // given
        List<Point2D> sequence =
                Arrays.asList(Point2D.of(0.0, 0.0), Point2D.of(1.0, 1.0), Point2D.of(-2.0, -2.0),
                              Point2D.of(-3.0, -3.0), Point2D.of(4.0, 4.0));
        // when
        Geometry2D.sortByAngle(sequence);
        // then
        Assertions.assertThat(sequence)
                  .containsExactly(Point2D.of(0.0, 0.0), Point2D.of(1.0, 1.0), Point2D.of(4.0, 4.0),
                                   Point2D.of(-2.0, -2.0), Point2D.of(-3.0, -3.0));
    }

    @Test
    public void distance_WhenDifferentPoints_ThenDistance()
    {
        // when
        double result = Geometry2D.distance(Point2D.of(4.0, 5.0), Point2D.of(-2.0, -3.0));
        // then
        Assertions.assertThat(result).isEqualTo(10.0);
    }

    @Test
    public void distance_WhenSamePoint_ThenZero()
    {
        // given
        Point2D point = Point2D.of(13.5, 6.5);
        // when
        double result = Geometry2D.distance(point, point);
        // then
        Assertions.assertThat(result).isEqualTo(0.0);
    }

    @Test
    public void translate_ThenPointTranslated()
    {
        // when
        Point2D result = Geometry2D.translate(Point2D.of(13.7, 6.5), Vector2D.of(-10.4, 3.3));
        // then
        Assertions.assertThat(result).isEqualTo(Point2D.of(3.3, 9.8));
    }

    @Test
    public void translate_WhenZeroVector_ThenSamePoint()
    {
        // given
        Point2D point = Point2D.of(13.5, 6.5);
        // when
        Point2D result = Geometry2D.translate(point, Vector2D.of(0.0, 0.0));
        // then
        Assertions.assertThat(result).isEqualTo(point);
    }

    @Test
    public void reflect_ThenPointReflected()
    {
        // when
        Point2D result = Geometry2D.reflect(Point2D.of(13.5, 6.5), Point2D.of(2.0, -3.0));
        // then
        Assertions.assertThat(result).isEqualTo(Point2D.of(-9.5, -12.5));
    }

    @Test

    public void reflect_WhenZeroPoint_ThenPointReflected()
    {
        // when
        Point2D result = Geometry2D.reflect(Point2D.of(13.5, 6.5), Point2D.of(0.0, 0.0));
        // then
        Assertions.assertThat(result).isEqualTo(Point2D.of(-13.5, -6.5));
    }

    @Test

    public void reflect_WhenSamePoint_ThenSamePoint()
    {
        // given
        Point2D point = Point2D.of(13.5, 6.5);
        // when
        Point2D result = Geometry2D.reflect(point, point);
        // then
        Assertions.assertThat(result).isEqualTo(point);
    }
}
