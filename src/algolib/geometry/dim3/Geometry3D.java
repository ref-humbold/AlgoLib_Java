package algolib.geometry.dim3;

import java.util.List;

/** Basic geometric operations in 3 dimensions */
public final class Geometry3D
{
    /**
     * Mutably sorts points by their X coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sortByX(List<Point3D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.x, pt2.x));
    }

    /**
     * Mutably sorts points by their Y coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sortByY(List<Point3D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.y, pt2.y));
    }

    /**
     * Mutably sorts points by their Z coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sortByZ(List<Point3D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.z, pt2.z));
    }

    /**
     * Counts the distance between given points.
     * @param p1 first point
     * @param p2 second point
     * @return distance between the points
     */
    public static double distance(Point3D p1, Point3D p2)
    {
        return Math.sqrt(
                (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y) + (p2.z - p1.z) * (
                        p2.z - p1.z));
    }

    /**
     * Translates given point by given vector.
     * @param p a point
     * @param v a translation vector
     * @return result of translation
     */
    public static Point3D translate(Point3D p, Vector3D v)
    {
        return Point3D.of(p.x + v.x, p.y + v.y, p.z + v.z);
    }
}
