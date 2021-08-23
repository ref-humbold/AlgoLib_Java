package algolib.geometry.dim2;

import java.util.List;

/** Algorithms for basic geometrical computations in 2D */
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
     * Sorting is guaranteed to be stable.
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

    /**
     * Counts the distance between given points.
     * @param point1 first point
     * @param point2 second point
     * @return distance between the points
     */
    public static double distance(Point2D point1, Point2D point2)
    {
        return Math.sqrt(
                (point2.x - point1.x) * (point2.x - point1.x) + (point2.y - point1.y) * (point2.y
                        - point1.y));
    }

    /**
     * Translates given point by given vector.
     * @param point a point
     * @param vector a translation vector
     * @return the translated point
     */
    public static Point2D translate(Point2D point, Vector2D vector)
    {
        return Point2D.of(point.x + vector.x, point.y + vector.y);
    }

    /**
     * Reflects given point in another point.
     * @param point a point to be reflected
     * @param centre a reflection point
     * @return the reflected point
     */
    public static Point2D reflect(Point2D point, Point2D centre)
    {
        return Point2D.of(-point.x + 2 * centre.x, -point.y + 2 * centre.y);
    }
}
