// STRUKTURA SKIEROWANEGO GRAFU PROSTEGO
package ref_humbold.algolib.graphs;

import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

public class DirectedSimpleGraph
    extends SimpleGraph
{
    public DirectedSimpleGraph(int n)
    {
        super(n);
    }

    public DirectedSimpleGraph(int n, List< Pair<Integer, Integer> > edges)
    {
        super(n);

        for(Pair<Integer, Integer> e : edges)
            graphrepr.get(e.first).add(e.second);
    }

    /** @see Graph#getEdges */
    public List< Pair<Integer, Integer> > getEdges()
    {
        List< Pair<Integer, Integer> > edges = new ArrayList< Pair<Integer, Integer> >();

        for(Integer v : getVertices())
            for( Integer u : getNeighbours(v) )
                edges.add( new Pair<Integer, Integer>(v, u) );

        return edges;
    }

    /** @see Graph#getEdgesNumber */
    public int getEdgesNumber()
    {
        int edgesNumber = 0;

        for(Integer v : getVertices())
            for( Integer u : getNeighbours(v) )
                ++edgesNumber;

        return edgesNumber;
    }
}

