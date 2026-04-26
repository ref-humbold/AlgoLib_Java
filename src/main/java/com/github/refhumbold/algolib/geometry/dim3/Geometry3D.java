package com.github.refhumbold.algolib.geometry.dim3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.github.refhumbold.algolib.geometry.GeometryComparator;

/** Algorithms for basic geometrical operations in 3D. */
public final class Geometry3D
{
    private static final GeometryComparator comparator = new GeometryComparator();

    /**
     * Immutably sorts given points by their X coordinate. Sorting is guaranteed to be stable.
     * @param points the points
     */
    public static List<Point3D> sortByX(List<Point3D> points)
    {
        return points.stream()
                     .sorted(Comparator.comparing(pt -> pt.x, comparator))
                     .collect(Collectors.toList());
    }

    /**
     * Immutably sorts given points by their Y coordinate. Sorting is guaranteed to be stable.
     * @param points the points
     */
    public static List<Point3D> sortByY(List<Point3D> points)
    {
        return points.stream()
                     .sorted(Comparator.comparing(pt -> pt.y, comparator))
                     .collect(Collectors.toList());
    }

    /**
     * Immutably sorts given points by their Z coordinate. Sorting is guaranteed to be stable.
     * @param points the points
     */
    public static List<Point3D> sortByZ(List<Point3D> points)
    {
        return points.stream()
                     .sorted(Comparator.comparing(pt -> pt.z, comparator))
                     .collect(Collectors.toList());
    }

    /**
     * Calculates distance between given points.
     * @param point1 the first point
     * @param point2 the second point
     * @return the distance between the points
     */
    public static double distance(Point3D point1, Point3D point2)
    {
        return Math.sqrt(
                (point2.x - point1.x) * (point2.x - point1.x) + (point2.y - point1.y) * (point2.y
                        - point1.y) + (point2.z - point1.z) * (point2.z - point1.z));
    }

    /**
     * Translates given point by given vector.
     * @param point the point
     * @param vector the vector of translation
     * @return the translated point
     */
    public static Point3D translate(Point3D point, Vector3D vector)
    {
        return Point3D.of(point.x + vector.x, point.y + vector.y, point.z + vector.z);
    }

    /**
     * Reflects given point in another point.
     * @param point the point to be reflected
     * @param centre the point of reflection
     * @return the reflected point
     */
    public static Point3D reflect(Point3D point, Point3D centre)
    {
        return Point3D.of(-point.x + 2 * centre.x, -point.y + 2 * centre.y,
                -point.z + 2 * centre.z);
    }
}
