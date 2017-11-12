// PORÓWNYWALNA PARA
package ref_humbold.algolib.tuples;

public class ComparablePair<F extends Comparable<F>, S extends Comparable<S>>
    extends Pair<F, S>
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
        return new ComparablePair<>(pair.first, pair.second);
    }

    @Override
    public int compareTo(Pair<F, S> p)
    {
        if(this.first == null)
            return p.first == null ? 0 : -1;

        int comparedFirst = this.first.compareTo(p.first);

        if(comparedFirst != 0)
            return comparedFirst;

        if(this.second == null)
            return p.getSecond() == null ? 0 : -1;

        return this.second.compareTo(p.second);
    }
}
