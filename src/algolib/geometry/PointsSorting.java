package algolib.geometry;

import java.util.List;
import java.util.Objects;

public final class PointsSorting
{
    /**
     * Mutably sorts points by their X coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort2DByX(List<Point2D> points)
    {
        Objects.requireNonNull(points, "List of points is null");
        points.sort((pt1, pt2) -> Double.compare(pt1.x, pt2.x));
    }

    /**
     * Mutably sorts points by their X coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort3DByX(List<Point3D> points)
    {
        Objects.requireNonNull(points, "List of points is null");
        points.sort((pt1, pt2) -> Double.compare(pt1.x, pt2.x));
    }

    /**
     * Mutably sorts points by their Y coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort2DByY(List<Point2D> points)
    {
        Objects.requireNonNull(points, "List of points is null");
        points.sort((pt1, pt2) -> Double.compare(pt1.y, pt2.y));
    }

    /**
     * Mutably sorts points by their Y coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort3DByY(List<Point3D> points)
    {
        Objects.requireNonNull(points, "List of points is null");
        points.sort((pt1, pt2) -> Double.compare(pt1.y, pt2.y));
    }

    /**
     * Mutably sorts points by their Z coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort3DByZ(List<Point3D> points)
    {
        Objects.requireNonNull(points, "List of points is null");
        points.sort((pt1, pt2) -> Double.compare(pt1.z, pt2.z));
    }

    /**
     * Mutably sorts points by their polar coordinates. First sorts by angle, then by radius.
     * @param points a list of points
     */
    public static void sort2DByAngle(List<Point2D> points)
    {
        Objects.requireNonNull(points, "List of points is null");

        points.sort((pt1, pt2) -> {
            if(pt1.angleDeg() == pt2.angleDeg())
                return Double.compare(pt1.radius(), pt2.radius());
            else
                return Double.compare(pt1.angleDeg(), pt2.angleDeg());
        });
    }
}
