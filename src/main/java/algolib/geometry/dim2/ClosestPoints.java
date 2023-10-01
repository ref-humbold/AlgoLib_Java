package algolib.geometry.dim2;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import algolib.tuples.Pair;

/** Algorithm for searching closest points. */
public final class ClosestPoints
{
    /**
     * Searches for the pair of closest points among given points.
     * @param points the points
     * @return the pair of closest points
     * @throws NoSuchElementException if no points specified
     */
    public static Pair<Point2D, Point2D> findClosestPoints(List<Point2D> points)
    {
        Objects.requireNonNull(points, "Sequence is null");

        if(points.isEmpty())
            throw new NoSuchElementException("Sequence contains no points");

        List<Point2D> pointsX = new ArrayList<>(points);
        List<Point2D> pointsY = new ArrayList<>(points);

        Geometry2D.sortByY(pointsX);
        Geometry2D.sortByX(pointsX);
        Geometry2D.sortByY(pointsY);

        return searchClosest(pointsX, pointsY, 0, pointsX.size());
    }

    // Searches for closest points among three of them.
    private static Pair<Point2D, Point2D> searchThree(
            Point2D point1, Point2D point2, Point2D point3)
    {
        double distance12 = Geometry2D.distance(point1, point2);
        double distance23 = Geometry2D.distance(point2, point3);
        double distance31 = Geometry2D.distance(point3, point1);

        if(distance12 <= distance23 && distance12 <= distance31)
            return Pair.of(point1, point2);

        if(distance23 <= distance12 && distance23 <= distance31)
            return Pair.of(point2, point3);

        return Pair.of(point1, point3);
    }

    // Searches for closest points inside a belt of given width.
    // The resulting distance should not be less than belt width.
    private static Optional<Pair<Point2D, Point2D>> checkBelt(
            List<Point2D> pointsY, double middleX, double beltWidth)
    {
        Optional<Pair<Point2D, Point2D>> closestPoints = Optional.empty();
        List<Integer> beltPoints = new ArrayList<>();
        double minDistance = beltWidth;

        for(int i = 0; i < pointsY.size(); ++i)
            if(pointsY.get(i).x >= middleX - beltWidth && pointsY.get(i).x <= middleX + beltWidth)
                beltPoints.add(i);

        for(int i = 1; i < beltPoints.size(); ++i)
            for(int j = i + 1; j < beltPoints.size(); ++j)
            {
                Point2D pt1 = pointsY.get(beltPoints.get(i));
                Point2D pt2 = pointsY.get(beltPoints.get(j));

                if(pt2.y > pt1.y + beltWidth)
                    break;

                if((pt1.x <= middleX && pt2.x >= middleX) || (pt1.x > middleX && pt2.x <= middleX))
                {
                    double currentDistance = Geometry2D.distance(pt1, pt2);

                    if(currentDistance < minDistance)
                    {
                        minDistance = currentDistance;
                        closestPoints = Optional.of(Pair.of(pt1, pt2));
                    }
                }
            }

        return closestPoints;
    }

    // Searches for closest points in given sublist of points.
    // Points are given sorted by X coordinate and by Y coordinate.
    private static Pair<Point2D, Point2D> searchClosest(
            List<Point2D> pointsX, List<Point2D> pointsY, int indexBegin, int indexEnd)
    {
        if(indexEnd - indexBegin <= 2)
            return Pair.of(pointsX.get(indexBegin), pointsX.get(indexEnd - 1));

        if(indexEnd - indexBegin == 3)
            return searchThree(pointsX.get(indexBegin), pointsX.get(indexBegin + 1),
                               pointsX.get(indexBegin + 2));

        int indexMiddle = (indexBegin + indexEnd) / 2;
        List<Point2D> closetsYL = new ArrayList<>();
        List<Point2D> closetsYR = new ArrayList<>();

        for(Point2D pt : pointsY)
            if(pt.x < pointsX.get(indexMiddle).x
                       || pt.x == pointsX.get(indexMiddle).x && pt.y < pointsX.get(indexMiddle).y)
                closetsYL.add(pt);
            else
                closetsYR.add(pt);

        Pair<Point2D, Point2D> closestL =
                searchClosest(pointsX, closetsYL, indexBegin, indexMiddle);
        Pair<Point2D, Point2D> closestR = searchClosest(pointsX, closetsYR, indexMiddle, indexEnd);
        Pair<Point2D, Point2D> closestPoints =
                Geometry2D.distance(closestL.first, closestL.second) <= Geometry2D.distance(
                        closestR.first, closestR.second) ? closestL : closestR;
        Optional<Pair<Point2D, Point2D>> beltPoints = checkBelt(pointsY, pointsX.get(indexMiddle).x,
                                                                Geometry2D.distance(
                                                                        closestPoints.first,
                                                                        closestPoints.second));

        return beltPoints.orElse(closestPoints);
    }
}
