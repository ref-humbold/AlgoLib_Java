// STRUKTURA DWUDZIELNEGO GRAFU PROSTEGO
package ref_humbold.algolib.graphs;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

public class BipartiteSimpleGraph
    extends UndirectedSimpleGraph
    implements BipartiteGraph
{
    /** Informacja o grupie wierzchołka. */
    private List<Boolean> parts;

    public BipartiteSimpleGraph(int n, List<Integer> prt)
    {
        super(n);
        parts = new ArrayList<Boolean>( Collections.nCopies(n, false) );

        for(Integer i : prt)
            if(i < n)
                parts.set(i, true);
    }

    public BipartiteSimpleGraph(int n, List<Integer> prt, List< Pair<Integer, Integer> > edges)
        throws IllegalStateException
    {
        this(n, prt);

        for(Pair<Integer, Integer> e : edges)
            if( isPartDifferent(e.first, e.second) )
            {
                graphrepr.get(e.first).add(e.second);
                graphrepr.get(e.second).add(e.first);
            }
            else
                throwPartitionError();
    }

    /** @see BipartiteGraph#getFirstPart */
    public List<Integer> getFirstPart()
    {
        List<Integer> fstPart = new ArrayList<Integer>();

        for(Integer v : getVertices())
            if( parts.get(v) )
                fstPart.add(v);

        return fstPart;
    }

    /** @see BipartiteGraph#getSecondPart */
    public List<Integer> getSecondPart()
    {
        List<Integer> sndPart = new ArrayList<Integer>();

        for(Integer v : getVertices())
            if( !parts.get(v) )
                sndPart.add(v);

        return sndPart;
    }

    /** @see BipartiteGraph#inFirstPart */
    public boolean inFirstPart(int vertex)
    {
        return parts.get(vertex);
    }

    /** @see BipartiteGraph#inSecondPart */
    public boolean inSecondPart(int vertex)
    {
        return !parts.get(vertex);
    }

    /** @see BipartiteGraph#isPartDifferent */
    public boolean isPartDifferent(int vertex1, int vertex2)
    {
        return parts.get(vertex1) != parts.get(vertex2);
    }
}
