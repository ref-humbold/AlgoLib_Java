// PARA
package algolib.tuples;

public final class ImmutablePair<F, S>
    extends AbstractPair<F, S>
{
    public ImmutablePair(F first, S second)
    {
        super(first, second);
    }

    public static <F, S> ImmutablePair<F, S> make(F first, S second)
    {
        return new ImmutablePair<>(first, second);
    }
}
