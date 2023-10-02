package algolib.geometry.dim3;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Algorithms for basic geometrical operations in 3D.
public class Geometry3DTest
{
    @Test
    public void sortByX_ThenSortedStablyAscending()
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
    public void sortByY_ThenSortedStablyAscending()
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
    public void sortByZ_ThenSortedStablyAscending()
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

    @Test
    public void distance_WhenDifferentPoints_ThenDistance()
    {
        // when
        double result = Geometry3D.distance(Point3D.of(4.0, 8.0, 5.0), Point3D.of(-2.0, -1.0, 3.0));
        // then
        Assertions.assertThat(result).isEqualTo(11.0);
    }

    @Test
    public void distance_WhenSamePoint_ThenZero()
    {
        // given
        Point3D point = Point3D.of(13.5, 6.5, -4.2);
        // when
        double result = Geometry3D.distance(point, point);
        // then
        Assertions.assertThat(result).isEqualTo(0.0);
    }

    @Test
    public void translate_ThenPointTranslated()
    {
        // when
        Point3D result =
                Geometry3D.translate(Point3D.of(13.7, 6.5, -4.2), Vector3D.of(-10.4, 3.3, 1.1));
        // then
        Assertions.assertThat(result).isEqualTo(Point3D.of(3.3, 9.8, -3.1));
    }

    @Test
    public void translate_WhenZeroVector_ThenSamePoint()
    {
        // given
        Point3D point = Point3D.of(13.5, 6.5, -4.2);
        // when
        Point3D result = Geometry3D.translate(point, Vector3D.of(0.0, 0.0, 0.0));
        // then
        Assertions.assertThat(result).isEqualTo(point);
    }

    @Test
    public void reflect_ThenPointReflected()
    {
        // when
        Point3D result =
                Geometry3D.reflect(Point3D.of(13.5, 6.5, -4.2), Point3D.of(2.0, -1.0, -3.0));
        // then
        Assertions.assertThat(result).isEqualTo(Point3D.of(-9.5, -8.5, -1.8));
    }

    @Test
    public void reflect_WhenZeroPoint_ThenPointReflected()
    {
        // when
        Point3D result = Geometry3D.reflect(Point3D.of(13.5, 6.5, -4.2), Point3D.of(0.0, 0.0, 0.0));
        // then
        Assertions.assertThat(result).isEqualTo(Point3D.of(-13.5, -6.5, 4.2));
    }

    @Test
    public void reflect_WhenSamePoint_ThenSamePoint()
    {
        // given
        Point3D point = Point3D.of(13.5, 6.5, -4.2);
        // when
        Point3D result = Geometry3D.reflect(point, point);
        // then
        Assertions.assertThat(result).isEqualTo(point);
    }
}
