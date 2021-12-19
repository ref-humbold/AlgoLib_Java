package algolib.tuples;

import java.util.Comparator;
import java.util.Objects;

/** Structure of comparable pair */
public final class ComparablePair<F extends Comparable<? super F>, S extends Comparable<? super S>>
        extends Pair<F, S>
        implements Comparable<Pair<F, S>>
{
    private ComparablePair(F first, S second)
    {
        super(first, second);
    }

    public static <F extends Comparable<F>, S extends Comparable<S>> ComparablePair<F, S> of(
            F first, S second)
    {
        return new ComparablePair<>(first, second);
    }

    public static <F extends Comparable<F>, S extends Comparable<S>> ComparablePair<F, S> of(
            Pair<F, S> pair)
    {
        return new ComparablePair<>(pair.first, pair.second);
    }

    @Override
    public int compareTo(Pair<F, S> p)
    {
        int comparedFirst =
                Objects.compare(first, p.first, Comparator.nullsFirst(Comparator.naturalOrder()));

        if(comparedFirst != 0)
            return comparedFirst;

        return Objects.compare(second, p.second, Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
