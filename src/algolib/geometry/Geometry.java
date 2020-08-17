// Basic geometrical operations
package algolib.geometry;

public final class Geometry
{
    public Point2D translate(Point2D p, Vector2D v)
    {
        return new Point2D(p.x + v.x, p.y + v.y);
    }

    public Point3D translate(Point3D p, Vector3D v)
    {
        return new Point3D(p.x + v.x, p.y + v.y, p.z + v.z);
    }
}
