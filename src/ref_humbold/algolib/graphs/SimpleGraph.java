// STRUKTURA GRAFU PROSTEGO
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ref_humbold.algolib.structures.Pair;

public abstract class SimpleGraph
    implements Graph
{
    /** Oznaczenie nieskończoności. */
    protected static Double INF = Double.POSITIVE_INFINITY;

    /** Domyślna waga krawędzi. */
    protected static Double DEFAULT_WEIGHT = 1.0;

    /** Lista sąsiedztwa grafu. */
    protected List<Set<Pair<Integer, Double>>> graphrepr;

    public SimpleGraph(int n)
    {
        graphrepr = new ArrayList<>();

        for(int i = 0; i < n; ++i)
            graphrepr.add(new HashSet<Pair<Integer, Double>>());
    }

    /** @see Graph#getVerticesNumber */
    @Override
    public int getVerticesNumber()
    {
        return graphrepr.size();
    }

    /** @see Graph#addVertex */
    @Override
    public Integer addVertex()
    {
        graphrepr.add(new HashSet<Pair<Integer, Double>>());

        return graphrepr.size() - 1;
    }

    /** @see Graph#getVertices */
    @Override
    public Iterable<Integer> getVertices()
    {
        List<Integer> vertices = new ArrayList<>();

        for(int i = 0; i < getVerticesNumber(); ++i)
            vertices.add(i);

        return vertices;
    }

    /** @see Graph#getNeighbours */
    @Override
    public Iterable<Integer> getNeighbours(Integer vertex)
    {
        List<Integer> neighbours = new ArrayList<Integer>();

        for(Pair<Integer, Double> e : graphrepr.get(vertex))
            neighbours.add(e.getFirst());

        return neighbours;
    }

    /** @see Graph#getOutdegree */
    @Override
    public int getOutdegree(Integer vertex)
    {
        return graphrepr.get(vertex).size();
    }
}
