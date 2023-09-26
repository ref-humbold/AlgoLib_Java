package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public class EmptyStrategy<VertexId>
        implements DfsStrategy<VertexId>
{
    @Override
    public void forRoot(Vertex<VertexId> root)
    {
    }

    @Override
    public void onEntry(Vertex<VertexId> vertex)
    {
    }

    @Override
    public void onNextVertex(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
    {
    }

    @Override
    public void onExit(Vertex<VertexId> vertex)
    {
    }

    @Override
    public void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
    {
    }
}
