// TRÓJKA
package ref_humbold.algolib.tuples;

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

        if(obj == null || !(obj instanceof Triple))
            return false;

        Triple<?, ?, ?> other = (Triple<?, ?, ?>)obj;

        return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second)
            && Objects.equals(this.third, other.third);
    }

    @Override
    public int hashCode()
    {
        int prime = 37;
        int result = 1;

        result = prime * result + (first == null ? 0 : first.hashCode());
        result = prime * result + (second == null ? 0 : second.hashCode());
        result = prime * result + (third == null ? 0 : third.hashCode());

        return result;
    }

    @Override
    public String toString()
    {
        String firstString = first == null ? "null" : first.toString();
        String secondString = second == null ? "null" : second.toString();
        String thirdString = third == null ? "null" : third.toString();

        return "(" + firstString + ", " + secondString + ", " + thirdString + ")";
    }
}
