package refhumbold.algolib.tuples;

import java.util.Objects;

abstract class AbstractPair<F, S>
    implements Pair<F, S>
{
    protected final F first;
    protected final S second;

    public AbstractPair(F first, S second)
    {
        this.first = first;
        this.second = second;
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
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof AbstractPair))
            return false;

        AbstractPair<?, ?> other = (AbstractPair<?, ?>)obj;

        return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(first, second);
    }

    @Override
    public String toString()
    {
        return "(" + Objects.toString(first) + ", " + Objects.toString(second) + ")";
    }
}
