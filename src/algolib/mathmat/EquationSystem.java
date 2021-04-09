package algolib.mathmat;

import java.util.Arrays;

/** Structure of linear equation system with Gauss elimination algorithm */
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

    public Equation getEquation(int i)
    {
        return equations[i];
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        EquationSystem other = (EquationSystem)obj;

        return Arrays.equals(equations, other.equations);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(equations);
    }

    public int size()
    {
        return equations.length;
    }

    /**
     * Computes the solution of this equation system.
     * @return solution vector
     * @throws NoSolutionException if there is no solution
     * @throws InfiniteSolutionsException if there are infinitely many solutions
     */
    public double[] solve()
            throws InfiniteSolutionsException, NoSolutionException
    {
        gaussianReduce();

        if(equations[equations.length - 1].getCoefficient(equations.length - 1) == 0
                && equations[equations.length - 1].getFree() == 0)
            throw new InfiniteSolutionsException();

        if(equations[equations.length - 1].getCoefficient(equations.length - 1) == 0
                && equations[equations.length - 1].getFree() != 0)
            throw new NoSolutionException();

        double[] solution = new double[equations.length];

        solution[equations.length - 1] =
                equations[equations.length - 1].getFree() / equations[equations.length
                        - 1].getCoefficient(equations.length - 1);

        for(int i = equations.length - 2; i >= 0; --i)
        {
            double value = equations[i].getFree();

            for(int j = equations.length - 1; j > i; --j)
                value -= equations[i].getCoefficient(j) * solution[j];

            solution[i] = value / equations[i].getCoefficient(i);
        }

        return solution;
    }

    /** Runs the Gauss elimination algorithm. */
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
                        equations[j].combine(equations[i], -param);
                }
            }
        }
    }

    /**
     * Swaps two equations.
     * @param i index of first equation
     * @param j index of second equation
     */
    public void swap(int i, int j)
    {
        Equation temp = equations[i];

        equations[i] = equations[j];
        equations[j] = temp;
    }

    public boolean isSolution(double[] solution)
    {
        return Arrays.stream(equations).allMatch(eq -> eq.isSolution(solution));
    }
}
