package algolib.maths;

import java.util.Arrays;

/** Structure of linear equation system. */
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

    /**
     * Gets the number of equations.
     * @return the number of equations
     */
    public int size()
    {
        return equations.length;
    }

    /**
     * Gets the equation at given index.
     * @param i the index of equation
     * @return the equation of this system specified by the index
     */
    public Equation getEquation(int i)
    {
        return equations[i];
    }

    /**
     * Computes solution of this equation system.
     * @return the solution
     * @throws InfiniteSolutionsException if there are infinitely many solutions
     * @throws NoSolutionException if there is no solution
     */
    public double[] solve()
            throws InfiniteSolutionsException, NoSolutionException
    {
        gaussianReduce();

        if(equations[equations.length - 1].getCoefficient(equations.length - 1) == 0
                   && equations[equations.length - 1].getFreeTerm() == 0)
            throw new InfiniteSolutionsException();

        if(equations[equations.length - 1].getCoefficient(equations.length - 1) == 0
                   && equations[equations.length - 1].getFreeTerm() != 0)
            throw new NoSolutionException();

        double[] solution = new double[equations.length];

        solution[equations.length - 1] =
                equations[equations.length - 1].getFreeTerm() / equations[equations.length
                                                                                  - 1].getCoefficient(
                        equations.length - 1);

        for(int i = equations.length - 2; i >= 0; --i)
        {
            double value = equations[i].getFreeTerm();

            for(int j = equations.length - 1; j > i; --j)
                value -= equations[i].getCoefficient(j) * solution[j];

            solution[i] = value / equations[i].getCoefficient(i);
        }

        return solution;
    }

    /** Runs the Gaussian elimination algorithm on this equation system. */
    public void gaussianReduce()
    {
        for(int i = 0; i < equations.length - 1; ++i)
        {
            int indexMin = i;

            for(int j = i + 1; j < equations.length; ++j)
            {
                double minCoef = equations[indexMin].getCoefficient(i);
                double actCoef = equations[j].getCoefficient(i);

                if(actCoef != 0 && (minCoef == 0 || Math.abs(actCoef) < Math.abs(minCoef)))
                    indexMin = j;
            }

            if(equations[indexMin].getCoefficient(i) != 0)
            {
                swap(indexMin, i);

                for(int j = i + 1; j < equations.length; ++j)
                {
                    double param = equations[j].getCoefficient(i) / equations[i].getCoefficient(i);

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
}
