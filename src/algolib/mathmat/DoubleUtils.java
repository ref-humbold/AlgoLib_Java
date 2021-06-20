package algolib.mathmat;

public final class DoubleUtils
{
    public static final double EPSILON = 1e-15;

    private DoubleUtils()
    {
    }

    public static boolean equal(double d1, double d2)
    {
        return Math.abs(d1 - d2) <= EPSILON;
    }

    public static boolean notEqual(double d1, double d2)
    {
        return !equal(d1, d2);
    }
}
