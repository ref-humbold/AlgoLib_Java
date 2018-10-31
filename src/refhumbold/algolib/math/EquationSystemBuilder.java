package refhumbold.algolib.math;

public class EquationSystemBuilder
{
    private final int equationsNum;
    private int equationsAdded;
    private double[][] coeffs;
    private double[] freeTerms;

    public EquationSystemBuilder(int numEq)
    {
        this.equationsNum = numEq;
        this.equationsAdded = 0;
        this.coeffs = new double[numEq][numEq];
        this.freeTerms = new double[numEq];
    }

    public EquationSystemBuilder add(double[] coef, double free)
    {
        if(equationsAdded == equationsNum)
            throw new IllegalStateException("No equations can be added.");

        if(coef.length != equationsNum)
            throw new IllegalArgumentException("Incorrect number of coefficients.");

        coeffs[equationsAdded] = coef;
        freeTerms[equationsAdded] = free;
        ++equationsAdded;
        return this;
    }

    public EquationSystem build()
    {
        return new EquationSystem(equationsNum, coeffs, freeTerms);
    }
}
