package refhumbold.algolib.graphs;

import java.util.Collection;

import refhumbold.algolib.graphs.exceptions.CycleException;
import refhumbold.algolib.graphs.exceptions.NoSuchVertexException;
import refhumbold.algolib.graphs.exceptions.NotConnectedException;
import refhumbold.algolib.structures.DisjointSets;
import refhumbold.algolib.tuples.ComparablePair;
import refhumbold.algolib.tuples.ImmutablePair;

public class TreeGraph
        implements UndirectedGraph
{
    private UndirectedSimpleGraph graph;

    public TreeGraph(int n, Iterable<ImmutablePair<Integer, Integer>> edges)
            throws NoSuchVertexException
    {
        graph = new UndirectedSimpleGraph(n);
        DisjointSets<Integer> components = new DisjointSets<>(graph.getVertices());

        for(ImmutablePair<Integer, Integer> e : edges)
        {
            if(components.isSameSet(e.getFirst(), e.getSecond()))
                throw new CycleException("Edge from " + e.getFirst() + " to " + e.getSecond()
                                                 + " may create a cycle");

            graph.addEdge(e.getFirst(), e.getSecond());
            components.unionSet(e.getFirst(), e.getSecond());
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
            throws NoSuchVertexException
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
