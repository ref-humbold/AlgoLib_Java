// PARA
package ref_humbold.algolib.structures;

public class Pair<F extends Comparable<F>, S extends Comparable<S>>
    implements Comparable<Pair<F, S>>
{
    private final F first;
    private final S second;

    public Pair(F f, S s)
        throws IllegalArgumentException
    {
        if(f == null || s == null)
            throw new IllegalArgumentException("Argument is null.");

        first = f;
        second = s;
    }

    public F getFirst()
    {
        return first;
    }

    public S getSecond()
    {
        return second;
    }

    public int compareTo(Pair<F, S> p)
    {
        int comparedFirst = this.getFirst().compareTo(p.getFirst());

        return comparedFirst == 0 ? this.getSecond().compareTo(p.getSecond()) : comparedFirst;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null)
            return false;

        if(!obj.getClass().equals(this.getClass()))
            throw new IllegalArgumentException("Argument is not a pair.");

        Pair<?, ?> p = (Pair<?, ?>)obj;

        return this.getFirst().equals(p.getFirst()) && this.getSecond().equals(p.getSecond());
    }

    @Override
    public int hashCode()
    {
        int prime = 37;
        int modulo = 1000007;
        int fstHash = getFirst().hashCode();
        int sndHash = getSecond().hashCode();

        return ((fstHash * prime) % modulo + sndHash) % modulo;
    }

    @Override
    public String toString()
    {
        return "(" + getFirst().toString() + ", " + getFirst().toString() + ")";
    }
}
