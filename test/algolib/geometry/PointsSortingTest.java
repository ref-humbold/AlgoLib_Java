package algolib.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PointsSortingTest
{
    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void angleSort()
    {
        List<Point2D> sequence = Arrays.asList(Point2D.make(0.0, 0.0), Point2D.make(-2.0, -3.0),
                                               Point2D.make(-3.0, -2.0), Point2D.make(3.0, -2.0),
                                               Point2D.make(-2.0, 3.0), Point2D.make(3.0, 2.0),
                                               Point2D.make(2.0, -3.0), Point2D.make(2.0, 3.0),
                                               Point2D.make(-3.0, 2.0));

        PointsSorting.angleSort(sequence);

        Assertions.assertArrayEquals(
                new Object[]{Point2D.make(0.0, 0.0), Point2D.make(3.0, 2.0), Point2D.make(2.0, 3.0),
                             Point2D.make(-2.0, 3.0), Point2D.make(-3.0, 2.0),
                             Point2D.make(-3.0, -2.0), Point2D.make(-2.0, -3.0),
                             Point2D.make(2.0, -3.0), Point2D.make(3.0, -2.0)}, sequence.toArray());
    }

    @Test
    public void angleSort_WhenAllEqual()
    {
        List<Point2D> sequence = Arrays.asList(Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0),
                                               Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0),
                                               Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0),
                                               Point2D.make(1.0, 2.0));

        PointsSorting.angleSort(sequence);

        Assertions.assertArrayEquals(
                new Object[]{Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0),
                             Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0),
                             Point2D.make(1.0, 2.0)}, sequence.toArray());
    }

    @Test
    public void angleSort_WhenEmptyList()
    {
        List<Point2D> sequence = new ArrayList<>();

        PointsSorting.angleSort(sequence);

        Assertions.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void sortByX()
    {
        List<Point2D> sequence = Arrays.asList(Point2D.make(0.0, 0.0), Point2D.make(-2.0, -3.0),
                                               Point2D.make(-3.0, -2.0), Point2D.make(3.0, -2.0),
                                               Point2D.make(-2.0, 3.0), Point2D.make(3.0, 2.0),
                                               Point2D.make(2.0, -3.0), Point2D.make(2.0, 3.0),
                                               Point2D.make(-3.0, 2.0));

        PointsSorting.sortByX(sequence);

        Assertions.assertArrayEquals(new Object[]{Point2D.make(-3.0, -2.0), Point2D.make(-3.0, 2.0),
                                                  Point2D.make(-2.0, -3.0), Point2D.make(-2.0, 3.0),
                                                  Point2D.make(0.0, 0.0), Point2D.make(2.0, -3.0),
                                                  Point2D.make(2.0, 3.0), Point2D.make(3.0, -2.0),
                                                  Point2D.make(3.0, 2.0)}, sequence.toArray());
    }

    @Test
    public void sortByY()
    {
        List<Point2D> sequence = Arrays.asList(Point2D.make(0.0, 0.0), Point2D.make(-2.0, -3.0),
                                               Point2D.make(-3.0, -2.0), Point2D.make(3.0, -2.0),
                                               Point2D.make(-2.0, 3.0), Point2D.make(3.0, 2.0),
                                               Point2D.make(2.0, -3.0), Point2D.make(2.0, 3.0),
                                               Point2D.make(-3.0, 2.0));

        PointsSorting.sortByY(sequence);

        Assertions.assertArrayEquals(new Object[]{Point2D.make(-2.0, -3.0), Point2D.make(2.0, -3.0),
                                                  Point2D.make(-3.0, -2.0), Point2D.make(3.0, -2.0),
                                                  Point2D.make(0.0, 0.0), Point2D.make(-3.0, 2.0),
                                                  Point2D.make(3.0, 2.0), Point2D.make(-2.0, 3.0),
                                                  Point2D.make(2.0, 3.0)}, sequence.toArray());
    }
}
