// Graham's algorithm for convex hull on a plane.
package algolib.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ConvexHull
{
    public static List<Point2D> find(List<Point2D> points)
    {
        List<Point2D> hull = new ArrayList<>();
        List<Point2D> sorted = new ArrayList<>(points);

        PointsSorting.sortByX(sorted);
        hull.add(sorted.get(0));
        hull.add(sorted.get(1));

        for(int i = 2; i < sorted.size(); ++i)
            addPoint(sorted.get(i), hull, 1);

        int upper_size = hull.size();

        Collections.reverse(sorted);

        for(int i = sorted.size() - 2; i >= 0; --i)
            addPoint(sorted.get(i), hull, upper_size);

        hull.remove(hull.size() - 1);

        return hull;
    }

    private static void addPoint(Point2D point, List<Point2D> hull, int minSize)
    {
        while(hull.size() > minSize
                && crossProduct(hull.get(hull.size() - 2), hull.get(hull.size() - 1), point) <= 0)
            hull.remove(hull.size() - 1);

        hull.add(point);
    }

    private static double crossProduct(Point2D pt1, Point2D pt2, Point2D pt3)
    {
        return (pt1.x - pt2.x) * (pt3.y - pt2.y) - (pt3.x - pt2.x) * (pt1.y - pt2.y);
    }
}
