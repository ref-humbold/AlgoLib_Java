package algolib.geometry;

import java.util.Arrays;
import java.util.stream.Collectors;

/** Structure of geometric point */
public final class Point
{
    private final double[] coordinates;

    private Point(double[] coordinates)
    {
        if(coordinates.length == 0)
            throw new IllegalArgumentException("Empty coordinates array");

        this.coordinates = coordinates;
    }

    public static Point of(double... coordinates)
    {
        return new Point(coordinates);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Point))
            return false;

        Point other = (Point)obj;

        return Arrays.equals(coordinates, other.coordinates);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(coordinates);
    }

    @Override
    public String toString()
    {
        return Arrays.stream(coordinates)
                     .mapToObj(Double::toString)
                     .collect(Collectors.joining(", ", "[", "]"));
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
                                                : Point.of(Arrays.copyOf(coordinates, dimensions));
    }
}
