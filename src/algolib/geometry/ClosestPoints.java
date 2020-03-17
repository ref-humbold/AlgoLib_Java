// Algorithm for closest pair of points on a plane.
package algolib.geometry;

import java.util.ArrayList;
import java.util.List;

import algolib.tuples.Pair;

class ClosestPoints
{
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
        double min_distance = Math.min(distance(closestL.first, closestL.second),
                                       distance(closestR.first, closestR.second));
        Pair<Point2D, Point2D> closest_points =
                distance(closestL.first, closestL.second) <= distance(closestR.first,
                                                                      closestR.second) ? closestL
                                                                                       : closestR;
        double middle_width = min_distance;
        List<Integer> middle_points = new ArrayList<>();

        for(int i = 0; i < pointsY.size(); ++i)
            if(pointsY.get(i).x >= middleX - middle_width
                    && pointsY.get(i).x <= middleX + middle_width)
                middle_points.add(i);

        for(int i = 1; i < middle_points.size(); ++i)
            for(int j = i + 1; j < middle_points.size(); ++j)
            {
                Point2D pt1 = pointsY.get(middle_points.get(i));
                Point2D pt2 = pointsY.get(middle_points.get(j));

                if(pt2.y > pt1.y + middle_width)
                    break;

                if((pt1.x <= middleX && pt2.x > middleX) || (pt1.x > middleX && pt2.x <= middleX))
                {
                    double actual_distance = distance(pt1, pt2);

                    if(actual_distance < min_distance)
                    {
                        min_distance = actual_distance;
                        closest_points = Pair.make(pt1, pt2);
                    }
                }
            }

        return closest_points;
    }
}
