// STRUKTURA UKŁADÓW RÓWNAŃ LINIOWYCH Z ALGORYTMEM ELIMINACJI GAUSSA
package refhumbold.algolib.structures;

public class EquationSystem
{
    /**
     * Liczba równań układu.
     */
    private int equations;

    /**
     * Macierz współczynników równania.
     */
    private double[][] coeffs;

    /**
     * Wektor wyrazów wolnych równania.
     */
    private double[] freeTerms;

    public EquationSystem(int numEq)
    {
        equations = numEq;
        coeffs = new double[numEq][numEq];
        freeTerms = new double[numEq];
    }

    public int getEquationsNumber()
    {
        return equations;
    }

    /**
     * Wyliczanie rozwiązań układu równań liniowych.
     * @return wektor wyniku równania
     */
    public double[] solve()
        throws Exception
    {
        gaussianReduce();

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

    /**
     * Algorytm eliminacji Gaussa.
     */
    public void gaussianReduce()
    {
        for(int equ = 0; equ < equations - 1; ++equ)
        {
            int index_min = equ;

            for(int i = equ + 1; i < equations; ++i)
            {
                double min_coef = coeffs[index_min][equ];
                double act_coef = coeffs[i][equ];

                if(act_coef != 0 && (min_coef == 0 || Math.abs(act_coef) < Math.abs(min_coef)))
                    index_min = i;
            }

            if(coeffs[index_min][equ] != 0)
            {
                change(index_min, equ);

                for(int i = equ + 1; i < equations; ++i)
                {
                    double param = coeffs[i][equ] / coeffs[equ][equ];

                    linearComb(i, equ, -param);
                }
            }
        }
    }

    /**
     * Zamiana równań miejscami.
     * @param eqnum1 numer pierwszego równania
     * @param eqnum2 numer drugiego równania
     */
    public void change(int eqnum1, int eqnum2)
    {
        double temp;

        for(int i = 0; i < equations; ++i)
        {
            temp = coeffs[eqnum1][i];
            coeffs[eqnum1][i] = coeffs[eqnum2][i];
            coeffs[eqnum2][i] = temp;
        }

        temp = freeTerms[eqnum1];
        freeTerms[eqnum1] = freeTerms[eqnum2];
        freeTerms[eqnum2] = temp;
    }

    /**
     * Przekształcenie równania przez kombinację liniową z innym równaniem.
     * @param eqnum1 numer równania przekształcanego
     * @param eqnum2 numer drugiego równania
     * @param constans stała kombinacji liniowej
     */
    public void linearComb(int eqnum1, int eqnum2, double constans)
    {
        for(int i = 0; i < equations; ++i)
            coeffs[eqnum1][i] += constans * coeffs[eqnum2][i];

        freeTerms[eqnum1] += constans * freeTerms[eqnum2];
    }
}
