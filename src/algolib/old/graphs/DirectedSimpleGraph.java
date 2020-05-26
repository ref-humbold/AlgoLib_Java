// Structure of directed simple graph
package algolib.old.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import algolib.tuples.ComparablePair;
import algolib.tuples.Pair;

public class DirectedSimpleGraph
        extends SimpleGraph
        implements DirectedGraph
{
    public DirectedSimpleGraph(int n)
    {
        super(n);
    }

    public DirectedSimpleGraph(int n, Iterable<Pair<Integer, Integer>> edges)
            throws NoSuchVertexException
    {
        super(n);

        for(Pair<Integer, Integer> e : edges)
            addEdge(e.first, e.second);
    }

    @Override
    public int getEdgesNumber()
    {
        int edgesNumber = 0;

        for(Integer v : getVertices())
            edgesNumber += getOutdegree(v);

        return edgesNumber;
    }

    @Override
    public Collection<ComparablePair<Integer, Integer>> getEdges()
    {
        List<ComparablePair<Integer, Integer>> edges = new ArrayList<>();

        for(Integer v : getVertices())
            for(Integer u : getNeighbours(v))
                edges.add(ComparablePair.of(v, u));

        return edges;
    }

    @Override
    public void addEdge(Integer vertex1, Integer vertex2)
    {
        if(vertex1 < 0 || vertex1 >= getVerticesNumber())
            throw new NoSuchVertexException("No vertex " + vertex1);

        if(vertex2 < 0 || vertex2 >= getVerticesNumber())
            throw new NoSuchVertexException("No vertex " + vertex2);

        graphrepr.get(vertex1).add(ComparablePair.of(vertex2, SimpleGraph.DEFAULT_WEIGHT));
    }

    @Override
    public int getIndegree(Integer vertex)
    {
        int indeg = 0;

        for(Integer w : getVertices())
            for(Integer u : getNeighbours(w))
                if(u.equals(vertex))
                    ++indeg;

        return indeg;
    }

    @Override
    public void reverse()
    {
        List<Set<ComparablePair<Integer, Double>>> revgraphrepr = new ArrayList<>();

        for(Integer v : getVertices())
            revgraphrepr.add(new HashSet<>());

        for(Pair<Integer, Integer> e : getEdges())
            revgraphrepr.get(e.second).add(ComparablePair.of(e.first, SimpleGraph.DEFAULT_WEIGHT));

        graphrepr = revgraphrepr;
    }
}
