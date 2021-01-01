package algolib.geometry;

import java.util.Objects;

/** Structure of vector on a plane */
public final class Vector2D
{
    public final double x;
    public final double y;

    private Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public static Vector2D of(double x, double y)
    {
        return new Vector2D(x, y);
    }

    public static Vector2D between(Point2D begin, Point2D end)
    {
        return Vector2D.of(end.x - begin.x, end.y - begin.y);
    }

    public static Vector2D fromVector(Vector p)
    {
        if(p.dims() != 2)
            throw new IllegalArgumentException("Vector should have exactly 2 dimensions");

        return Vector2D.of(p.dim(1), p.dim(2));
    }

    public static double dot(Vector2D v1, Vector2D v2)
    {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public static double area(Vector2D v1, Vector2D v2)
    {
        return v1.x * v2.y - v1.y * v2.x;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Vector2D))
            return false;

        Vector2D other = (Vector2D)obj;

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
        return String.format("[%f, %f]", x, y);
    }

    public Vector toVector()
    {
        return Vector.of(x, y);
    }

    public double length()
    {
        return x * x + y * y;
    }

    public Vector2D add(Vector2D v)
    {
        return Vector2D.of(x + v.x, y + v.y);
    }

    public Vector2D subtract(Vector2D v)
    {
        return Vector2D.of(x - v.x, y - v.y);
    }

    public Vector2D multiply(double c)
    {
        return Vector2D.of(x * c, y * c);
    }

    public Vector2D divide(double c)
    {
        if(c == 0)
            throw new ArithmeticException("Division by zero");

        return Vector2D.of(x / c, y / c);
    }
}
