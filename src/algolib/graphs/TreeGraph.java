// Structure of tree graph
package algolib.graphs;

import java.util.Collection;
import java.util.Collections;

public class TreeGraph<VertexId, VertexProperty, EdgeProperty>
        implements UndirectedGraph<VertexId, VertexProperty, EdgeProperty>
{
    private final UndirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> graph;

    public TreeGraph(VertexId vertexId)
    {
        graph = new UndirectedSimpleGraph<>(Collections.singleton(vertexId));
    }

    @Override
    public Properties<VertexId, VertexProperty, EdgeProperty> getProperties()
    {
        return graph.getProperties();
    }

    @Override
    public int getVerticesCount()
    {
        return graph.getVerticesCount();
    }

    @Override
    public int getEdgesCount()
    {
        return graph.getEdgesCount();
    }

    @Override
    public Collection<Vertex<VertexId>> getVertices()
    {
        return graph.getVertices();
    }

    @Override
    public Collection<Edge<VertexId>> getEdges()
    {
        return graph.getEdges();
    }

    @Override
    public Edge<VertexId> getEdge(VertexId sourceId, VertexId destinationId)
    {
        return graph.getEdge(sourceId, destinationId);
    }

    @Override
    public Collection<Vertex<VertexId>> getNeighbours(Vertex<VertexId> vertex)
    {
        return graph.getNeighbours(vertex);
    }

    @Override
    public Collection<Edge<VertexId>> getAdjacentEdges(Vertex<VertexId> vertex)
    {
        return graph.getAdjacentEdges(vertex);
    }

    @Override
    public int getOutputDegree(Vertex<VertexId> vertex)
    {
        return graph.getOutputDegree(vertex);
    }

    @Override
    public int getInputDegree(Vertex<VertexId> vertex)
    {
        return graph.getInputDegree(vertex);
    }

    public DirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> asDirected()
    {
        return graph.asDirected();
    }

    /**
     * Adds a new vertex to this graph and creates an edge to an existing vertex.
     * @param vertexId a new vertex
     * @param neighbour an existing vertex
     * @return the edge between the vertices
     */
    public Edge<VertexId> addVertex(VertexId vertexId, Vertex<VertexId> neighbour)
    {
        Vertex<VertexId> vertex = graph.addVertex(vertexId);

        return vertex != null ? graph.addEdgeBetween(vertex, neighbour) : null;
    }

    /**
     * Adds a new vertex to this graph and creates an edge to an existing vertex.
     * @param vertexId a new vertex
     * @param neighbour an existing vertex
     * @param vertexProperty a vertex property
     * @param edgeProperty an edge property
     * @return the edge between the vertices, or {@code null} if vertex already exists
     */
    public Edge<VertexId> addVertex(VertexId vertexId, Vertex<VertexId> neighbour,
                                    VertexProperty vertexProperty, EdgeProperty edgeProperty)
    {
        Vertex<VertexId> vertex = graph.addVertex(vertexId, vertexProperty);

        return vertex != null ? graph.addEdgeBetween(vertex, neighbour, edgeProperty) : null;
    }
}
