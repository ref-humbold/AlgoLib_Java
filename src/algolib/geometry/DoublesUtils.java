package algolib.geometry;

public final class DoublesUtils
{
    public static final double EPSILON = 1e-15;

    private DoublesUtils()
    {
    }

    public static boolean equal(double c1, double c2)
    {
        return Math.abs(c1 - c2) <= EPSILON;
    }
}
