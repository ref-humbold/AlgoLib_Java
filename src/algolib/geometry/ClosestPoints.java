// Algorithm for pair of closest points on a plane
package algolib.geometry;

import java.util.ArrayList;
import java.util.List;

import algolib.tuples.Pair;

class ClosestPoints
{
    /**
     * Searches for a pair closest of points among specified points.
     * @param points a list of points
     * @return pair of closest points
     */
    public Pair<Point2D, Point2D> find(List<Point2D> points)
    {
        List<Point2D> pointsX = new ArrayList<>(points);
        List<Point2D> pointsY = new ArrayList<>(points);

        PointsSorting.sortByX(pointsX);
        PointsSorting.sortByY(pointsY);

        return searchClosest(pointsX, pointsY, 0, -1);
    }

    private double distance(Point2D pt1, Point2D pt2)
    {
        double dx = pt1.x - pt2.x;
        double dy = pt1.y - pt2.y;

        return dx * dx + dy * dy;
    }

    // Finds closest pair of points among three of them.
    private Pair<Point2D, Point2D> searchThree(List<Point2D> pointsX, int index_begin,
                                               int index_end)
    {
        int index_middle = index_begin + 1;
        double distance12 = distance(pointsX.get(index_begin), pointsX.get(index_middle));
        double distance23 = distance(pointsX.get(index_middle), pointsX.get(index_end));
        double distance31 = distance(pointsX.get(index_begin), pointsX.get(index_end));

        if(distance12 <= distance23 && distance12 <= distance31)
            return Pair.make(pointsX.get(index_begin), pointsX.get(index_middle));

        if(distance23 <= distance12 && distance23 <= distance31)
            return Pair.make(pointsX.get(index_middle), pointsX.get(index_end));

        return Pair.make(pointsX.get(index_begin), pointsX.get(index_end));
    }

    // Finds closest pair inside a belt of specified width.
    // The resulting distance should not be less than belt width.
    private Pair<Point2D, Point2D> checkBelt(List<Point2D> pointsY, double middleX,
                                             double beltWidth)
    {
        Pair<Point2D, Point2D> closestPoints = null;
        double minDistance = beltWidth;
        List<Integer> beltPoints = new ArrayList<>();

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
                    double actual_distance = distance(pt1, pt2);

                    if(actual_distance < minDistance)
                    {
                        minDistance = actual_distance;
                        closestPoints = Pair.make(pt1, pt2);
                    }
                }
            }

        return closestPoints;
    }

    // Searches for a pair of closest points in specified sublist of points.
    // Points are specified sorted by X coordinate and by Y coordinate.
    private Pair<Point2D, Point2D> searchClosest(List<Point2D> pointsX, List<Point2D> pointsY,
                                                 int index_begin, int index_end)
    {
        index_begin = (index_begin + pointsX.size()) % pointsX.size();
        index_end = (index_end + pointsX.size()) % pointsX.size();

        if(index_end - index_begin == 1)
            return Pair.make(pointsX.get(index_begin), pointsX.get(index_end));

        if(index_end - index_begin == 2)
            return searchThree(pointsX, index_begin, index_end);

        int index_middle = (index_begin + index_end) / 2;
        double middleX = (pointsX.get(index_middle).x + pointsX.get(index_middle + 1).x) / 2;
        List<Point2D> pointsYL = new ArrayList<>();
        List<Point2D> pointsYR = new ArrayList<>();

        for(Point2D pt : pointsY)
            if(pt.x <= index_middle)
                pointsYL.add(pt);
            else
                pointsYR.add(pt);

        Pair<Point2D, Point2D> closestL =
                searchClosest(pointsX, pointsYL, index_begin, index_middle);
        Pair<Point2D, Point2D> closestR =
                searchClosest(pointsX, pointsYR, index_middle + 1, index_end);
        Pair<Point2D, Point2D> closestPoints =
                distance(closestL.first, closestL.second) <= distance(closestR.first,
                                                                      closestR.second) ? closestL
                                                                                       : closestR;
        Pair<Point2D, Point2D> beltPoints =
                checkBelt(pointsY, middleX, distance(closestPoints.first, closestPoints.second));

        return beltPoints != null ? beltPoints : closestPoints;
    }
}
