// Structure of linear equation system with Gauss elimination algorithm
package algolib.mathmat;

import java.util.Arrays;

public class EquationSystem
{
    private Equation[] equations;

    public EquationSystem(Equation[] equations)
    {
        this.equations = equations;

        if(Arrays.stream(this.equations).anyMatch(eq -> eq.size() != this.equations[0].size()))
            throw new IllegalArgumentException("Incorrect number of variables in one of equations");
    }

    public int size()
    {
        return equations.length;
    }

    public Equation get(int i)
    {
        return equations[i];
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

        if(equations[equations.length - 1].get(equations.length - 1) == 0
                && equations[equations.length - 1].free == 0)
            throw new InfiniteSolutionsException();

        if(equations[equations.length - 1].get(equations.length - 1) == 0
                && equations[equations.length - 1].free != 0)
            throw new NoSolutionException();

        double[] solution = new double[equations.length];

        solution[equations.length - 1] =
                equations[equations.length - 1].free / equations[equations.length - 1].get(
                        equations.length - 1);

        for(int i = equations.length - 2; i >= 0; --i)
        {
            double value = equations[i].free;

            for(int j = equations.length - 1; j > i; --j)
                value -= equations[i].get(j) * solution[j];

            solution[i] = value / equations[i].get(i);
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
                double minCoef = equations[indexMin].get(i);
                double actCoef = equations[j].get(i);

                if(actCoef != 0 && (minCoef == 0 || Math.abs(actCoef) < Math.abs(minCoef)))
                    indexMin = j;
            }

            if(equations[indexMin].get(i) != 0)
            {
                swap(indexMin, i);

                for(int j = i + 1; j < equations.length; ++j)
                {
                    double param = equations[j].get(i) / equations[i].get(i);

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
