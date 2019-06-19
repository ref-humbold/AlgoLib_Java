package algolib.geometry;

import java.util.List;
import java.util.Objects;

public class PointsSorting
{
    /**
     * Mutably sorts points by their polar coordinates. First sorts by angle, then by radius.
     * @param points list of points
     */
    public static void angleSort(List<Point2D> points)
    {
        Objects.requireNonNull(points, "List of points is null.");

        points.sort((pt1, pt2) -> pt1.angle() == pt2.angle() ? Double.compare(pt1.radius(),
                                                                              pt2.radius())
                                                             : Double.compare(pt1.angle(),
                                                                              pt2.angle()));
    }

    /**
     * Mutably sorts points by their cartesian coordinates.
     * First sorts by X coordinate, then by Y coordinate.
     * @param points list of points
     */
    public static void sortByX(List<Point2D> points)
    {
        Objects.requireNonNull(points, "List of points is null.");

        points.sort((pt1, pt2) -> pt1.compareTo(pt2));
    }

    /**
     * Mutably sorts points by their cartesian coordinates.
     * First sorts by Y coordinate, then by X coordinate.
     * @param points list of points
     */
    public static void sortByY(List<Point2D> points)
    {
        Objects.requireNonNull(points, "List of points is null.");

        points.sort((pt1, pt2) -> pt1.y == pt2.y ? Double.compare(pt1.x, pt2.x)
                                                 : Double.compare(pt1.y, pt2.y));
    }
}
