// TRÓJKA
package refhumbold.algolib.tuples;

import java.util.Objects;

public class Triple<F, S, T>
{
    protected final F first;
    protected final S second;
    protected final T third;

    public Triple(F first, S second, T third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <F, S, T> Triple<F, S, T> make(F first, S second, T third)
    {
        return new Triple<>(first, second, third);
    }

    public F getFirst()
    {
        return this.first;
    }

    public S getSecond()
    {
        return this.second;
    }

    public T getThird()
    {
        return this.third;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Triple))
            return false;

        Triple<?, ?, ?> other = (Triple<?, ?, ?>)obj;

        return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second)
            && Objects.equals(this.third, other.third);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(first, second, third);
    }

    @Override
    public String toString()
    {
        return "(" + Objects.toString(first) + ", " + Objects.toString(second) + ", "
            + Objects.toString(third) + ")";
    }
}
