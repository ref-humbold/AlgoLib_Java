// STRUKTURA NIESKIEROWANEGO GRAFU PROSTEGO
package algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import algolib.tuples.ComparablePair;
import algolib.tuples.Pair;
import algolib.tuples.Pair;

public class UndirectedSimpleGraph
        extends SimpleGraph
        implements UndirectedGraph
{
    public UndirectedSimpleGraph(int n)
    {
        super(n);
    }

    public UndirectedSimpleGraph(int n, Iterable<Pair<Integer, Integer>> edges)
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
        {
            edgesNumber += getOutdegree(v);

            if(getNeighbours(v).contains(v))
                ++edgesNumber;
        }

        return edgesNumber / 2;
    }

    @Override
    public Collection<ComparablePair<Integer, Integer>> getEdges()
    {
        List<ComparablePair<Integer, Integer>> edges = new ArrayList<>();

        for(Integer v : getVertices())
            for(Integer u : getNeighbours(v))
                if(u >= v)
                    edges.add(ComparablePair.make(v, u));

        return edges;
    }

    @Override
    public void addEdge(Integer vertex1, Integer vertex2)
    {
        if(vertex1 < 0 || vertex1 >= getVerticesNumber())
            throw new NoSuchVertexException("No vertex " + vertex1);

        if(vertex2 < 0 || vertex2 >= getVerticesNumber())
            throw new NoSuchVertexException("No vertex " + vertex2);

        graphrepr.get(vertex1).add(ComparablePair.make(vertex2, SimpleGraph.DEFAULT_WEIGHT));
        graphrepr.get(vertex2).add(ComparablePair.make(vertex1, SimpleGraph.DEFAULT_WEIGHT));
    }

    @Override
    public int getIndegree(Integer vertex)
    {
        return getOutdegree(vertex);
    }

    public DirectedSimpleGraph asDirected()
    {
        Collection<Pair<Integer, Integer>> diedges = new ArrayList<>();

        for(Pair<Integer, Integer> e : getEdges())
        {
            diedges.add(Pair.make(e.first, e.second));
            diedges.add(Pair.make(e.second, e.first));
        }

        return new DirectedSimpleGraph(getVerticesNumber(), diedges);
    }
}
