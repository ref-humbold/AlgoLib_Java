// PORÓWNYWALNA TRÓJKA
package ref_humbold.algolib.tuples;

public class ComparableTriple<F extends Comparable<F>, S extends Comparable<S>, T extends Comparable<T>>
    extends Triple<F, S, T>
    implements Comparable<Triple<F, S, T>>
{
    public ComparableTriple(F first, S second, T third)
    {
        super(first, second, third);
    }

    public static <F extends Comparable<F>, S extends Comparable<S>, T extends Comparable<T>> ComparableTriple<F, S, T> make(
        F first, S second, T third)
    {
        return new ComparableTriple<>(first, second, third);
    }

    public static <F extends Comparable<F>, S extends Comparable<S>, T extends Comparable<T>> ComparableTriple<F, S, T> from(
        Triple<F, S, T> triple)
    {
        return new ComparableTriple<>(triple.first, triple.second, triple.third);
    }

    @Override
    public int compareTo(Triple<F, S, T> t)
    {
        if(this.first == null)
            return t.first == null ? 0 : -1;

        int comparedFirst = this.first.compareTo(t.first);

        if(comparedFirst != 0)
            return comparedFirst;

        if(this.second == null)
            return t.getSecond() == null ? 0 : -1;

        int comparedSecond = this.second.compareTo(t.second);

        if(comparedSecond != 0)
            return comparedSecond;

        if(this.third == null)
            return t.getThird() == null ? 0 : -1;

        return this.third.compareTo(t.third);
    }
}
