package algolib.geometry.space;

import java.util.Objects;

/** Structure of vector in 3 dimensions */
public final class Vector3D
{
    public final double x;
    public final double y;
    public final double z;

    private Vector3D(double x, double y, double z)
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
        return Vector3D.of(end.x - begin.x, end.y - begin.y, end.z - begin.z);
    }

    public static double dot(Vector3D v1, Vector3D v2)
    {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static Vector3D cross(Vector3D v1, Vector3D v2)
    {
        return Vector3D.of(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z,
                           v1.x * v2.y - v1.y * v2.x);
    }

    public static double area(Vector3D v1, Vector3D v2)
    {
        return cross(v1, v2).length();
    }

    public static double volume(Vector3D v1, Vector3D v2, Vector3D v3)
    {
        return dot(v1, cross(v2, v3));
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
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString()
    {
        return String.format("[%f, %f, %f]", x, y, z);
    }

    public double length()
    {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3D add(Vector3D v)
    {
        return Vector3D.of(x + v.x, y + v.y, z + v.z);
    }

    public Vector3D subtract(Vector3D v)
    {
        return Vector3D.of(x - v.x, y - v.y, z - v.z);
    }

    public Vector3D multiply(double c)
    {
        return Vector3D.of(x * c, y * c, z * c);
    }

    public Vector3D divide(double c)
    {
        if(c == 0)
            throw new ArithmeticException("Division by zero");

        return Vector3D.of(x / c, y / c, z / c);
    }
}
