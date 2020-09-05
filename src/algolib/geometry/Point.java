package algolib.geometry;

import java.util.Arrays;

/** Structure of geometric point */
public class Point
{
    private final double[] coordinates;

    public Point(double... coordinates)
    {
        if(coordinates.length == 0)
            throw new IllegalArgumentException("Empty coordinates array");

        this.coordinates = coordinates;
    }

    public int dims()
    {
        return coordinates.length;
    }

    public double dim(int i)
    {
        if(i <= 0 || i > coordinates.length)
            throw new IndexOutOfBoundsException(
                    String.format("Coordinate index has to be between 1 and %d",
                                  coordinates.length));

        return coordinates[i - 1];
    }

    public double radius()
    {
        return Math.sqrt(Arrays.stream(coordinates).map(c -> c * c).sum());
    }

    public Point project(int dimensions)
    {
        if(dimensions <= 0)
            throw new IllegalArgumentException("Dimensions count has to be positive");

        return dimensions == coordinates.length ? this
                                                : new Point(Arrays.copyOf(coordinates, dimensions));
    }
}
