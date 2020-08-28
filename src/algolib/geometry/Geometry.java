package algolib.geometry;

/** Basic geometric operations */
public final class Geometry
{
    public static Vector2D vector(Point2D begin, Point2D end)
    {
        return new Vector2D(end.x - begin.x, end.y - begin.y);
    }

    public static Vector3D vector(Point3D begin, Point3D end)
    {
        return new Vector3D(end.x - begin.x, end.y - begin.y, end.z - begin.z);
    }

    public static double distance(Point2D p1, Point2D p2)
    {
        return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
    }

    public static double distance(Point3D p1, Point3D p2)
    {
        return Math.sqrt(
                (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y) + (p2.z - p1.z) * (
                        p2.z - p1.z));
    }

    public Point2D translate(Point2D p, Vector2D v)
    {
        return new Point2D(p.x + v.x, p.y + v.y);
    }

    public Point3D translate(Point3D p, Vector3D v)
    {
        return new Point3D(p.x + v.x, p.y + v.y, p.z + v.z);
    }
}
