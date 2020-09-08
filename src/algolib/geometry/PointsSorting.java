package algolib.geometry;

import java.util.List;

public final class PointsSorting
{
    /**
     * Mutably sorts points by given coordinate index. Sorting is guaranteed to be stable.
     * @param i coordinate index
     * @param points a list of points
     */
    public static void sortByDim(int i, List<Point> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.dim(i), pt2.dim(i)));
    }

    /**
     * Mutably sorts points by their X coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort2DByX(List<Point2D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.x, pt2.x));
    }

    /**
     * Mutably sorts points by their X coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort3DByX(List<Point3D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.x, pt2.x));
    }

    /**
     * Mutably sorts points by their Y coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort2DByY(List<Point2D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.y, pt2.y));
    }

    /**
     * Mutably sorts points by their Y coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort3DByY(List<Point3D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.y, pt2.y));
    }

    /**
     * Mutably sorts points by their Z coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sort3DByZ(List<Point3D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.z, pt2.z));
    }

    /**
     * Mutably sorts points by their polar coordinates. First sorts by angle, then by radius.
     * @param points a list of points
     */
    public static void sort2DByAngle(List<Point2D> points)
    {
        points.sort((pt1, pt2) -> {
            if(pt1.angleDeg() == pt2.angleDeg())
                return Double.compare(pt1.radius(), pt2.radius());
            else
                return Double.compare(pt1.angleDeg(), pt2.angleDeg());
        });
    }
}
