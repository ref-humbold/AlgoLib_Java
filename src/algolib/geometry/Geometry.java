package algolib.geometry;

import java.util.stream.IntStream;

/** Basic geometric operations */
public final class Geometry
{
    public static double distance(Point p1, Point p2)
    {
        int dims = Math.max(p1.dims(), p2.dims());
        Point newP1 = p1.project(dims);
        Point newP2 = p2.project(dims);

        return Math.sqrt(IntStream.rangeClosed(1, dims)
                                  .mapToDouble(i -> newP2.dim(i) - newP1.dim(i))
                                  .map(c -> c * c)
                                  .sum());
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

    public Point translate(Point p, Vector v)
    {
        int dims = Math.max(p.dims(), v.dims());
        Point newPoint = p.project(dims);
        Vector newVector = v.project(dims);

        return Point.of(IntStream.rangeClosed(1, dims)
                                 .mapToDouble(i -> newPoint.dim(i) + newVector.dim(i))
                                 .toArray());
    }

    public Point2D translate(Point2D p, Vector2D v)
    {
        return Point2D.of(p.x + v.x, p.y + v.y);
    }

    public Point3D translate(Point3D p, Vector3D v)
    {
        return Point3D.of(p.x + v.x, p.y + v.y, p.z + v.z);
    }
}
