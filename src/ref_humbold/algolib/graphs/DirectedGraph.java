// STRUKTURA SKIEROWANEGO GRAFU PROSTEGO
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ref_humbold.algolib.structures.Pair;

public class DirectedGraph
    extends SimpleGraph
{
    public DirectedGraph(int n)
    {
        super(n);
    }

    public DirectedGraph(int n, List<Pair<Integer, Integer>> edges)
    {
        super(n);

        for(Pair<Integer, Integer> e : edges)
            graphrepr.get(e.getFirst()).add(new Pair<>(e.getSecond(), DEFAULT_WEIGHT));
    }

    /** @see Graph#getEdgesNumber */
    @Override
    public int getEdgesNumber()
    {
        int edgesNumber = 0;

        for(Integer v : getVertices())
            edgesNumber += getOutdegree(v);

        return edgesNumber;
    }

    /** @see Graph#getEdges */
    @Override
    public Collection<Pair<Integer, Integer>> getEdges()
    {
        List<Pair<Integer, Integer>> edges = new ArrayList<Pair<Integer, Integer>>();

        for(Integer v : getVertices())
            for(Integer u : getNeighbours(v))
                edges.add(new Pair<Integer, Integer>(v, u));

        return edges;
    }

    /** @see Graph#addEdge */
    @Override
    public void addEdge(Integer vertex1, Integer vertex2)
    {
        if(vertex1 < 0 || vertex1 > getVerticesNumber() || vertex2 < 0
           || vertex2 > getVerticesNumber())
            throw new IllegalArgumentException("No such vertex.");

        graphrepr.get(vertex1).add(new Pair<>(vertex2, DEFAULT_WEIGHT));
    }

    /** @see Graph#getIndegree */
    @Override
    public int getIndegree(Integer vertex)
    {
        int indeg = 0;

        for(Integer w : getVertices())
            for(Integer u : getNeighbours(w))
                if(u == vertex)
                    ++indeg;

        return indeg;
    }
}
