package algolib.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Algorithm for convex hull on a plane (monotone chain) */
public final class ConvexHull
{
    /**
     * Constructs a convex hull of given points.
     * @param points a list of points
     * @return list of hull points
     */
    public static List<Point2D> find(List<Point2D> points)
    {
        if(points.size() < 3)
            return List.of();

        List<Point2D> sorted = new ArrayList<>(points);

        PointsSorting.sort2DByX(sorted);

        List<Point2D> lowerHull = createHalfHull(sorted);

        Collections.reverse(sorted);

        List<Point2D> upperHull = createHalfHull(sorted);

        lowerHull.remove(lowerHull.size() - 1);
        upperHull.remove(upperHull.size() - 1);
        lowerHull.addAll(upperHull);
        return lowerHull;
    }

    // Creates a half of a convex hull for given points.
    private static List<Point2D> createHalfHull(List<Point2D> points)
    {
        List<Point2D> hull = new ArrayList<>();

        for(Point2D pt : points)
        {
            while(hull.size() > 1
                    && crossProduct(hull.get(hull.size() - 2), hull.get(hull.size() - 1), pt) >= 0)
                hull.remove(hull.size() - 1);

            hull.add(pt);
        }

        return hull;
    }

    private static double crossProduct(Point2D pt1, Point2D pt2, Point2D pt3)
    {
        return Vector2D.area(Vector2D.between(pt2, pt1), Vector2D.between(pt2, pt3));
    }
}
