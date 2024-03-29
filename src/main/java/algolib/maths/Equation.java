package algolib.maths;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Structure of linear equation. */
public final class Equation
{
    private final double[] coefficients;
    private final double freeTerm;

    private Equation(double[] coefficients, double freeTerm)
    {
        this.coefficients = coefficients;
        this.freeTerm = freeTerm;
    }

    public double[] getCoefficients()
    {
        return coefficients;
    }

    public double getFreeTerm()
    {
        return freeTerm;
    }

    public static Equation of(double[] coefficients, double free)
    {
        return new Equation(coefficients, free);
    }

    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return IntStream.range(0, coefficients.length)
                        .filter(i -> coefficients[i] != 0)
                        .mapToObj(i -> "%s x_%d".formatted(df.format(coefficients[i]), i))
                        .collect(Collectors.joining(" + ", "",
                                                    " = %s".formatted(df.format(freeTerm))));
    }

    /**
     * Gets the number of variables in this equation.
     * @return the number of variables
     */
    public int size()
    {
        return coefficients.length;
    }

    /**
     * Negates this equation.
     * @return the equation with all coefficients negated
     */
    public Equation negate()
    {
        return new Equation(Arrays.stream(coefficients).map(c -> -c).toArray(), -freeTerm);
    }

    /**
     * Adds given equation to this equation.
     * @param equation the other equation
     * @return the equation with coefficients added
     * @throws IllegalArgumentException if the equations have different number of variables
     */
    public Equation add(Equation equation)
    {
        if(equation.size() != coefficients.length)
            throw new IllegalArgumentException("Equation has different number of variables");

        return new Equation(IntStream.range(0, coefficients.length)
                                     .mapToDouble(i -> coefficients[i] + equation.coefficients[i])
                                     .toArray(), freeTerm + equation.freeTerm);
    }

    /**
     * Subtracts given equation from this equation.
     * @param equation the other equation
     * @return the equation with coefficients subtracted
     * @throws IllegalArgumentException if the equations have different number of variables
     */
    public Equation subtract(Equation equation)
    {
        if(equation.size() != coefficients.length)
            throw new IllegalArgumentException("Equation has different number of variables");

        return new Equation(IntStream.range(0, coefficients.length)
                                     .mapToDouble(i -> coefficients[i] - equation.coefficients[i])
                                     .toArray(), freeTerm - equation.freeTerm);
    }

    /**
     * Multiplies this equation by given constant.
     * @param constant the constant
     * @return the equation with all coefficients multiplied
     * @throws ArithmeticException if the constant is equal to zero
     */
    public Equation multiply(double constant)
    {
        if(constant == 0)
            throw new ArithmeticException("Constant cannot be zero");

        return new Equation(Arrays.stream(coefficients).map(c -> c * constant).toArray(),
                            freeTerm * constant);
    }

    /**
     * Divides this equation by given constant.
     * @param constant the constant
     * @return the equation with all coefficients divided
     * @throws ArithmeticException if the constant is equal to zero
     */
    public Equation divide(double constant)
    {
        if(constant == 0)
            throw new ArithmeticException("Constant cannot be zero");

        return new Equation(Arrays.stream(coefficients).map(c -> c / constant).toArray(),
                            freeTerm / constant);
    }

    /**
     * Checks whether given values solve this equation.
     * @param solution the values
     * @return {@code true} if the solution is correct, otherwise {@code false}
     */
    public boolean hasSolution(double[] solution)
    {
        if(solution.length != coefficients.length)
            return false;

        return IntStream.range(0, coefficients.length)
                        .mapToDouble(i -> solution[i] * coefficients[i])
                        .sum() == freeTerm;
    }
}
