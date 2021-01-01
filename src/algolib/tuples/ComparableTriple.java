package algolib.tuples;

/** Structure of comparable triple */
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
        if(first == null)
            return t.first == null ? 0 : -1;

        int comparedFirst = first.compareTo(t.first);

        if(comparedFirst != 0)
            return comparedFirst;

        if(second == null)
            return t.second == null ? 0 : -1;

        int comparedSecond = second.compareTo(t.second);

        if(comparedSecond != 0)
            return comparedSecond;

        if(third == null)
            return t.third == null ? 0 : -1;

        return third.compareTo(t.third);
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
