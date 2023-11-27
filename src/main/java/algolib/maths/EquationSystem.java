package algolib.maths;

import java.util.Arrays;
import java.util.stream.Collectors;

/** Structure of system of linear equations. */
public final class EquationSystem
{
    private final Equation[] equations;

    private EquationSystem(Equation[] equations)
    {
        this.equations = equations;

        if(Arrays.stream(this.equations).anyMatch(eq -> eq.size() != this.equations[0].size()))
            throw new IllegalArgumentException("Incorrect number of variables in one of equations");
    }

    public static EquationSystem of(Equation... equations)
    {
        return new EquationSystem(equations);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof EquationSystem other))
            return false;

        return Arrays.equals(equations, other.equations);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(equations);
    }

    @Override
    public String toString()
    {
        return "{ %s }".formatted(Arrays.stream(equations)
                                        .map(Equation::toString)
                                        .collect(Collectors.joining(" ; ")));
    }

    /**
     * Gets the number of equations in this system.
     * @return the number of equations
     */
    public int size()
    {
        return equations.length;
    }

    /**
     * Gets the equation at given index.
     * @param i the index
     * @return the equation by the index
     */
    public Equation getEquation(int i)
    {
        return equations[i];
    }

    /**
     * Computes solution of this equation system.
     * @return the solution
     * @throws InfiniteSolutionsException if infinitely many solutions
     * @throws NoSolutionException if no solution
     */
    public double[] solve()
            throws InfiniteSolutionsException, NoSolutionException
    {
        gaussianReduce();

        int lastIndex = equations.length - 1;

        if(equations[lastIndex].getCoefficients()[lastIndex] == 0
                   && equations[lastIndex].getFreeTerm() == 0)
            throw new InfiniteSolutionsException();

        if(equations[lastIndex].getCoefficients()[lastIndex] == 0
                   && equations[lastIndex].getFreeTerm() != 0)
            throw new NoSolutionException();

        double[] solution = new double[equations.length];

        solution[lastIndex] = equations[lastIndex].getFreeTerm()
                                      / equations[lastIndex].getCoefficients()[lastIndex];

        for(int i = equations.length - 2; i >= 0; --i)
        {
            double value = equations[i].getFreeTerm();

            for(int j = lastIndex; j > i; --j)
                value -= equations[i].getCoefficients()[j] * solution[j];

            solution[i] = value / equations[i].getCoefficients()[i];
        }

        return solution;
    }

    /** Runs the Gaussian elimination algorithm on this equation system. */
    public void gaussianReduce()
    {
        for(int i = 0; i < equations.length - 1; ++i)
        {
            int indexMin = getMinimalCoefficientIndex(i);

            if(equations[indexMin].getCoefficients()[i] != 0)
            {
                swap(indexMin, i);

                for(int j = i + 1; j < equations.length; ++j)
                {
                    double param =
                            equations[j].getCoefficients()[i] / equations[i].getCoefficients()[i];

                    if(param != 0)
                        equations[j] = equations[j].add(equations[i].multiply(-param));
                }
            }
        }
    }

    /**
     * Swaps two equations in this system.
     * @param i the index of the first equation
     * @param j the index of the second equation
     */
    public void swap(int i, int j)
    {
        Equation temp = equations[i];

        equations[i] = equations[j];
        equations[j] = temp;
    }

    /**
     * Checks whether given values solve this equation system.
     * @param solution the values
     * @return {@code true} if the solution is correct, otherwise {@code false}
     */
    public boolean hasSolution(double[] solution)
    {
        return Arrays.stream(equations).allMatch(eq -> eq.hasSolution(solution));
    }

    private int getMinimalCoefficientIndex(int startingIndex)
    {
        int indexMin = startingIndex;

        for(int i = startingIndex + 1; i < equations.length; ++i)
        {
            double minCoefficient = equations[indexMin].getCoefficients()[startingIndex];
            double currentCoefficient = equations[i].getCoefficients()[startingIndex];

            if(currentCoefficient != 0 && (minCoefficient == 0
                                                   || Math.abs(currentCoefficient) < Math.abs(
                    minCoefficient)))
                indexMin = i;
        }

        return indexMin;
    }
}
