package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;

/** Structure of simple graph */
public abstract class SimpleGraph<VertexId, VertexProperty, EdgeProperty>
        implements Graph<VertexId, VertexProperty, EdgeProperty>
{
    GraphRepresentation<VertexId, VertexProperty, EdgeProperty> representation;
    private final GraphProperties<VertexId, VertexProperty, EdgeProperty> properties =
            new GraphProperties<>()
            {
                @Override
                public VertexProperty get(Vertex<VertexId> vertex)
                {
                    return representation.getProperty(vertex);
                }

                @Override
                public void set(Vertex<VertexId> vertex, VertexProperty property)
                {
                    representation.setProperty(vertex, property);
                }

                @Override
                public EdgeProperty get(Edge<VertexId> edge)
                {
                    return representation.getProperty(edge);
                }

                @Override
                public void set(Edge<VertexId> edge, EdgeProperty property)
                {
                    representation.setProperty(edge, property);
                }
            };

    public SimpleGraph()
    {
        representation = new GraphRepresentation<>();
    }

    public SimpleGraph(Collection<VertexId> vertexIds)
    {
        representation = new GraphRepresentation<>(vertexIds);
    }

    @Override
    public GraphProperties<VertexId, VertexProperty, EdgeProperty> getProperties()
    {
        return properties;
    }

    @Override
    public int getVerticesCount()
    {
        return representation.size();
    }

    @Override
    public Collection<Vertex<VertexId>> getVertices()
    {
        return representation.getVertices().collect(Collectors.toList());
    }

    @Override
    public Vertex<VertexId> getVertex(VertexId vertexId)
    {
        return representation.getVertex(vertexId);
    }

    @Override
    public Edge<VertexId> getEdge(VertexId sourceId, VertexId destinationId)
    {
        return representation.getEdge(sourceId, destinationId);
    }

    @Override
    public Collection<Edge<VertexId>> getAdjacentEdges(Vertex<VertexId> vertex)
    {
        return representation.getAdjacentEdges(vertex).collect(Collectors.toSet());
    }

    @Override
    public Collection<Vertex<VertexId>> getNeighbours(Vertex<VertexId> vertex)
    {
        return representation.getAdjacentEdges(vertex)
                             .map(edge -> edge.getNeighbour(vertex))
                             .collect(Collectors.toSet());
    }

    /**
     * Adds new vertex to this graph.
     * @param vertexId the identifier of new vertex
     * @return the created vertex, or {@code null} if vertex already exists
     */
    public Vertex<VertexId> addVertex(VertexId vertexId)
    {
        return addVertex(vertexId, null);
    }

    /**
     * Adds new vertex with given property to this graph.
     * @param vertexId the identifier of new vertex
     * @param property the vertex property
     * @return the created vertex, or {@code null} if vertex already exists
     */
    public Vertex<VertexId> addVertex(VertexId vertexId, VertexProperty property)
    {
        return addVertex(new Vertex<>(vertexId), property);
    }

    /**
     * Adds new vertex to this graph.
     * @param vertex the new vertex
     * @return the created vertex, or {@code null} if vertex already exists
     */
    public Vertex<VertexId> addVertex(Vertex<VertexId> vertex)
    {
        return addVertex(vertex, null);
    }

    /**
     * Adds new vertex with given property to this graph.
     * @param vertex the new vertex
     * @param property the vertex property
     * @return the created vertex, or {@code null} if vertex already exists
     */
    public Vertex<VertexId> addVertex(Vertex<VertexId> vertex, VertexProperty property)
    {
        boolean wasAdded = representation.addVertex(vertex);

        if(wasAdded)
        {
            representation.setProperty(vertex, property);
            return vertex;
        }

        return null;
    }

    /**
     * Adds new edge between given vertices to this graph.
     * @param source the source vertex
     * @param destination the destination vertex
     * @return the created edge, or {@code null} if edge already exists
     */
    public Edge<VertexId> addEdgeBetween(Vertex<VertexId> source, Vertex<VertexId> destination)
    {
        return addEdge(new Edge<>(source, destination));
    }

    /**
     * Adds new edge between given vertices with given property to this graph.
     * @param source the source vertex
     * @param destination the destination vertex
     * @param property the edge property
     * @return the created edge, or {@code null} if edge already exists
     */
    public Edge<VertexId> addEdgeBetween(
            Vertex<VertexId> source, Vertex<VertexId> destination, EdgeProperty property)
    {
        return addEdge(new Edge<>(source, destination), property);
    }

    /**
     * Adds new edge to this graph.
     * @param edge the new edge
     * @return the created edge, or {@code null} if edge already exists
     */
    public Edge<VertexId> addEdge(Edge<VertexId> edge)
    {
        return addEdge(edge, null);
    }

    /**
     * Adds new edge with given property to this graph.
     * @param edge the new edge
     * @param property the edge property
     * @return the created edge, or {@code null} if edge already exists
     */
    public abstract Edge<VertexId> addEdge(Edge<VertexId> edge, EdgeProperty property);
}
