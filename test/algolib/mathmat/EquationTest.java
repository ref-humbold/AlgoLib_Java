package algolib.mathmat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EquationTest
{
    private Equation testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new Equation(new double[]{2, 3, 0, -2}, 15);
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
        Assertions.assertArrayEquals(new double[]{4, 6, 0, -4}, testObject.coefficients);
        Assertions.assertEquals(30, testObject.free);
    }

    @Test
    public void multiply_WhenConstantIsZero_ThenArithmeticException()
    {
        // when - then
        Assertions.assertThrows(ArithmeticException.class, () -> testObject.multiply(0));
    }

    @Test
    public void combine_WhenConstantIsNonZero_ThenCombined()
    {
        // when
        testObject.combine(new Equation(new double[]{1, -1, 4, 10}, 5), -2);
        // then
        Assertions.assertArrayEquals(new double[]{0, 5, -8, -22}, testObject.coefficients);
        Assertions.assertEquals(5, testObject.free);
    }

    @Test
    public void combine_WhenNoConstant_ThenAddEquation()
    {
        // when
        testObject.combine(new Equation(new double[]{1, -1, 4, 10}, 5));
        // then
        Assertions.assertArrayEquals(new double[]{3, 2, 4, 8}, testObject.coefficients);
        Assertions.assertEquals(20, testObject.free);
    }

    @Test
    public void combine_WhenConstantIsZero_ThenArithmeticException()
    {
        // when - then
        Assertions.assertThrows(ArithmeticException.class, () -> testObject.combine(
                new Equation(new double[]{1, -1, 10, 7}, 5), 0));
    }
}
