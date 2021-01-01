package algolib.mathmat;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/** Structure of linear equation */
public final class Equation
{
    private final double[] coefficients;
    private double free;

    private Equation(double[] coefficients, double free)
    {
        this.coefficients = coefficients;
        this.free = free;
    }

    public static Equation of(double[] coefficients, double free)
    {
        return new Equation(coefficients, free);
    }

    public double getFree()
    {
        return free;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Equation other = (Equation)obj;

        return Double.compare(other.free, free) == 0 && Arrays.equals(coefficients,
                                                                      other.coefficients);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(free, Arrays.hashCode(coefficients));
    }

    public double getCoefficient(int i)
    {
        return coefficients[i];
    }

    public int size()
    {
        return coefficients.length;
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
