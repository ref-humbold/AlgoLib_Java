// Structure of point on a plane
package algolib.geometry;

import java.util.Objects;

public final class Point2D
{
    public final double x;
    public final double y;

    public Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
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

        if(!(obj instanceof Point2D))
            return false;

        Point2D other = (Point2D)obj;

        return Objects.equals(x, other.x) && Objects.equals(y, other.y);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, 0x933ff53);
    }

    @Override
    public String toString()
    {
        return String.format("(%f, %f)", x, y);
    }

    public double angle()
    {
        double ang = Math.atan2(y, x) * 90.0 / (Math.PI / 2.0);

        return y >= 0.0 ? ang : ang + 360.0;
    }

    public double radius()
    {
        return x * x + y * y;
    }
}
