// STRUKTURA NIESKIEROWANEGO GRAFU PROSTEGO
package ref_humbold.algolib.graphs;

import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

public class UndirectedSimpleGraph
    extends SimpleGraph
{
    public UndirectedSimpleGraph(int n)
    {
        super(n);
    }

    public UndirectedSimpleGraph(int n, List< Pair<Integer, Integer> > edges)
    {
        super(n);

        for(Pair<Integer, Integer> e : edges)
        {
            graphrepr.get(e.first).add(e.second);
            graphrepr.get(e.second).add(e.first);
        }
    }

    /** @see Graph#edges */
    public List< Pair<Integer, Integer> > getEdges()
    {
        List< Pair<Integer, Integer> > edges = new ArrayList< Pair<Integer, Integer> >();

        for(Integer v : getVertices())
            for( Integer u : getNeighbours(v) )
                if(u > v)
                    edges.add( new Pair<Integer, Integer>(v, u) );

        return edges;
    }
}

