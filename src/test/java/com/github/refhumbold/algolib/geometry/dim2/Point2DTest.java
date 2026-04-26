package com.github.refhumbold.algolib.geometry.dim2;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Point2DTest
{
    private static final Offset<Double> OFFSET = Offset.offset(1e-12);

    @Test
    public void coordinates_ThenArray()
    {
        // when
        double[] result = Point2D.of(150.123456789, -3700.987654321).coordinates();

        // then
        Assertions.assertThat(result).isEqualTo(new double[]{150.123456789, -3700.987654321});
    }

    @ParameterizedTest
    @MethodSource("paramsFor_angle")
    public void angle_ThenCounterClockwiseAngleFromXAxis(Point2D point, double expected)
    {
        // when
        Angle result = point.angle();

        // then
        Assertions.assertThat(result).isEqualTo(Angle.fromDegrees(expected));
    }

    @ParameterizedTest
    @MethodSource("paramsFor_radius")
    public void radius_ThenDistanceFromZeroPoint(Point2D point, double expected)
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
        String result = Point2D.of(150.123456789, -3700.987654321).toString();

        // then
        Assertions.assertThat(result).isEqualTo("(150.123456789, -3700.987654321)");
    }

    private static Stream<Arguments> paramsFor_angle()
    {
        return Stream.of(Arguments.of(Point2D.ZERO, 0.0), Arguments.of(Point2D.of(7.0, 0.0), 0.0),
                Arguments.of(Point2D.of(7.0, 7.0), 45.0), Arguments.of(Point2D.of(0.0, 7.0), 90.0),
                Arguments.of(Point2D.of(-7.0, 7.0), 135.0),
                Arguments.of(Point2D.of(-7.0, 0.0), 180.0),
                Arguments.of(Point2D.of(-7.0, -7.0), 225.0),
                Arguments.of(Point2D.of(0.0, -7.0), 270.0),
                Arguments.of(Point2D.of(7.0, -7.0), 315.0));
    }

    private static Stream<Arguments> paramsFor_radius()
    {
        return Stream.of(Arguments.of(Point2D.ZERO, 0.0), Arguments.of(Point2D.of(14.0, 0.0), 14.0),
                Arguments.of(Point2D.of(-14.0, 0.0), 14.0),
                Arguments.of(Point2D.of(0.0, 14.0), 14.0),
                Arguments.of(Point2D.of(0.0, -14.0), 14.0),
                Arguments.of(Point2D.of(8.0, 6.0), 10.0), Arguments.of(Point2D.of(8.0, -6.0), 10.0),
                Arguments.of(Point2D.of(-8.0, 6.0), 10.0),
                Arguments.of(Point2D.of(-8.0, -6.0), 10.0));
    }
}
