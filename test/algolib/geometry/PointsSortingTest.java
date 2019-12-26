package algolib.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PointsSortingTest
{
    @Before
    public void setUp()
    {
    }

    @After
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

        Assert.assertArrayEquals(
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

        Assert.assertArrayEquals(
                new Object[]{Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0),
                             Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0), Point2D.make(1.0, 2.0),
                             Point2D.make(1.0, 2.0)}, sequence.toArray());
    }

    @Test
    public void angleSort_WhenEmptyList()
    {
        List<Point2D> sequence = new ArrayList<>();

        PointsSorting.angleSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
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

        Assert.assertArrayEquals(new Object[]{Point2D.make(-3.0, -2.0), Point2D.make(-3.0, 2.0),
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

        Assert.assertArrayEquals(new Object[]{Point2D.make(-2.0, -3.0), Point2D.make(2.0, -3.0),
                                              Point2D.make(-3.0, -2.0), Point2D.make(3.0, -2.0),
                                              Point2D.make(0.0, 0.0), Point2D.make(-3.0, 2.0),
                                              Point2D.make(3.0, 2.0), Point2D.make(-2.0, 3.0),
                                              Point2D.make(2.0, 3.0)}, sequence.toArray());
    }
}
