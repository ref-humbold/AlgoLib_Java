package com.github.refhumbold.algolib.geometry.dim2;

import java.util.Objects;
import com.github.refhumbold.algolib.geometry.GeometryComparator;

public final class Angle
        implements Comparable<Angle>
{
    private static final GeometryComparator comparator = new GeometryComparator();
    private static final double fullAngleDeg = 360.0;
    private final double degrees;

    private Angle(double degrees)
    {
        this.degrees = degrees;
    }

    public double getDegrees()
    {
        return degrees;
    }

    public double getRadians()
    {
        return Math.toRadians(degrees);
    }

    public static Angle fromDegrees(double degrees)
    {
        return new Angle((degrees % fullAngleDeg + fullAngleDeg) % fullAngleDeg);
    }

    public static Angle fromRadians(double radians)
    {
        return fromDegrees(Math.toDegrees(radians));
    }

    @Override
    public boolean equals(Object obj)
    {
        return this == obj
                || obj instanceof Angle other && comparator.compare(degrees, other.degrees) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(degrees);
    }

    @Override
    public int compareTo(Angle angle)
    {
        return angle == null ? 1 : comparator.compare(degrees, angle.degrees);
    }
}
