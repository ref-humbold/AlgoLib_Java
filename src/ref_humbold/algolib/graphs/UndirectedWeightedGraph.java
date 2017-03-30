// STRUKTURA NIESKIEROWANEGO GRAFU WAŻONEGO
package ref_humbold.algolib.graphs;

import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

public class UndirectedWeightedGraph
    extends WeightedGraph
    implements UndirectedGraph
{
    public UndirectedWeightedGraph(int n)
    {
        super(n);
    }

    public UndirectedWeightedGraph(int n, List< Pair< Integer, Pair<Integer, Double> > > edges)
    {
        super(n);

        for(Pair< Integer, Pair<Integer, Double> > e : edges)
        {
            graphrepr.get(e.first).add(e.second);
            graphrepr.get(e.second.first).add(new Pair(e.first, e.second.second));
        }
    }

    /** @see Graph#getEdges */
    public List< Pair<Integer, Integer> > getEdges()
    {
        List< Pair<Integer, Integer> > edges = new ArrayList< Pair<Integer, Integer> >();

        for(Integer v : getVertices())
            for(Integer u : getNeighbours(v))
                if(u > v)
                    edges.add(new Pair<Integer, Integer>(v, u));

        return edges;
    }

    /** @see Graph#getEdgesNumber */
    public int getEdgesNumber()
    {
        int edgesNumber = 0;

        for(Integer v : getVertices())
            edgesNumber += getOutdegree(v);

        return edgesNumber>>1;
    }

    /** @see WeightedGraph#getWeightedEdges */
    public List< Pair<Pair<Integer, Integer>, Double> > getWeightedEdges()
    {
        List< Pair<Pair<Integer, Integer>, Double> > edges =
            new ArrayList< Pair<Pair<Integer, Integer>, Double> >();

        for(Integer v : getVertices())
            for(Pair<Integer, Double> e : getWeightedNeighbours(v))
                if(e.first > v)
                {
                    Pair<Integer, Integer> edge = new Pair<Integer, Integer>(v, e.first);

                    edges.add(new Pair<Pair<Integer, Integer>, Double>(edge, e.second));
                }

        return edges;
    }

    /** @see Graph#getIndegree */
    public int getIndegree(Integer v)
    {
        return getOutdegree(v);
    }
}

