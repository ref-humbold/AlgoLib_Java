// Structure of linear equation
package algolib.mathmat;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Equation
{
    public double[] coefficients;
    public double free;

    public Equation(double[] coefficients, double free)
    {
        this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
        this.free = free;
    }

    public int size()
    {
        return coefficients.length;
    }

    public double get(int i)
    {
        return coefficients[i];
    }

    /**
     * Multiplies equation by a constant.
     * @param constant constant
     * @throws ArithmeticException if constant is zero
     */
    public void multiply(double constant)
    {
        if(constant == 0)
            throw new ArithmeticException("Constant cannot be zero");

        for(int i = 0; i < coefficients.length; ++i)
            coefficients[i] *= constant;

        free *= constant;
    }

    /**
     * Transforms equation through a linear combination with another equation.
     * @param equation equation
     * @param constant linear combination constant
     * @throws IllegalArgumentException if equations sizes differ
     * @throws ArithmeticException if constant is zero
     */
    public void combine(Equation equation, double constant)
    {
        if(equation.size() != coefficients.length)
            throw new IllegalArgumentException("Equation has different number of variables");

        if(constant == 0)
            throw new ArithmeticException("Constant cannot be zero");

        for(int i = 0; i < coefficients.length; ++i)
            coefficients[i] += constant * equation.coefficients[i];

        free += constant * equation.free;
    }

    /**
     * Transforms equation by adding another equation.
     * @param equation equation
     * @throws IllegalArgumentException if equations sizes differ
     */
    public void combine(Equation equation)
    {
        combine(equation, 1);
    }

    /**
     * Checks whether given values solve this equation.
     * @param solution values to check
     * @return {@code true} if solution is correct, otherwise {@code false}
     */
    public boolean isSolution(double[] solution)
    {
        return solution.length == coefficients.length && IntStream.range(0, coefficients.length)
                                                                  .mapToDouble(i -> solution[i]
                                                                          * coefficients[i])
                                                                  .sum() == free;
    }
}
