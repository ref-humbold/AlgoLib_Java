package refhumbold.algolib.tuples;

import java.util.Objects;

class AbstractTriple<F, S, T>
    implements Triple<F, S, T>
{
    protected final F first;
    protected final S second;
    protected final T third;

    public AbstractTriple(F first, S second, T third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public F getFirst()
    {
        return this.first;
    }

    @Override
    public S getSecond()
    {
        return this.second;
    }

    @Override
    public T getThird()
    {
        return this.third;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof AbstractTriple))
            return false;

        AbstractTriple<?, ?, ?> other = (AbstractTriple<?, ?, ?>)obj;

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
