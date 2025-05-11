package algolib.geometry.dim2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/** Algorithm for convex hull in 2D (Graham's scan). */
public final class ConvexHull
{
    /**
     * Computes convex hull of given points.
     * @param points the points
     * @return the points in the convex hull
     */
    public static List<Point2D> findConvexHull(List<Point2D> points)
    {
        if(points.size() < 3)
            return List.of();

        Point2D minPoint = points.stream()
                                 .min(Comparator.comparingDouble((Point2D pt) -> pt.y)
                                                .thenComparingDouble(pt -> pt.x))
                                 .orElseThrow();
        Vector2D moving = Vector2D.between(Point2D.of(0, 0), minPoint);
        List<Point2D> anglePoints = points.stream()
                                          .map(pt -> Geometry2D.translate(pt, moving.negate()))
                                          .collect(Collectors.toCollection(ArrayList::new));

        Geometry2D.sortByAngle(anglePoints);

        List<Point2D> hull = new ArrayList<>();

        for(Point2D pt : anglePoints)
        {
            while(hull.size() > 1
                    && crossProduct(hull.get(hull.size() - 2), hull.get(hull.size() - 1), pt) >= 0)
                hull.remove(hull.size() - 1);

            hull.add(pt);
        }

        return hull.stream().map(pt -> Geometry2D.translate(pt, moving)).toList();
    }

    private static double crossProduct(Point2D pt1, Point2D pt2, Point2D pt3)
    {
        return Vector2D.area(Vector2D.between(pt2, pt1), Vector2D.between(pt2, pt3));
    }
}
