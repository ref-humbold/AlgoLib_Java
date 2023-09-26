package algolib.tuples;

import java.util.Objects;

/** Structure of triple */
public class Triple<F, S, T>
{
    public final F first;
    public final S second;
    public final T third;

    protected Triple(F first, S second, T third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <F, S, T> Triple<F, S, T> of(F first, S second, T third)
    {
        return new Triple<>(first, second, third);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Triple))
            return false;

        Triple<?, ?, ?> other = (Triple<?, ?, ?>)obj;

        return Objects.equals(first, other.first) && Objects.equals(second, other.second)
                       && Objects.equals(third, other.third);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(first, second, third);
    }

    @Override
    public String toString()
    {
        return "(%s, %s, %s)".formatted(first, second, third);
    }
}
