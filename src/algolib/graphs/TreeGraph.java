package algolib.graphs;

import java.util.Collection;

import algolib.structures.DisjointSets;
import algolib.tuples.ComparablePair;
import algolib.tuples.Pair;
import algolib.tuples.Pair;

public class TreeGraph
        implements UndirectedGraph
{
    private UndirectedSimpleGraph graph;

    public TreeGraph(int n, Iterable<Pair<Integer, Integer>> edges)
    {
        graph = new UndirectedSimpleGraph(n);
        DisjointSets<Integer> components = new DisjointSets<>(graph.getVertices());

        for(Pair<Integer, Integer> e : edges)
        {
            if(components.isSameSet(e.first, e.second))
                throw new CycleException(
                        "Edge from " + e.first + " to " + e.second + " may create a cycle");

            graph.addEdge(e.first, e.second);
            components.unionSet(e.first, e.second);
        }

        if(components.size() > 1)
            throw new NotConnectedException("Tree is not a connected graph");
    }

    @Override
    public int getVerticesNumber()
    {
        return graph.getVerticesNumber();
    }

    @Override
    public int getEdgesNumber()
    {
        return graph.getEdgesNumber();
    }

    @Override
    public Collection<Integer> getVertices()
    {
        return graph.getVertices();
    }

    @Override
    public Collection<ComparablePair<Integer, Integer>> getEdges()
    {
        return graph.getEdges();
    }

    @Override
    public Integer addVertex(Collection<Integer> neighbours)
    {
        if(neighbours.size() == 0)
            throw new NotConnectedException(
                    "New vertex won't be connected with the rest of the tree");

        if(neighbours.size() > 1)
            throw new CycleException("More than one edge from new vertex may create a cycle");

        return graph.addVertex(neighbours);
    }

    @Override
    public void addEdge(Integer vertex1, Integer vertex2)
    {
        throw new CycleException(
                "Edge from vertex " + vertex1 + " to vertex " + vertex2 + " will create a cycle.");
    }

    @Override
    public Collection<Integer> getNeighbours(Integer vertex)
    {
        return graph.getNeighbours(vertex);
    }

    @Override
    public int getOutdegree(Integer vertex)
    {
        return graph.getOutdegree(vertex);
    }

    @Override
    public int getIndegree(Integer vertex)
    {
        return graph.getIndegree(vertex);
    }
}
