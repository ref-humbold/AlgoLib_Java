package algolib.geometry;

public abstract class GeometryObject
{
    private static final double EPSILON = 1e-15;

    protected GeometryObject()
    {
    }

    protected boolean areEqual(double d1, double d2)
    {
        return Math.abs(d1 - d2) <= EPSILON;
    }
}
