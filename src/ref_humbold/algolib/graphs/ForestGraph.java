package ref_humbold.algolib.graphs;

import java.util.Collection;

import ref_humbold.algolib.structures.DisjointSets;
import ref_humbold.algolib.tuples.ComparablePair;
import ref_humbold.algolib.tuples.Pair;

public class ForestGraph
    implements UndirectedGraph
{
    private UndirectedGraph graph;
    private DisjointSets<Integer> components;

    public ForestGraph(UndirectedGraph graph)
    {
        this.graph = graph;
        this.components = new DisjointSets<>(this.graph.getVertices());
    }

    public ForestGraph(UndirectedGraph graph, Iterable<Pair<Integer, Integer>> edges)
        throws NoSuchVertexException
    {
        this(graph);

        for(Pair<Integer, Integer> e : edges)
            this.addEdge(e.getFirst(), e.getSecond());
    }

    /**
     * @return liczba drzew w grafie
     */
    public int getTreesNumber()
    {
        return components.size();
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
    public Integer addVertex()
    {
        Integer vertex = graph.addVertex();
        components.makeSet(vertex);

        return vertex;
    }

    @Override
    public void addEdge(Integer vertex1, Integer vertex2)
        throws NoSuchVertexException
    {
        if(isSameTree(vertex1, vertex2))
            throw new CycleException(
                "Edge from vertex " + vertex1 + " to vertex " + vertex2 + " will create a cycle.");

        components.unionSet(vertex1, vertex2);
        graph.addEdge(vertex1, vertex2);
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

    @Override
    public DirectedGraph asDirected()
    {
        return graph.asDirected();
    }

    /**
     * Sprawdzanie, czy wierzchołki należą do tego samego drzewa.
     * @param vertex1 pierwszy wierzchołek
     * @param vertex2 drugi wierzchołek
     * @return czy wierzchołki są w jednym drzewie
     */
    public boolean isSameTree(Integer vertex1, Integer vertex2)
    {
        return components.isSameSet(vertex1, vertex2);
    }
}
