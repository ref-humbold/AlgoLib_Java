package com.github.refhumbold.algolib.geometry.dim2;

import java.text.DecimalFormat;
import java.util.Objects;
import com.github.refhumbold.algolib.geometry.GeometryComparator;

/** Structure of vector in 2D. */
public final class Vector2D
{
    public static final Vector2D ZERO = of(0, 0);
    private static final GeometryComparator comparator = new GeometryComparator();
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

    public static double dot(Vector2D v1, Vector2D v2)
    {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public static double area(Vector2D v1, Vector2D v2)
    {
        return v1.x * v2.y - v1.y * v2.x;
    }

    public double[] coordinates()
    {
        return new double[]{x, y};
    }

    @Override
    public boolean equals(Object obj)
    {
        return this == obj || obj instanceof Vector2D other && comparator.compare(x, other.x) == 0
                && comparator.compare(y, other.y) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public String toString()
    {
        var df = new DecimalFormat("0.0###########");

        return "[%s, %s]".formatted(df.format(x), df.format(y));
    }

    public double length()
    {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D negate()
    {
        return Vector2D.of(-x, -y);
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
