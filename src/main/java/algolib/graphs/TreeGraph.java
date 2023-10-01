package algolib.graphs;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/** Structure of tree graph. */
public class TreeGraph<VertexId, VertexProperty, EdgeProperty>
        implements UndirectedGraph<VertexId, VertexProperty, EdgeProperty>
{
    private final UndirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> graph;

    public TreeGraph(VertexId vertexId)
    {
        graph = new UndirectedSimpleGraph<>(Collections.singleton(vertexId));
    }

    @Override
    public GraphProperties<VertexId, VertexProperty, EdgeProperty> getProperties()
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
    public Vertex<VertexId> getVertex(VertexId vertexId)
    {
        return graph.getVertex(vertexId);
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

    @Override
    public DirectedGraph<VertexId, VertexProperty, EdgeProperty> asDirected()
    {
        return graph.asDirected();
    }

    /**
     * Adds new vertex to this graph and creates an edge to given existing vertex.
     * @param vertexId the identifier of new vertex
     * @param neighbour the existing vertex
     * @return the created edge between the vertices, or {@code null} if vertex already exists
     */
    public Edge<VertexId> addVertex(VertexId vertexId, Vertex<VertexId> neighbour)
    {
        return addVertex(new Vertex<>(vertexId), neighbour);
    }

    /**
     * Adds new vertex to this graph and creates an edge to given existing vertex.
     * @param vertexId the identifier of new vertex
     * @param neighbour the existing vertex
     * @param vertexProperty the vertex property
     * @param edgeProperty the edge property
     * @return the created edge between the vertices, or {@code null} if vertex already exists
     */
    public Edge<VertexId> addVertex(
            VertexId vertexId,
            Vertex<VertexId> neighbour,
            VertexProperty vertexProperty,
            EdgeProperty edgeProperty)
    {
        return addVertex(new Vertex<>(vertexId), neighbour, vertexProperty, edgeProperty);
    }

    /**
     * Adds new vertex to this graph and creates an edge to given existing vertex.
     * @param vertex the new vertex
     * @param neighbour the existing vertex
     * @return the created edge between the vertices, or {@code null} if vertex already exists
     */
    public Edge<VertexId> addVertex(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
    {
        return addVertex(vertex, neighbour, null, null);
    }

    /**
     * Adds new vertex to this graph and creates an edge to given existing vertex.
     * @param vertex the new vertex
     * @param neighbour the existing vertex
     * @param vertexProperty the vertex property
     * @param edgeProperty the edge property
     * @return the created edge between the vertices, or {@code null} if vertex already exists
     */
    public Edge<VertexId> addVertex(
            Vertex<VertexId> vertex,
            Vertex<VertexId> neighbour,
            VertexProperty vertexProperty,
            EdgeProperty edgeProperty)
    {
        return Optional.ofNullable(graph.addVertex(vertex, vertexProperty))
                       .map(newVertex -> graph.addEdgeBetween(newVertex, neighbour, edgeProperty))
                       .orElse(null);
    }
}
