package algolib.maths;

import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of linear equation
public class EquationTest
{
    private Equation testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = Equation.of(new double[]{2, 3, 0, -2.5}, 15);
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void negate_ThenNegated()
    {
        // when
        Equation result = testObject.negate();
        // then
        Assertions.assertThat(coefficients(result))
                  .usingComparatorWithPrecision(0.0)
                  .containsExactly(-2, -3, 0, 2.5);
        Assertions.assertThat(result.getFree()).isEqualTo(-15);
    }

    @Test
    public void add_ThenAddingEquations()
    {
        // when
        Equation result = testObject.add(Equation.of(new double[]{1, -1, 4, 10}, 5));
        // then
        Assertions.assertThat(coefficients(result))
                  .usingComparatorWithPrecision(0.0)
                  .containsExactly(3, 2, 4, 7.5);
        Assertions.assertThat(result.getFree()).isEqualTo(20);
    }

    @Test
    public void subtract_ThenSubtractingEquations()
    {
        // when
        Equation result = testObject.subtract(Equation.of(new double[]{1, -1, 4, 10}, 5));
        // then
        Assertions.assertThat(coefficients(result))
                  .usingComparatorWithPrecision(0.0)
                  .containsExactly(1, 4, -4, -12.5);
        Assertions.assertThat(result.getFree()).isEqualTo(10);
    }

    @Test
    public void multiply_WhenConstantIsNonZero_ThenMultiplyingEquations()
    {
        // when
        Equation result = testObject.multiply(2);
        // then
        Assertions.assertThat(coefficients(result))
                  .usingComparatorWithPrecision(0.0)
                  .containsExactly(4, 6, 0, -5);
        Assertions.assertThat(result.getFree()).isEqualTo(30);
    }

    @Test
    public void multiply_WhenConstantIsZero_ThenArithmeticException()
    {
        // when
        Throwable result = Assertions.catchThrowable(() -> testObject.multiply(0));
        // then
        Assertions.assertThat(result).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void divide_WhenConstantIsNonZero_ThenDividingEquations()
    {
        // when
        Equation result = testObject.divide(-2);
        // then
        Assertions.assertThat(coefficients(result))
                  .usingComparatorWithPrecision(0.0)
                  .containsExactly(-1, -1.5, 0, 1.25);
        Assertions.assertThat(result.getFree()).isEqualTo(-7.5);
    }

    @Test
    public void divide_WhenConstantIsZero_ThenArithmeticException()
    {
        // when
        Throwable result = Assertions.catchThrowable(() -> testObject.divide(0));
        // then
        Assertions.assertThat(result).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void toString_ThenStringRepresentation()
    {
        // when
        String result = testObject.toString();
        // then
        Assertions.assertThat(result).isEqualTo("2 x_0 + 3 x_1 + -2.5 x_3 = 15");
    }

    @Test
    public void hasSolution_WhenSolution_ThenTrue()
    {
        // given
        double[] solution = {10, 10, -29, 14};
        // when
        boolean result = testObject.hasSolution(solution);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void hasSolution_WhenNotSolution_ThenFalse()
    {
        // given
        double[] solution = {10, 6, -17, 14};
        // when
        boolean result = testObject.hasSolution(solution);
        // then
        Assertions.assertThat(result).isFalse();
    }

    private double[] coefficients(Equation equation)
    {
        return IntStream.range(0, equation.size()).mapToDouble(equation::getCoefficient).toArray();
    }
}
