package algolib.geometry;

import java.util.Objects;

/** Structure of point in a space */
public final class Point3D
{
    public final double x;
    public final double y;
    public final double z;

    public Point3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Point3D of(double x, double y, double z)
    {
        return new Point3D(x, y, z);
    }

    public static Point3D fromPoint(Point p)
    {
        if(p.dims() != 3)
            throw new IllegalArgumentException("Point should have exactly 3 dimensions");

        return new Point3D(p.dim(1), p.dim(2), p.dim(3));
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Point3D))
            return false;

        Point3D other = (Point3D)obj;

        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, z, 0x933ff53);
    }

    @Override
    public String toString()
    {
        return String.format("(%f, %f, %f)", x, y, z);
    }

    public Point toPoint()
    {
        return new Point(x, y, z);
    }

    public double radius()
    {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
