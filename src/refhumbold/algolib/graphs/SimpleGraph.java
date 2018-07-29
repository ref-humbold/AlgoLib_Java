// STRUKTURA GRAFU PROSTEGO
package refhumbold.algolib.graphs;

import java.util.*;

import refhumbold.algolib.tuples.ComparablePair;
import refhumbold.algolib.tuples.Pair;

public abstract class SimpleGraph
    implements Graph
{
    /**
     * Domyślna waga krawędzi.
     */
    protected static Double DEFAULT_WEIGHT = 1.0;

    /**
     * Lista sąsiedztwa grafu.
     */
    protected List<Set<ComparablePair<Integer, Double>>> graphrepr;

    public SimpleGraph(int n)
    {
        this.graphrepr = new ArrayList<>();

        for(int i = 0; i < n; ++i)
            this.graphrepr.add(new HashSet<>());
    }

    @Override
    public int getVerticesNumber()
    {
        return graphrepr.size();
    }

    @Override
    public Collection<Integer> getVertices()
    {
        List<Integer> vertices = new ArrayList<>();

        for(int i = 0; i < getVerticesNumber(); ++i)
            vertices.add(i);

        return vertices;
    }

    @Override
    public Integer addVertex()
    {
        graphrepr.add(new HashSet<>());

        return graphrepr.size() - 1;
    }

    @Override
    public Collection<Integer> getNeighbours(Integer vertex)
    {
        List<Integer> neighbours = new ArrayList<>();

        for(Pair<Integer, Double> e : graphrepr.get(vertex))
            neighbours.add(e.getFirst());

        return neighbours;
    }

    @Override
    public int getOutdegree(Integer vertex)
    {
        return graphrepr.get(vertex).size();
    }
}