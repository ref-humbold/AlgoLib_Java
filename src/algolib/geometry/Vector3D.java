package algolib.geometry;

import java.util.Objects;

/** Structure of vector in a space */
public final class Vector3D
{
    public final double x;
    public final double y;
    public final double z;

    public Vector3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3D of(double x, double y, double z)
    {
        return new Vector3D(x, y, z);
    }

    public static Vector3D between(Point3D begin, Point3D end)
    {
        return new Vector3D(end.x - begin.x, end.y - begin.y, end.z - begin.z);
    }

    public static Vector3D fromVector(Vector p)
    {
        if(p.dims() != 3)
            throw new IllegalArgumentException("Point should have exactly 3 dimensions");

        return new Vector3D(p.dim(1), p.dim(2), p.dim(3));
    }

    public static double area(Vector3D v1, Vector3D v2)
    {
        return v1.cross(v2).length();
    }

    public static double volume(Vector3D v1, Vector3D v2, Vector3D v3)
    {
        return v1.dot(v2.cross(v3));
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Vector3D))
            return false;

        Vector3D other = (Vector3D)obj;

        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, z, 0x935fe66);
    }

    @Override
    public String toString()
    {
        return String.format("[%f, %f, %f]", x, y, z);
    }

    public Vector toVector()
    {
        return new Vector(x, y, z);
    }

    public double length()
    {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3D add(Vector3D v)
    {
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }

    public Vector3D subtract(Vector3D v)
    {
        return new Vector3D(x - v.x, y - v.y, z - v.z);
    }

    public Vector3D multiply(double c)
    {
        return new Vector3D(x * c, y * c, z * c);
    }

    public Vector3D divide(double c)
    {
        if(c == 0)
            throw new ArithmeticException("Division by zero");

        return new Vector3D(x / c, y / c, z / c);
    }

    public double dot(Vector3D v)
    {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector3D cross(Vector3D v)
    {
        return new Vector3D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }
}
