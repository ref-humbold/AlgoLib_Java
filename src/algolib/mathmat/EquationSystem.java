// STRUKTURA UKŁADÓW RÓWNAŃ LINIOWYCH Z ALGORYTMEM ELIMINACJI GAUSSA
package algolib.mathmat;

import java.util.Arrays;
import java.util.Objects;

public class EquationSystem
{
    /** Liczba równań układu. */
    private final int equations;

    /** Macierz współczynników równania */
    private double[][] coeffs;

    /** Wektor wyrazów wolnych równania */
    private double[] freeTerms;

    public EquationSystem(int numEq)
    {
        equations = numEq;
        coeffs = new double[numEq][numEq];
        freeTerms = new double[numEq];
    }

    public EquationSystem(int numEq, double[][] coef, double[] frees)
    {
        validate(numEq, coef, frees);
        equations = numEq;
        coeffs = coef;
        freeTerms = frees;
    }

    public int getEquationsNumber()
    {
        return equations;
    }

    /**
     * Wyliczanie rozwiązań układu równań liniowych
     * @return wektor wyniku równania
     */
    public double[] solve()
        throws Exception
    {
        gauss();

        if(coeffs[equations - 1][equations - 1] == 0 && freeTerms[equations - 1] == 0)
            throw new InfiniteSolutionsException();

        if(coeffs[equations - 1][equations - 1] == 0 && freeTerms[equations - 1] != 0)
            throw new NoSolutionException();

        double[] solution = new double[equations];

        solution[equations - 1] = freeTerms[equations - 1] / coeffs[equations - 1][equations - 1];

        for(int equ = equations - 2; equ >= 0; --equ)
        {
            double value = freeTerms[equ];

            for(int i = equations - 1; i > equ; --i)
                value -= coeffs[equ][i] * solution[i];

            solution[equ] = value / coeffs[equ][equ];
        }

        return solution;
    }

    /** Algorytm eliminacji Gaussa */
    public void gauss()
    {
        for(int equ = 0; equ < equations - 1; ++equ)
        {
            int indexMin = equ;

            for(int i = equ + 1; i < equations; ++i)
            {
                double minCoef = coeffs[indexMin][equ];
                double actCoef = coeffs[i][equ];

                if(actCoef != 0 && (minCoef == 0 || Math.abs(actCoef) < Math.abs(minCoef)))
                    indexMin = i;
            }

            if(coeffs[indexMin][equ] != 0)
            {
                swap(indexMin, equ);

                for(int i = equ + 1; i < equations; ++i)
                {
                    double param = coeffs[i][equ] / coeffs[equ][equ];

                    combine(i, equ, -param);
                }
            }
        }
    }

    /**
     * Przemnożenie równania przez niezerową stałą.
     * @param eq1 numer równania
     * @param constant stała
     */
    public void mult(int eq1, double constant)
    {
        if(constant == 0)
            throw new IllegalArgumentException("Constant cannot equal zero.");

        for(int i = 0; i < equations; ++i)
            coeffs[eq1][i] *= constant;

        freeTerms[eq1] *= constant;
    }

    /**
     * Zamiana równań miejscami
     * @param eq1 numer pierwszego równania
     * @param eq2 numer drugiego równania
     */
    public void swap(int eq1, int eq2)
    {
        double temp;

        for(int i = 0; i < equations; ++i)
        {
            temp = coeffs[eq1][i];
            coeffs[eq1][i] = coeffs[eq2][i];
            coeffs[eq2][i] = temp;
        }

        temp = freeTerms[eq1];
        freeTerms[eq1] = freeTerms[eq2];
        freeTerms[eq2] = temp;
    }

    /**
     * Przekształcenie równania przez kombinację liniową z innym równaniem
     * @param eq1 numer równania przekształcanego
     * @param eq2 numer drugiego równania
     * @param constant stała kombinacji liniowej
     */
    public void combine(int eq1, int eq2, double constant)
    {
        for(int i = 0; i < equations; ++i)
            coeffs[eq1][i] += constant * coeffs[eq2][i];

        freeTerms[eq1] += constant * freeTerms[eq2];
    }

    private void validate(int numEq, double[][] coef, double[] frees)
    {
        Objects.requireNonNull(coef);
        Objects.requireNonNull(frees);

        if(coef.length != numEq || frees.length != numEq)
            throw new IllegalArgumentException("Wrong number of equations.");

        if(Arrays.stream(coef).anyMatch(a -> a.length != numEq))
            throw new IllegalArgumentException("Coefficient matrix is not a square matrix.");
    }
}
