// STRUKTURA NIESKIEROWANEGO GRAFU WAŻONEGO
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ref_humbold.algolib.structures.Pair;
import ref_humbold.algolib.structures.Triple;

public class UndirectedWeightedSimpleGraph
    extends UndirectedSimpleGraph
    implements UndirectedWeightedGraph
{
    public UndirectedWeightedSimpleGraph(int n)
    {
        super(n);
    }

    public UndirectedWeightedSimpleGraph(int n, Iterable<Triple<Integer, Integer, Double>> edges)
    {
        super(n);

        for(Triple<Integer, Integer, Double> e : edges)
            this.addWeightedEdge(e.getFirst(), e.getSecond(), e.getThird());
    }

    @Override
    public Collection<Triple<Integer, Integer, Double>> getWeightedEdges()
    {
        List<Triple<Integer, Integer, Double>> edges = new ArrayList<>();

        for(Integer v : getVertices())
            for(Pair<Integer, Double> e : getWeightedNeighbours(v))
                if(e.getFirst() >= v)
                    edges.add(Triple.make(v, e.getFirst(), e.getSecond()));

        return edges;
    }

    @Override
    public void addWeightedEdge(Integer vertex1, Integer vertex2, Double weight)
    {
        if(vertex1 < 0 || vertex1 >= getVerticesNumber())
            throw new IllegalArgumentException("No such vertex: " + vertex1.toString() + ".");

        if(vertex2 < 0 || vertex2 >= getVerticesNumber())
            throw new IllegalArgumentException("No such vertex: " + vertex2.toString() + ".");

        graphrepr.get(vertex1).add(Pair.make(vertex2, weight));
        graphrepr.get(vertex2).add(Pair.make(vertex1, weight));
    }

    @Override
    public Collection<Pair<Integer, Double>> getWeightedNeighbours(Integer vertex)
    {
        return graphrepr.get(vertex);
    }

    @Override
    public DirectedWeightedSimpleGraph asDirected()
    {
        Collection<Triple<Integer, Integer, Double>> diwedges = getWeightedEdges();

        for(Triple<Integer, Integer, Double> e : getWeightedEdges())
            diwedges.add(Triple.make(e.getSecond(), e.getFirst(), e.getThird()));

        return new DirectedWeightedSimpleGraph(getVerticesNumber(), diwedges);
    }
}
