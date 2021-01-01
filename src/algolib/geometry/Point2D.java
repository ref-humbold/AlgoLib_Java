package algolib.geometry;

import java.util.Objects;

/** Structure of point on a plane */
public final class Point2D
{
    public final double x;
    public final double y;

    private Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public static Point2D of(double x, double y)
    {
        return new Point2D(x, y);
    }

    public static Point2D fromPoint(Point p)
    {
        if(p.dims() != 2)
            throw new IllegalArgumentException("Point should have exactly 2 dimensions");

        return Point2D.of(p.dim(1), p.dim(2));
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Point2D))
            return false;

        Point2D other = (Point2D)obj;

        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public String toString()
    {
        return String.format("(%f, %f)", x, y);
    }

    public Point toPoint()
    {
        return Point.of(x, y);
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
