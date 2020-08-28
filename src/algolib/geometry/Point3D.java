package algolib.geometry;

import java.util.Objects;

/** Structure of point in a space */
public final class Point3D
{
    public static final Point3D ZERO = new Point3D(0.0, 0.0, 0.0);
    public final double x;
    public final double y;
    public final double z;

    public Point3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(Point2D p)
    {
        this(p.x, p.y, 0);
    }

    public static Point3D of(double x, double y, double z)
    {
        return new Point3D(x, y, z);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Point3D))
            return false;

        Point3D other = (Point3D)obj;

        return Objects.equals(x, other.x) && Objects.equals(y, other.y) && Objects.equals(z,
                                                                                          other.z);
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

    public double radius()
    {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
