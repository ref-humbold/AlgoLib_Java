// STRUKTURA SKIEROWANEGO GRAFU PROSTEGO
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ref_humbold.algolib.structures.Pair;

public class DirectedSimpleGraph
    extends SimpleGraph
    implements DirectedGraph
{
    public DirectedSimpleGraph(int n)
    {
        super(n);
    }

    public DirectedSimpleGraph(int n, Iterable<Pair<Integer, Integer>> edges)
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
            edgesNumber += getOutdegree(v);

        return edgesNumber;
    }

    @Override
    public Collection<Pair<Integer, Integer>> getEdges()
    {
        List<Pair<Integer, Integer>> edges = new ArrayList<>();

        for(Integer v : getVertices())
            for(Integer u : getNeighbours(v))
                edges.add(new Pair<>(v, u));

        return edges;
    }

    @Override
    public void addEdge(Integer vertex1, Integer vertex2)
    {
        if(vertex1 < 0 || vertex1 >= getVerticesNumber())
            throw new IllegalArgumentException(vertex1.toString());

        if(vertex2 < 0 || vertex2 >= getVerticesNumber())
            throw new IllegalArgumentException(vertex2.toString());

        graphrepr.get(vertex1).add(Pair.make(vertex2, DEFAULT_WEIGHT));
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
        List<Set<Pair<Integer, Double>>> revgraphrepr = new ArrayList<>();

        for(Integer v : getVertices())
            revgraphrepr.add(new HashSet<>());

        for(Pair<Integer, Integer> e : getEdges())
            revgraphrepr.get(e.getSecond()).add(Pair.make(e.getFirst(), DEFAULT_WEIGHT));

        graphrepr = revgraphrepr;
    }
}
