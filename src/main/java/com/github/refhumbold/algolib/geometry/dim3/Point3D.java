package com.github.refhumbold.algolib.geometry.dim3;

import java.text.DecimalFormat;
import java.util.Objects;
import com.github.refhumbold.algolib.geometry.GeometryComparator;

/** Structure of point in 3D. */
public final class Point3D
{
    public static final Point3D ZERO = of(0, 0, 0);
    private static final GeometryComparator comparator = new GeometryComparator();
    public final double x;
    public final double y;
    public final double z;

    private Point3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double[] getCoordinates()
    {
        return new double[]{ x, y, z };
    }

    public static Point3D of(double x, double y, double z)
    {
        return new Point3D(x, y, z);
    }

    @Override
    public boolean equals(Object obj)
    {
        return this == obj || obj instanceof Point3D other && comparator.compare(x, other.x) == 0
                && comparator.compare(y, other.y) == 0 && comparator.compare(z, other.z) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString()
    {
        var df = new DecimalFormat("0.0###########");

        return "(%s, %s, %s)".formatted(df.format(x), df.format(y), df.format(z));
    }

    public double radius()
    {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
