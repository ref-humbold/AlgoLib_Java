package algolib.geometry;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Structure of geometric vector */
public class Vector
{
    private final double[] coordinates;

    public Vector(double... coordinates)
    {
        if(coordinates.length == 0)
            throw new IllegalArgumentException("Empty coordinates array");

        this.coordinates = coordinates;
    }

    public static Vector between(Point begin, Point end)
    {
        int vectorDims = Math.max(begin.dims(), end.dims());
        Point newBegin = begin.project(vectorDims);
        Point newEnd = end.project(vectorDims);

        return new Vector(IntStream.rangeClosed(1, vectorDims)
                                   .mapToDouble(i -> newEnd.dim(i) - newBegin.dim(i))
                                   .toArray());
    }

    public static double dot(Vector v1, Vector v2)
    {
        int newDims = Math.max(v1.coordinates.length, v2.coordinates.length);
        double[] coordinates1 = v1.projectCoordinates(newDims);
        double[] coordinates2 = v2.projectCoordinates(newDims);

        return IntStream.range(0, newDims)
                        .mapToDouble(i -> coordinates1[i] * coordinates2[i])
                        .sum();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Vector))
            return false;

        Vector other = (Vector)obj;

        return Arrays.equals(coordinates, other.coordinates);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(coordinates, 0x995ee33);
    }

    @Override
    public String toString()
    {
        return "[" + Arrays.stream(coordinates)
                           .mapToObj(Double::toString)
                           .collect(Collectors.joining(", ")) + "]";
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

    public double length()
    {
        return Math.sqrt(Arrays.stream(coordinates).map(c -> c * c).sum());
    }

    public Vector project(int dimensions)
    {
        if(dimensions <= 0)
            throw new IllegalArgumentException("Dimensions count has to be positive");

        return dimensions == coordinates.length ? this : new Vector(projectCoordinates(dimensions));
    }

    public Vector add(Vector v)
    {
        int newDims = Math.max(coordinates.length, v.coordinates.length);
        double[] coordinates1 = projectCoordinates(newDims);
        double[] coordinates2 = v.projectCoordinates(newDims);

        return new Vector(IntStream.range(0, newDims)
                                   .mapToDouble(i -> coordinates1[i] + coordinates2[i])
                                   .toArray());
    }

    public Vector subtract(Vector v)
    {
        int newDims = Math.max(coordinates.length, v.coordinates.length);
        double[] coordinates1 = projectCoordinates(newDims);
        double[] coordinates2 = v.projectCoordinates(newDims);

        return new Vector(IntStream.range(0, newDims)
                                   .mapToDouble(i -> coordinates1[i] - coordinates2[i])
                                   .toArray());
    }

    public Vector multiply(double c)
    {
        return new Vector(Arrays.stream(coordinates).map(coord -> coord * c).toArray());
    }

    public Vector divide(double c)
    {
        if(c == 0)
            throw new ArithmeticException("Division by zero");

        return new Vector(Arrays.stream(coordinates).map(coord -> coord / c).toArray());
    }

    private double[] projectCoordinates(int dimensions)
    {
        return dimensions == coordinates.length ? coordinates
                                                : Arrays.copyOf(coordinates, dimensions);
    }
}


