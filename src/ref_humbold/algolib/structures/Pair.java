// PARA
package ref_humbold.algolib.structures;

import java.lang.Comparable;

public class Pair< F extends Comparable<F>, S extends Comparable<S> >
    implements Comparable< Pair<F, S> >
{
    public F first;
    public S second;

    public Pair(F f, S s)
        throws IllegalArgumentException
    {
        if(f == null || s == null)
            throw new IllegalArgumentException("Argument is null.");

        first = f;
        second = s;
    }

    public int compareTo(Pair<F, S> p)
    {
        int comparedFirst = this.first.compareTo(p.first);

        return comparedFirst == 0 ? this.second.compareTo(p.second) : comparedFirst;
    }

    public boolean equals(Pair<F, S> p)
    {
        return this.first.equals(p.first) && this.second.equals(p.second);
    }
}
