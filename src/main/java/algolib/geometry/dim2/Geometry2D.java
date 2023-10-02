package algolib.geometry.dim2;

import java.util.List;

/** Algorithms for basic geometrical operations in 2D. */
public final class Geometry2D
{
    /**
     * Mutably sorts given points by their X coordinate. Sorting is guaranteed to be stable.
     * @param points the points
     */
    public static void sortByX(List<Point2D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.x, pt2.x));
    }

    /**
     * Mutably sorts given points by their Y coordinate. Sorting is guaranteed to be stable.
     * @param points the points
     */
    public static void sortByY(List<Point2D> points)
    {
        points.sort((pt1, pt2) -> Double.compare(pt1.y, pt2.y));
    }

    /**
     * Mutably sorts given points by their polar coordinates. First sorts by angle, then by radius.
     * Sorting is guaranteed to be stable.
     * @param points the points
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
     * Calculates the distance between given points.
     * @param point1 the first point
     * @param point2 the second point
     * @return the distance between the points
     */
    public static double distance(Point2D point1, Point2D point2)
    {
        double distanceX = point2.x - point1.x;
        double distanceY = point2.y - point1.y;

        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
     * Translates given point by given vector.
     * @param point the point
     * @param vector the vector of translation
     * @return the translated point
     */
    public static Point2D translate(Point2D point, Vector2D vector)
    {
        return Point2D.of(point.x + vector.x, point.y + vector.y);
    }

    /**
     * Reflects given point in another point.
     * @param point the point
     * @param centre the point of reflection
     * @return the reflected point
     */
    public static Point2D reflect(Point2D point, Point2D centre)
    {
        return Point2D.of(-point.x + 2 * centre.x, -point.y + 2 * centre.y);
    }
}
