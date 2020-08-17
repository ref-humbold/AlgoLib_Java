// Structure of point on a plane
package algolib.geometry;

import java.util.Objects;

public final class Vector2D
{
    public final double x;
    public final double y;

    public Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public static Vector2D of(double x, double y)
    {
        return new Vector2D(x, y);
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

        return Objects.equals(x, other.x) && Objects.equals(y, other.y);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, 0x935fe66);
    }

    @Override
    public String toString()
    {
        return String.format("[%f, %f]", x, y);
    }

    public double length()
    {
        return x * x + y * y;
    }

    public Vector2D add(Vector2D v)
    {
        return new Vector2D(x + v.x, y + v.y);
    }

    public Vector2D subtract(Vector2D v)
    {
        return new Vector2D(x - v.x, y - v.y);
    }

    public Vector2D multiply(double c)
    {
        return new Vector2D(x * c, y * c);
    }

    public Vector2D divide(double c)
    {
        if(c == 0)
            throw new ArithmeticException("Division by zero");

        return new Vector2D(x / c, y / c);
    }

    public double dot(Vector2D v)
    {
        return x * v.x + y * v.y;
    }
}
