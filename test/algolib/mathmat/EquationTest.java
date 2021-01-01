package algolib.mathmat;

import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EquationTest
{
    private Equation testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = Equation.of(new double[]{2, 3, 0, -2}, 15);
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void multiply_WhenConstantIsNonZero_ThenMultiplied()
    {
        // when
        testObject.multiply(2);
        // then
        Assertions.assertThat(allCoefficients(testObject)).containsExactly(4, 6, 0, -4);
        Assertions.assertThat(testObject.getFree()).isEqualTo(30);
    }

    @Test
    public void multiply_WhenConstantIsZero_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.multiply(0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void combine_WhenConstantIsNonZero_ThenCombined()
    {
        // when
        testObject.combine(Equation.of(new double[]{1, -1, 4, 10}, 5), -2);
        // then
        Assertions.assertThat(allCoefficients(testObject)).containsExactly(0, 5, -8, -22);
        Assertions.assertThat(testObject.getFree()).isEqualTo(5);
    }

    @Test
    public void combine_WhenNoConstant_ThenAddEquation()
    {
        // when
        testObject.combine(Equation.of(new double[]{1, -1, 4, 10}, 5));
        // then
        Assertions.assertThat(allCoefficients(testObject)).containsExactly(3, 2, 4, 8);
        Assertions.assertThat(testObject.getFree()).isEqualTo(20);
    }

    @Test
    public void combine_WhenConstantIsZero_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> testObject.combine(Equation.of(new double[]{1, -1, 10, 7}, 5), 0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    private double[] allCoefficients(Equation equation)
    {
        return IntStream.range(0, equation.size()).mapToDouble(equation::getCoefficient).toArray();
    }
}
