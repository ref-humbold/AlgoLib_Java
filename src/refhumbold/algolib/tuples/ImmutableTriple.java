// TRÓJKA
package refhumbold.algolib.tuples;

public final class ImmutableTriple<F, S, T>
    extends AbstractTriple<F, S, T>
{
    public ImmutableTriple(F first, S second, T third)
    {
        super(first, second, third);
    }

    public static <F, S, T> ImmutableTriple<F, S, T> make(F first, S second, T third)
    {
        return new ImmutableTriple<>(first, second, third);
    }
}
