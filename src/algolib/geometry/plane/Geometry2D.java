package algolib.geometry.plane;

import java.util.List;

/** Basic geometric operations in 2 dimensions */
public final class Geometry2D
{
    /**
     * Mutably sorts points by their X coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sortByX(List<Point2D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.x, pt2.x));
    }

    /**
     * Mutably sorts points by their Y coordinate. Sorting is guaranteed to be stable.
     * @param points a list of points
     */
    public static void sortByY(List<Point2D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.y, pt2.y));
    }

    /**
     * Mutably sorts points by their polar coordinates. First sorts by angle, then by radius.
     * @param points a list of points
     */
    public static void sortByAngle(List<Point2D> points)
    {
        points.sort((pt1, pt2) -> {
            if(pt1.angleDeg() == pt2.angleDeg())
                return Double.compare(pt1.radius(), pt2.radius());
            else
                return Double.compare(pt1.angleDeg(), pt2.angleDeg());
        });
    }

    public static double distance(Point2D p1, Point2D p2)
    {
        return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
    }

    public Point2D translate(Point2D p, Vector2D v)
    {
        return Point2D.of(p.x + v.x, p.y + v.y);
    }
}
