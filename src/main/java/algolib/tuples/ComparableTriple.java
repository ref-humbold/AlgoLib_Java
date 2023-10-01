package algolib.tuples;

import java.util.Comparator;
import java.util.Objects;

/** Structure of comparable triple. */
public final class ComparableTriple<F extends Comparable<? super F>, S extends Comparable<? super S>, T extends Comparable<? super T>>
        extends Triple<F, S, T>
        implements Comparable<Triple<F, S, T>>
{
    private ComparableTriple(F first, S second, T third)
    {
        super(first, second, third);
    }

    public static <F extends Comparable<F>, S extends Comparable<S>, T extends Comparable<T>> ComparableTriple<F, S, T> of(
            F first, S second, T third)
    {
        return new ComparableTriple<>(first, second, third);
    }

    public static <F extends Comparable<F>, S extends Comparable<S>, T extends Comparable<T>> ComparableTriple<F, S, T> of(
            Triple<F, S, T> triple)
    {
        return new ComparableTriple<>(triple.first, triple.second, triple.third);
    }

    @Override
    public int compareTo(Triple<F, S, T> t)
    {
        int comparedFirst =
                Objects.compare(first, t.first, Comparator.nullsFirst(Comparator.naturalOrder()));

        if(comparedFirst != 0)
            return comparedFirst;

        int comparedSecond =
                Objects.compare(second, t.second, Comparator.nullsFirst(Comparator.naturalOrder()));

        if(comparedSecond != 0)
            return comparedSecond;

        return Objects.compare(third, t.third, Comparator.nullsFirst(Comparator.naturalOrder()));
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
