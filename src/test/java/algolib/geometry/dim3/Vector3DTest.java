package algolib.geometry.dim3;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import algolib.geometry.GeometryObject;

// Tests: Structure of vector in 3D.
public class Vector3DTest
{
    private static final Offset<Double> OFFSET = Offset.offset(GeometryObject.EPSILON);

    @Test
    public void between_ThenVectorFromBeginToEnd()
    {
        // when
        Vector3D result =
                Vector3D.between(Point3D.of(2.4, 7.8, -10.3), Point3D.of(-1.5, 13.2, 15.8));
        // then
        Assertions.assertThat(result).isEqualTo(Vector3D.of(-3.9, 5.4, 26.1));
    }

    @Test
    public void getCoordinates_ThenArray()
    {
        // when
        double[] result = Vector3D.of(5.0, -19.0, 14.2).getCoordinates();
        // then
        Assertions.assertThat(result).containsExactly(5.0, -19.0, 14.2);
    }

    @Test
    public void dot_ThenScalarProduct()
    {
        // when
        double result = Vector3D.dot(Vector3D.of(1.5, -4.0, -3.5), Vector3D.of(9.0, -2.5, 8.5));
        // then
        Assertions.assertThat(result).isCloseTo(-6.25, OFFSET);
    }

    @Test
    public void dot_WhenOrthogonal_ThenZero()
    {
        // when
        double result = Vector3D.dot(Vector3D.of(1.0, 0.0, 1.0), Vector3D.of(0.0, -2.0, 0.0));
        // then
        Assertions.assertThat(result).isCloseTo(0.0, OFFSET);
    }

    @Test
    public void cross_ThenCrossProduct()
    {
        // when
        Vector3D result = Vector3D.cross(Vector3D.of(1.5, -4.0, -3.5), Vector3D.of(9.0, -2.5, 8.5));
        // then
        Assertions.assertThat(result).isEqualTo(Vector3D.of(-42.75, -44.25, 32.25));
    }

    @Test
    public void cross_WhenParallel_ThenZero()
    {
        // when
        Vector3D result = Vector3D.cross(Vector3D.of(3.0, 3.0, 3.0), Vector3D.of(-8.0, -8.0, -8.0));
        // then
        Assertions.assertThat(result).isEqualTo(Vector3D.of(0.0, 0.0, 0.0));
    }

    @Test
    public void area_ThenLengthOfCrossProduct()
    {
        // when
        double result = Vector3D.area(Vector3D.of(1.5, -4.0, -3.5), Vector3D.of(9.0, -2.5, 8.5));
        // then
        Assertions.assertThat(result).isCloseTo(69.46716850426538, OFFSET);
    }

    @Test
    public void area_WhenParallel_ThenZero()
    {
        // when
        double result = Vector3D.area(Vector3D.of(3.0, 3.0, 3.0), Vector3D.of(-8.0, -8.0, -8.0));
        // then
        Assertions.assertThat(result).isCloseTo(0.0, OFFSET);
    }

    @Test
    public void volume_ThenScalarTripleProduct()
    {
        // when
        double result = Vector3D.volume(Vector3D.of(1.5, -4.0, -3.5), Vector3D.of(9.0, -2.5, 8.5),
                                        Vector3D.of(1.0, -1.0, 1.0));
        // then
        Assertions.assertThat(result).isCloseTo(33.75, OFFSET);
    }

    @Test
    public void volume_WhenParallel_ThenZero()
    {
        // when
        double result = Vector3D.volume(Vector3D.of(3.0, 3.0, 3.0), Vector3D.of(-8.0, -8.0, -8.0),
                                        Vector3D.of(2.0, -2.0, 2.0));
        // then
        Assertions.assertThat(result).isCloseTo(0.0, OFFSET);
    }

    @Test
    public void volume_WhenOrthogonal_ThenZero()
    {
        // when
        double result = Vector3D.volume(Vector3D.of(3.0, 3.0, 3.0), Vector3D.of(1.0, 0.0, 1.0),
                                        Vector3D.of(0.0, -2.0, 0.0));
        // then
        Assertions.assertThat(result).isCloseTo(0.0, OFFSET);
    }

    @Test
    public void length_ThenLengthOfVector()
    {
        // when
        double result = Vector3D.of(18.0, -6.0, 13.0).length();
        // then
        Assertions.assertThat(result).isCloseTo(23.0, OFFSET);
    }

    @Test
    public void negate_ThenNegateEachCoordinate()
    {
        // when
        Vector3D result = Vector3D.of(5.4, 9.0, -12.3).negate();
        // then
        Assertions.assertThat(result).isEqualTo(Vector3D.of(-5.4, -9.0, 12.3));
    }

    @Test
    public void add_ThenAddEachCoordinate()
    {
        // when
        Vector3D result = Vector3D.of(5.4, 9.0, -12.3).add(Vector3D.of(7.9, -8.1, 1.4));
        // then
        Assertions.assertThat(result).isEqualTo(Vector3D.of(13.3, 0.9, -10.9));
    }

    @Test
    public void subtract_ThenSubtractEachCoordinate()
    {
        // when
        Vector3D result = Vector3D.of(5.4, 9.0, -12.3).subtract(Vector3D.of(7.9, -8.1, 1.4));
        // then
        Assertions.assertThat(result).isEqualTo(Vector3D.of(-2.5, 17.1, -13.7));
    }

    @Test
    public void multiply_ThenMultiplyEachCoordinate()
    {
        // when
        Vector3D result = Vector3D.of(5.4, 9.0, -12.3).multiply(3);
        // then
        Assertions.assertThat(result).isEqualTo(Vector3D.of(16.2, 27.0, -36.9));
    }

    @Test
    public void multiply_WhenMultiplicationByZero_ThenZeroVector()
    {
        // when
        Vector3D result = Vector3D.of(5.4, 9.0, -12.3).multiply(0);
        // then
        Assertions.assertThat(result).isEqualTo(Vector3D.of(0.0, 0.0, 0.0));
    }

    @Test
    public void divide_ThenDivideEachCoordinate()
    {
        // when
        Vector3D result = Vector3D.of(5.4, 9.0, -12.3).divide(3);
        // then
        Assertions.assertThat(result).isEqualTo(Vector3D.of(1.8, 3.0, -4.1));
    }

    @Test
    public void divide_WhenDivisionByZero_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Vector3D.of(1.0, 1.0, 1.0).divide(0));

        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }
}
