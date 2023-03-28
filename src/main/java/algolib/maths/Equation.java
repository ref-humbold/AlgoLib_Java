package algolib.maths;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
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

    public double getCoefficient(int i)
    {
        return coefficients[i];
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

    @Override
    public String toString()
    {
        return IntStream.range(0, coefficients.length)
                        .filter(i -> coefficients[i] != 0)
                        .mapToObj(i -> String.format("%f x_%d", coefficients[i], i))
                        .collect(Collectors.joining(" + ", "", String.format(" = %f", free)));
    }

    public int size()
    {
        return coefficients.length;
    }

    /**
     * Adds given equation to this equation.
     * @param equation equation to be added
     * @throws IllegalArgumentException if equations sizes are different
     */
    public void add(Equation equation)
    {
        if(equation.size() != coefficients.length)
            throw new IllegalArgumentException("Equation has different number of variables");

        for(int i = 0; i < coefficients.length; ++i)
            coefficients[i] += equation.coefficients[i];

        free += equation.free;
    }

    /**
     * Subtracts given equation from this equation.
     * @param equation equation to be subtracted
     * @throws IllegalArgumentException if equations sizes are different
     */
    public void subtract(Equation equation)
    {
        if(equation.size() != coefficients.length)
            throw new IllegalArgumentException("Equation has different number of variables");

        for(int i = 0; i < coefficients.length; ++i)
            coefficients[i] -= equation.coefficients[i];

        free -= equation.free;
    }

    /**
     * Multiplies this equation by given constant.
     * @param constant the constant
     * @throws ArithmeticException if the constant is zero
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
     * Divides this equation by given constant.
     * @param constant the constant
     * @throws ArithmeticException if the constant is zero
     */
    public void divide(double constant)
    {
        if(constant == 0)
            throw new ArithmeticException("Constant cannot be zero");

        for(int i = 0; i < coefficients.length; ++i)
            coefficients[i] /= constant;

        free /= constant;
    }

    /**
     * Transforms equation through linear combination with given equation.
     * @param equation the equation
     * @param constant the linear combination constant
     * @throws IllegalArgumentException if equations sizes are different
     * @throws ArithmeticException if the constant is zero
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
     * Checks whether given values solve this equation.
     * @param solution the values
     * @return {@code true} if the solution is correct, otherwise {@code false}
     */
    public boolean isSolution(double[] solution)
    {
        if(solution.length != coefficients.length)
            return false;

        double result = IntStream.range(0, coefficients.length)
                                 .mapToDouble(i -> solution[i] * coefficients[i])
                                 .sum();

        return result == free;
    }
}
