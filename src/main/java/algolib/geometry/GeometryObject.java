package algolib.geometry;

public abstract class GeometryObject
{
    public static final double EPSILON = 1e-12;

    protected GeometryObject()
    {
    }

    protected static boolean areEqual(double d1, double d2)
    {
        return Math.abs(d1 - d2) < EPSILON;
    }

    public abstract double[] getCoordinates();
}
