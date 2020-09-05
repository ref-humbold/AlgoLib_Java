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

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Vector))
            return false;

        Vector other = (Vector)obj;

        if(coordinates.length != other.coordinates.length)
            return false;

        return IntStream.range(0, coordinates.length)
                        .allMatch(i -> coordinates[i] == other.coordinates[i]);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(coordinates, 0x935fe66);
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

        return dimensions == coordinates.length ? this : new Vector(
                Arrays.copyOf(coordinates, dimensions));
    }

    public Vector add(Vector v)
    {
        int newDims = Math.max(coordinates.length, v.coordinates.length);
        Vector v1 = project(newDims);
        Vector v2 = v.project(newDims);

        return new Vector(IntStream.range(0, newDims)
                                   .mapToDouble(i -> v1.coordinates[i] + v2.coordinates[i])
                                   .toArray());
    }

    public Vector subtract(Vector v)
    {
        int newDims = Math.max(coordinates.length, v.coordinates.length);
        Vector v1 = project(newDims);
        Vector v2 = v.project(newDims);

        return new Vector(IntStream.range(0, newDims)
                                   .mapToDouble(i -> v1.coordinates[i] - v2.coordinates[i])
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

    public double dot(Vector v)
    {
        int newDims = Math.max(coordinates.length, v.coordinates.length);
        Vector v1 = project(newDims);
        Vector v2 = v.project(newDims);

        return IntStream.range(0, newDims)
                        .mapToDouble(i -> v1.coordinates[i] * v2.coordinates[i])
                        .sum();
    }
}


