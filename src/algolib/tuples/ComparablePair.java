// Structure of comparable pair
package algolib.tuples;

public final class ComparablePair<F extends Comparable<? super F>, S extends Comparable<? super S>>
        extends Pair<F, S>
        implements Comparable<Pair<F, S>>
{
    public ComparablePair(F first, S second)
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
        if(first == null)
            return p.first == null ? 0 : -1;

        int comparedFirst = first.compareTo(p.first);

        if(comparedFirst != 0)
            return comparedFirst;

        if(second == null)
            return p.second == null ? 0 : -1;

        return second.compareTo(p.second);
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
