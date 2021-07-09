package algolib.geometry.dim3;

import java.util.Objects;

import algolib.geometry.GeometryObject;

/** Structure of point in 3 dimensions */
public final class Point3D
        extends GeometryObject
{
    public final double x;
    public final double y;
    public final double z;

    private Point3D(double x, double y, double z)
    {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Point3D of(double x, double y, double z)
    {
        return new Point3D(x, y, z);
    }

    @Override
    public double[] getCoordinates()
    {
        return new double[]{x, y, z};
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Point3D))
            return false;

        Point3D other = (Point3D)obj;

        return areEqual(x, other.x) && areEqual(y, other.y) && areEqual(z, other.z);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, z);
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
