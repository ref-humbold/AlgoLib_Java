package com.github.refhumbold.algolib.geometry.dim2;

import java.text.DecimalFormat;
import java.util.Objects;
import com.github.refhumbold.algolib.geometry.GeometryComparator;

/** Structure of point in 2D. */
public final class Point2D
{
    public static final Point2D ZERO = of(0, 0);
    private static final GeometryComparator comparator = new GeometryComparator();
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

    public double[] coordinates()
    {
        return new double[]{x, y};
    }

    public double radius()
    {
        return Math.sqrt(x * x + y * y);
    }

    public Angle angle()
    {
        return Angle.fromRadians(Math.atan2(y, x));
    }

    @Override
    public boolean equals(Object obj)
    {
        return this == obj || obj instanceof Point2D other && comparator.compare(x, other.x) == 0
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

        return "(%s, %s)".formatted(df.format(x), df.format(y));
    }
}
