// STRUKTURA DWUDZIELNEGO GRAFU WAŻONEGO
package ref_humbold.algolib.graphs;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

public class BipartiteWeightedGraph
    extends UndirectedWeightedGraph
    implements BipartiteGraph
{
    /** Informacja o grupie wierzchołka. */
    private List<Boolean> parts;

    public BipartiteWeightedGraph(int n, List<Integer> prt)
    {
        super(n);
        parts = new ArrayList<Boolean>( Collections.nCopies(n, false) );

        for(Integer i : prt)
            if(i < n)
                parts.set(i, true);
    }

    public BipartiteWeightedGraph(int n, List<Integer> prt,
        List< Pair< Integer, Pair<Integer, Double> > > edges)
        throws IllegalStateException
    {
        this(n, prt);

        for(Pair< Integer, Pair<Integer, Double> > e : edges)
            if( isPartDifferent(e.first, e.second.first) )
            {
                graphrepr.get(e.first).add(e.second);
                graphrepr.get(e.second.first).add( new Pair(e.first, e.second.second) );
            }
            else
                throw new IllegalStateException("Graph is not bipartite");
    }

    /** @see BipartiteGraph#getFirstPart */
    public List<Integer> getFirstPart()
    {
        List<Integer> fstPart = new ArrayList<>();

        for(Integer v : getVertices())
            if( parts.get(v) )
                fstPart.add(v);

        return fstPart;
    }

    /** @see BipartiteGraph#getSecondPart */
    public List<Integer> getSecondPart()
    {
        List<Integer> sndPart = new ArrayList<>();

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
