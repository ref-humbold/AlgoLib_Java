// PORÓWNYWALNA PARA
package refhumbold.algolib.tuples;

public final class ComparablePair<F extends Comparable<? super F>, S extends Comparable<? super S>>
    extends AbstractPair<F, S>
    implements Comparable<Pair<F, S>>
{
    public ComparablePair(F first, S second)
    {
        super(first, second);
    }

    public static <F extends Comparable<F>, S extends Comparable<S>> ComparablePair<F, S> make(
        F first, S second)
    {
        return new ComparablePair<>(first, second);
    }

    public static <F extends Comparable<F>, S extends Comparable<S>> ComparablePair<F, S> from(
        Pair<F, S> pair)
    {
        return new ComparablePair<>(pair.getFirst(), pair.getSecond());
    }

    @Override
    public int compareTo(Pair<F, S> p)
    {
        if(first == null)
            return p.getFirst() == null ? 0 : -1;

        int comparedFirst = first.compareTo(p.getFirst());

        if(comparedFirst != 0)
            return comparedFirst;

        if(second == null)
            return p.getSecond() == null ? 0 : -1;

        return second.compareTo(p.getSecond());
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
