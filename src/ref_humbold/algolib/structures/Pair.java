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

    @Override
    public int compareTo(Pair<F, S> p)
    {
        if(first == null)
            return p.getFirst() == null ? 0 : -1;

        int comparedFirst = this.first.compareTo(p.getFirst());

        if(comparedFirst != 0)
            return comparedFirst;

        if(second == null)
            return p.getSecond() == null ? 0 : -1;

        return this.second.compareTo(p.getSecond());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null)
            return false;

        if(!(obj instanceof Pair))
            return false;

        Pair<?, ?> other = (Pair<?, ?>)obj;

        if(first == null && other.first != null || !first.equals(other.first))
            return false;

        if(second == null && other.second != null || !second.equals(other.second))
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        final int prime = 37;
        int result = 1;

        result = prime * result + (first == null ? 0 : first.hashCode());
        result = prime * result + (second == null ? 0 : second.hashCode());

        return result;
    }

    @Override
    public String toString()
    {
        return "(" + getFirst().toString() + ", " + getFirst().toString() + ")";
    }
}
