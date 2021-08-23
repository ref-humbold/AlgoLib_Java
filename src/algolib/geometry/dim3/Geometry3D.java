package algolib.geometry.dim3;

import java.util.List;

/** Algorithms for basic geometrical computations in 3D */
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
     * @param point1 first point
     * @param point2 second point
     * @return distance between the points
     */
    public static double distance(Point3D point1, Point3D point2)
    {
        return Math.sqrt(
                (point2.x - point1.x) * (point2.x - point1.x) + (point2.y - point1.y) * (point2.y
                        - point1.y) + (point2.z - point1.z) * (point2.z - point1.z));
    }

    /**
     * Translates given point by given vector.
     * @param point a point
     * @param vector a translation vector
     * @return the translated point
     */
    public static Point3D translate(Point3D point, Vector3D vector)
    {
        return Point3D.of(point.x + vector.x, point.y + vector.y, point.z + vector.z);
    }

    /**
     * Reflects given point in another point.
     * @param point a point to be reflected
     * @param centre a reflection point
     * @return the reflected point
     */
    public static Point3D reflect(Point3D point, Point3D centre)
    {
        return Point3D.of(-point.x + 2 * centre.x, -point.y + 2 * centre.y,
                          -point.z + 2 * centre.z);
    }
}
