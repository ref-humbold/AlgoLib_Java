package com.github.refhumbold.algolib.geometry.dim3;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Point3DTest
{
    private static final Offset<Double> OFFSET = Offset.offset(1e-12);

    @Test
    public void coordinates_ThenArray()
    {
        // when
        double[] result = Point3D.of(150.123456789, -3700.987654321, 0.55555555).coordinates();

        // then
        Assertions.assertThat(result)
                  .isEqualTo(new double[]{150.123456789, -3700.987654321, 0.55555555});
    }

    @ParameterizedTest
    @MethodSource("paramsFor_radius")
    public void radius_ThenDistanceFromZeroPoint(Point3D point, double expected)
    {
        // when
        double result = point.radius();

        // then
        Assertions.assertThat(result).isCloseTo(expected, OFFSET);
    }

    @Test
    public void toString_ThenStringRepresentation()
    {
        // when
        String result = Point3D.of(150.123456789, -3700.987654321, 0.55555555).toString();

        // then
        Assertions.assertThat(result).isEqualTo("(150.123456789, -3700.987654321, 0.55555555)");
    }

    private static Stream<Arguments> paramsFor_radius()
    {
        return Stream.of(Arguments.of(Point3D.ZERO, 0.0),
                Arguments.of(Point3D.of(14.0, 0.0, 0.0), 14.0),
                Arguments.of(Point3D.of(-14.0, 0.0, 0.0), 14.0),
                Arguments.of(Point3D.of(0.0, 14.0, 0.0), 14.0),
                Arguments.of(Point3D.of(0.0, -14.0, 0.0), 14.0),
                Arguments.of(Point3D.of(0.0, 0.0, 14.0), 14.0),
                Arguments.of(Point3D.of(0.0, 0.0, -14.0), 14.0),
                Arguments.of(Point3D.of(8.0, 6.0, 0.0), 10.0),
                Arguments.of(Point3D.of(8.0, -6.0, 0.0), 10.0),
                Arguments.of(Point3D.of(-8.0, 6.0, 0.0), 10.0),
                Arguments.of(Point3D.of(-8.0, -6.0, 0.0), 10.0),
                Arguments.of(Point3D.of(8.0, 0.0, 6.0), 10.0),
                Arguments.of(Point3D.of(8.0, 0.0, -6.0), 10.0),
                Arguments.of(Point3D.of(-8.0, 0.0, 6.0), 10.0),
                Arguments.of(Point3D.of(-8.0, 0.0, -6.0), 10.0),
                Arguments.of(Point3D.of(0.0, 8.0, 6.0), 10.0),
                Arguments.of(Point3D.of(0.0, 8.0, -6.0), 10.0),
                Arguments.of(Point3D.of(0.0, -8.0, 6.0), 10.0),
                Arguments.of(Point3D.of(0.0, -8.0, -6.0), 10.0),
                Arguments.of(Point3D.of(18.0, 6.0, 13.0), 23.0),
                Arguments.of(Point3D.of(18.0, 6.0, -13.0), 23.0),
                Arguments.of(Point3D.of(18.0, -6.0, 13.0), 23.0),
                Arguments.of(Point3D.of(18.0, -6.0, -13.0), 23.0),
                Arguments.of(Point3D.of(-18.0, 6.0, 13.0), 23.0),
                Arguments.of(Point3D.of(-18.0, 6.0, -13.0), 23.0),
                Arguments.of(Point3D.of(-18.0, -6.0, 13.0), 23.0),
                Arguments.of(Point3D.of(-18.0, -6.0, -13.0), 23.0));
    }
}
