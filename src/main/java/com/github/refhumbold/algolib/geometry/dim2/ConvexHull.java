package com.github.refhumbold.algolib.geometry.dim2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/** Algorithms for convex hull in 2D. */
public final class ConvexHull
{
    /**
     * Computes convex hull of given points using Andrew's monotone chain.
     * @param points the points
     * @return the points in the convex hull
     */
    public static List<Point2D> findAndrewConvexHull(List<Point2D> points)
    {
        if(points.size() < 3)
            return List.of();

        List<Point2D> sorted = Geometry2D.sortByX(points);
        List<Point2D> lowerHull = collectHull(sorted);
        List<Point2D> upperHull = collectHull(sorted.reversed());

        lowerHull.removeLast();
        upperHull.removeLast();
        return Stream.concat(lowerHull.stream(), upperHull.stream()).toList();
    }

    /**
     * Computes convex hull of given points using Graham's scan.
     * @param points the points
     * @return the points in the convex hull
     */
    public static List<Point2D> findGrahamConvexHull(List<Point2D> points)
    {
        if(points.size() < 3)
            return List.of();

        Point2D minPoint = points.stream()
                                 .min(Comparator.comparingDouble((Point2D pt) -> pt.y)
                                                .thenComparingDouble(pt -> pt.x))
                                 .orElseThrow();
        Vector2D moving = Vector2D.between(minPoint, Point2D.ZERO);
        List<Point2D> anglePoints = Geometry2D.sortByAngle(
                points.stream().map(pt -> Geometry2D.translate(pt, moving)).toList());

        List<Point2D> hull = collectHull(anglePoints);

        return hull.stream().map(pt -> Geometry2D.translate(pt, moving.negate())).toList();
    }

    private static List<Point2D> collectHull(List<Point2D> points)
    {
        List<Point2D> hull = new ArrayList<>();

        for(Point2D pt : points)
        {
            while(hull.size() > 1
                    && crossProduct(hull.get(hull.size() - 2), hull.getLast(), pt) >= 0)
                hull.removeLast();

            hull.add(pt);
        }
        return hull;
    }

    private static double crossProduct(Point2D pt1, Point2D pt2, Point2D pt3)
    {
        return Vector2D.area(Vector2D.between(pt2, pt1), Vector2D.between(pt2, pt3));
    }
}
