package algolib.geometry.dim2;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import algolib.geometry.GeometryObject;

// Tests: Structure of vector in 2D.
public class Vector2DTest
{
    private static final Offset<Double> OFFSET = Offset.offset(GeometryObject.EPSILON);

    @Test
    public void between_ThenVectorFromBeginToEnd()
    {
        // when
        Vector2D result = Vector2D.between(Point2D.of(2.4, 7.8), Point2D.of(-1.5, 13.2));
        // then
        Assertions.assertThat(result).isEqualTo(Vector2D.of(-3.9, 5.4));
    }

    @Test
    public void getCoordinates_ThenArray()
    {
        // when
        double[] result = Vector2D.of(5.0, -19.0).getCoordinates();
        // then
        Assertions.assertThat(result).containsExactly(5.0, -19.0);
    }

    @Test
    public void dot_ThenScalarProduct()
    {
        // when
        double result = Vector2D.dot(Vector2D.of(1.5, -4.0), Vector2D.of(9.0, -2.5));
        // then
        Assertions.assertThat(result).isCloseTo(23.5, OFFSET);
    }

    @Test
    public void dot_WhenOrthogonal_ThenZero()
    {
        // when
        double result = Vector2D.dot(Vector2D.of(1.0, 0.0), Vector2D.of(0.0, -2.0));
        // then
        Assertions.assertThat(result).isCloseTo(0.0, OFFSET);
    }

    @Test
    public void area_ThenLengthOfCrossProduct()
    {
        // when
        double result = Vector2D.area(Vector2D.of(1.5, -4.0), Vector2D.of(9.0, -2.5));
        // then
        Assertions.assertThat(result).isCloseTo(32.25, OFFSET);
    }

    @Test
    public void area_WhenParallel_ThenZero()
    {
        // when
        double result = Vector2D.area(Vector2D.of(3.0, 3.0), Vector2D.of(-8.0, -8.0));
        // then
        Assertions.assertThat(result).isCloseTo(0.0, OFFSET);
    }

    @Test
    public void length_ThenLengthOfVector()
    {
        // when
        double result = Vector2D.of(8.0, -6.0).length();
        // then
        Assertions.assertThat(result).isCloseTo(10.0, OFFSET);
    }

    @Test
    public void negate_ThenNegateEachCoordinate()
    {
        // when
        Vector2D result = Vector2D.of(5.4, 9.0).negate();
        // then
        Assertions.assertThat(result).isEqualTo(Vector2D.of(-5.4, -9.0));
    }

    @Test
    public void add_ThenAddEachCoordinate()
    {
        // when
        Vector2D result = Vector2D.of(5.4, 9.0).add(Vector2D.of(7.9, -8.1));
        // then
        Assertions.assertThat(result).isEqualTo(Vector2D.of(13.3, 0.9));
    }

    @Test
    public void subtract_ThenSubtractEachCoordinate()
    {
        // when
        Vector2D result = Vector2D.of(5.4, 9.0).subtract(Vector2D.of(7.9, -8.1));
        // then
        Assertions.assertThat(result).isEqualTo(Vector2D.of(-2.5, 17.1));
    }

    @Test
    public void multiply_ThenMultiplyEachCoordinate()
    {
        // when
        Vector2D result = Vector2D.of(5.4, 9.0).multiply(3);
        // then
        Assertions.assertThat(result).isEqualTo(Vector2D.of(16.2, 27.0));
    }

    @Test
    public void multiply_WhenMultiplicationByZero_ThenZeroVector()
    {
        // when
        Vector2D result = Vector2D.of(5.4, 9.0).multiply(0);
        // then
        Assertions.assertThat(result).isEqualTo(Vector2D.of(0, 0));
    }

    @Test
    public void divide_ThenDivideEachCoordinate()
    {
        // when
        Vector2D result = Vector2D.of(5.4, 9.0).divide(3);
        // then
        Assertions.assertThat(result).isEqualTo(Vector2D.of(1.8, 3.0));
    }

    @Test
    public void divide_WhenDivisionByZero_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Vector2D.of(1.0, 1.0).divide(0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }
}
