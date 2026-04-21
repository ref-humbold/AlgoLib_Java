package com.github.refhumbold.algolib.geometry.dim2;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

// Tests: Structure of angle.
public class AngleTest
{
    private static final Offset<Double> OFFSET = Offset.offset(1e-6);

    @ParameterizedTest
    @MethodSource("paramsFor_degrees_WhenFromRadians")
    public void degrees_WhenFromRadians_ThenPositiveDegreesInRange(double radians, double degrees)
    {
        // given
        Angle angle = Angle.fromRadians(radians);

        // when
        double result = angle.getDegrees();

        // then
        Assertions.assertThat(result).isCloseTo(degrees, OFFSET);
    }

    @ParameterizedTest
    @MethodSource("paramsFor_radians_WhenFromDegrees")
    public void radians_WhenFromDegrees_ThenPositiveRadiansInRange(double degrees, double radians)
    {
        // given
        Angle angle = Angle.fromDegrees(degrees);

        // when
        double result = angle.getRadians();

        // then
        Assertions.assertThat(result).isCloseTo(radians, OFFSET);
    }

    @Test
    public void toString_ThenStringRepresentation()
    {
        // when
        String result = Angle.fromDegrees(150.123456789).toString();

        // then
        Assertions.assertThat(result).isEqualTo("Angle(150.123456789 deg)");
    }

    private static Stream<Arguments> paramsFor_degrees_WhenFromRadians()
    {
        return Stream.of(Arguments.of(0.0, 0.0), Arguments.of(Math.PI / 6, 30.0),
                Arguments.of(Math.PI / 4, 45.0), Arguments.of(Math.PI / 3, 60.0),
                Arguments.of(Math.PI / 2, 90.0), Arguments.of(Math.PI, 180.0),
                Arguments.of(2 * Math.PI, 0.0), Arguments.of(3 * Math.PI, 180.0),
                Arguments.of(-Math.PI / 6, 330.0), Arguments.of(-Math.PI / 4, 315.0),
                Arguments.of(-Math.PI / 3, 300.0), Arguments.of(-Math.PI / 2, 270.0),
                Arguments.of(-Math.PI, 180.0), Arguments.of(-2 * Math.PI, 0.0),
                Arguments.of(-3 * Math.PI, 180.0));
    }

    private static Stream<Arguments> paramsFor_radians_WhenFromDegrees()
    {
        return Stream.of(Arguments.of(0.0, 0.0), Arguments.of(30.0, Math.PI / 6),
                Arguments.of(45.0, Math.PI / 4), Arguments.of(60.0, Math.PI / 3),
                Arguments.of(90.0, Math.PI / 2), Arguments.of(180.0, Math.PI),
                Arguments.of(360.0, 0.0), Arguments.of(540.0, Math.PI),
                Arguments.of(-30.0, 11 * Math.PI / 6), Arguments.of(-45.0, 7 * Math.PI / 4),
                Arguments.of(-60.0, 5 * Math.PI / 3), Arguments.of(-90.0, 3 * Math.PI / 2),
                Arguments.of(-180.0, Math.PI), Arguments.of(-360.0, 0.0),
                Arguments.of(-540.0, Math.PI));
    }
}
