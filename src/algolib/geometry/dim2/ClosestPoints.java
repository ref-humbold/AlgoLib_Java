package algolib.geometry.dim2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import algolib.tuples.Pair;

/** Algorithm for searching closest points */
public final class ClosestPoints
{
    /**
     * Searches for closest of points among given points.
     * @param points list of points
     * @return pair of the closest points
     */
    public static Pair<Point2D, Point2D> findClosestPoints(List<Point2D> points)
    {
        List<Point2D> pointsX = new ArrayList<>(points);
        List<Point2D> pointsY = new ArrayList<>(points);

        Geometry2D.sortByX(pointsX);
        Geometry2D.sortByY(pointsY);

        return searchClosest(pointsX, pointsY, 0, -1);
    }

    // Finds closest pair of points among three of them.
    private static Pair<Point2D, Point2D> searchThree(List<Point2D> pointsX, int indexBegin,
                                                      int indexEnd)
    {
        int indexMiddle = indexBegin + 1;
        double distance12 = Geometry2D.distance(pointsX.get(indexBegin), pointsX.get(indexMiddle));
        double distance23 = Geometry2D.distance(pointsX.get(indexMiddle), pointsX.get(indexEnd));
        double distance31 = Geometry2D.distance(pointsX.get(indexBegin), pointsX.get(indexEnd));

        if(distance12 <= distance23 && distance12 <= distance31)
            return Pair.of(pointsX.get(indexBegin), pointsX.get(indexMiddle));

        if(distance23 <= distance12 && distance23 <= distance31)
            return Pair.of(pointsX.get(indexMiddle), pointsX.get(indexEnd));

        return Pair.of(pointsX.get(indexBegin), pointsX.get(indexEnd));
    }

    // Finds closest pair inside a belt of given width.
    // The resulting distance should not be less than belt width.
    private static Optional<Pair<Point2D, Point2D>> checkBelt(List<Point2D> pointsY, double middleX,
                                                              double beltWidth)
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

                if((pt1.x <= middleX && pt2.x > middleX) || (pt1.x > middleX && pt2.x <= middleX))
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

    // Searches for a pair of closest points in given sublist of points.
    // Points are given sorted by X coordinate and by Y coordinate.
    // (indexBegin & indexEnd inclusive)
    private static Pair<Point2D, Point2D> searchClosest(List<Point2D> pointsX,
                                                        List<Point2D> pointsY, int indexBegin,
                                                        int indexEnd)
    {
        indexBegin = (indexBegin + pointsX.size()) % pointsX.size();
        indexEnd = (indexEnd + pointsX.size()) % pointsX.size();

        if(indexEnd - indexBegin <= 1)
            return Pair.of(pointsX.get(indexBegin), pointsX.get(indexEnd));

        if(indexEnd - indexBegin == 2)
            return searchThree(pointsX, indexBegin, indexEnd);

        int indexMiddle = (indexBegin + indexEnd) / 2;
        double middleX = (pointsX.get(indexMiddle).x + pointsX.get(indexMiddle + 1).x) / 2;
        List<Point2D> pointsYL = new ArrayList<>();
        List<Point2D> pointsYR = new ArrayList<>();

        for(Point2D pt : pointsY)
            if(pt.x <= indexMiddle)
                pointsYL.add(pt);
            else
                pointsYR.add(pt);

        Pair<Point2D, Point2D> closestL = searchClosest(pointsX, pointsYL, indexBegin, indexMiddle);
        Pair<Point2D, Point2D> closestR =
                searchClosest(pointsX, pointsYR, indexMiddle + 1, indexEnd);
        Pair<Point2D, Point2D> closestPoints =
                Geometry2D.distance(closestL.first, closestL.second) <= Geometry2D.distance(
                        closestR.first, closestR.second) ? closestL : closestR;
        Optional<Pair<Point2D, Point2D>> beltPoints = checkBelt(pointsY, middleX,
                                                                Geometry2D.distance(
                                                                        closestPoints.first,
                                                                        closestPoints.second));

        return beltPoints.orElse(closestPoints);
    }
}
