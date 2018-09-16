// PORÓWNYWALNA TRÓJKA
package refhumbold.algolib.tuples;

public final class ComparableTriple<F extends Comparable<? super F>, S extends Comparable<? super S>, T extends Comparable<? super T>>
    extends AbstractTriple<F, S, T>
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
        return new ComparableTriple<>(triple.getFirst(), triple.getSecond(), triple.getThird());
    }

    @Override
    public int compareTo(Triple<F, S, T> t)
    {
        if(this.first == null)
            return t.getFirst() == null ? 0 : -1;

        int comparedFirst = this.first.compareTo(t.getFirst());

        if(comparedFirst != 0)
            return comparedFirst;

        if(this.second == null)
            return t.getSecond() == null ? 0 : -1;

        int comparedSecond = this.second.compareTo(t.getSecond());

        if(comparedSecond != 0)
            return comparedSecond;

        if(this.third == null)
            return t.getThird() == null ? 0 : -1;

        return this.third.compareTo(t.getThird());
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
