// STRUKTURA SKIEROWANEGO GRAFU WAŻONEGO
package ref_humbold.algolib.graphs;

import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

public class DirectedWeightedGraph
    extends WeightedGraph
{
    public DirectedWeightedGraph(int n)
    {
        super(n);
    }

    public DirectedWeightedGraph(int n, List< Pair< Integer, Pair<Integer, Double> > > edges)
    {
        super(n);

        for(Pair< Integer, Pair<Integer, Double> > e : edges)
            graphrepr.get(e.first).add(e.second);
    }

    /** @see Graph#edges */
    public List< Pair<Integer, Integer> > getEdges()
    {
        List< Pair<Integer, Integer> > edges = new ArrayList< Pair<Integer, Integer> >();

        for(Integer v : getVertices())
            for( Integer u : getNeighbours(v) )
                edges.add( new Pair<Integer, Integer>(v, u) );

        return edges;
    }

    /** @see WeightedGraph#weightedEdges */
    public List< Pair<Pair<Integer, Integer>, Double> > getWeightedEdges()
    {
        List< Pair<Pair<Integer, Integer>, Double> > edges =
            new ArrayList< Pair<Pair<Integer, Integer>, Double> >();

        for(Integer v : getVertices())
            for( Pair<Integer, Double> e : getWeightedNeighbours(v) )
            {
                Pair<Integer, Integer> edge = new Pair<>(v, e.first);

                edges.add( new Pair<Pair<Integer, Integer>, Double>(edge, e.second) );
            }

        return edges;
    }
}

