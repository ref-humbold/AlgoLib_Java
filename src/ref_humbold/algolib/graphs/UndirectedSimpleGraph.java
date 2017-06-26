// STRUKTURA NIESKIEROWANEGO GRAFU PROSTEGO
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ref_humbold.algolib.structures.Pair;

public class UndirectedSimpleGraph
    extends SimpleGraph
    implements UndirectedGraph
{
    public UndirectedSimpleGraph(int n)
    {
        super(n);
    }

    public UndirectedSimpleGraph(int n, Iterable<Pair<Integer, Integer>> edges)
    {
        super(n);

        for(Pair<Integer, Integer> e : edges)
            this.addEdge(e.getFirst(), e.getSecond());
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
    public Collection<Pair<Integer, Integer>> getEdges()
    {
        List<Pair<Integer, Integer>> edges = new ArrayList<>();

        for(Integer v : getVertices())
            for(Integer u : getNeighbours(v))
                if(u >= v)
                    edges.add(new Pair<>(v, u));

        return edges;
    }

    @Override
    public void addEdge(Integer vertex1, Integer vertex2)
    {
        if(vertex1 < 0 || vertex1 >= getVerticesNumber())
            throw new IllegalArgumentException("No such vertex: " + vertex1.toString() + ".");

        if(vertex2 < 0 || vertex2 >= getVerticesNumber())
            throw new IllegalArgumentException("No such vertex: " + vertex2.toString() + ".");

        graphrepr.get(vertex1).add(Pair.make(vertex2, DEFAULT_WEIGHT));
        graphrepr.get(vertex2).add(Pair.make(vertex1, DEFAULT_WEIGHT));
    }

    @Override
    public int getIndegree(Integer vertex)
    {
        return getOutdegree(vertex);
    }

    @Override
    public DirectedSimpleGraph asDirected()
    {
        Collection<Pair<Integer, Integer>> diedges = getEdges();

        for(Pair<Integer, Integer> e : getEdges())
            diedges.add(Pair.make(e.getSecond(), e.getFirst()));

        return new DirectedSimpleGraph(getVerticesNumber(), diedges);
    }
}
