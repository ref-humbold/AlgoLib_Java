package algolib.geometry.dim2;

import java.util.Objects;

import algolib.geometry.GeometryObject;

/** Structure of point in 2 dimensions. */
public final class Point2D
        extends GeometryObject
{
    public final double x;
    public final double y;

    private Point2D(double x, double y)
    {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public double[] getCoordinates()
    {
        return new double[]{x, y};
    }

    public static Point2D of(double x, double y)
    {
        return new Point2D(x, y);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Point2D other))
            return false;

        return areEqual(x, other.x) && areEqual(y, other.y);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public String toString()
    {
        return "(%f, %f)".formatted(x, y);
    }

    public double radius()
    {
        return Math.sqrt(x * x + y * y);
    }

    public double angleRad()
    {
        return Math.atan2(y, x);
    }

    public double angleDeg()
    {
        double ang = angleRad() * 180.0 / Math.PI;

        return y >= 0.0 ? ang : ang + 360.0;
    }
}
