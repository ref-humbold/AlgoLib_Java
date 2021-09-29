// Structure of simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class SimpleGraph<VertexId, VertexProperty, EdgeProperty>
        implements Graph<VertexId, VertexProperty, EdgeProperty>
{
    protected GraphRepresentation<VertexId, VertexProperty, EdgeProperty> representation;
    private final Properties<VertexId, VertexProperty, EdgeProperty> properties = new Properties<>()
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
    public Properties<VertexId, VertexProperty, EdgeProperty> getProperties()
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
    public Edge<VertexId> getEdge(VertexId sourceId, VertexId destinationId)
    {
        return representation.getAdjacentEdges(sourceId)
                             .filter(edge -> edge.getNeighbour(sourceId).equals(destinationId))
                             .findFirst()
                             .orElse(null);
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
     * @param vertexId new vertex identifier
     * @return new vertex
     */
    public Vertex<VertexId> addVertex(VertexId vertexId)
    {
        return addVertex(vertexId, null);
    }

    /**
     * Adds new vertex with given property to this graph.
     * @param vertexId new vertex identifier
     * @param property vertex property
     * @return new vertex
     */
    public Vertex<VertexId> addVertex(VertexId vertexId, VertexProperty property)
    {
        Vertex<VertexId> vertex = representation.addVertex(vertexId);

        if(vertex != null)
            representation.setProperty(vertex, property);

        return vertex;
    }

    /**
     * Adds a new edge to this graph.
     * @param source source vertex
     * @param destination destination vertex
     * @return new edge if added, or the existing edge
     */
    public Edge<VertexId> addEdgeBetween(Vertex<VertexId> source, Vertex<VertexId> destination)
    {
        return addEdge(new Edge<>(source, destination));
    }

    /**
     * Adds new edge with given property to this graph.
     * @param source source vertex
     * @param destination destination vertex
     * @param property edge property
     * @return new edge if added, or the existing edge
     */
    public Edge<VertexId> addEdgeBetween(Vertex<VertexId> source, Vertex<VertexId> destination,
                                         EdgeProperty property)
    {
        return addEdge(new Edge<>(source, destination), property);
    }

    /**
     * Adds new edge to this graph.
     * @param edge new edge
     * @return new edge if added, or the existing edge
     */
    public Edge<VertexId> addEdge(Edge<VertexId> edge)
    {
        return addEdge(edge, null);
    }

    /**
     * Adds new edge with given property to this graph.
     * @param edge new edge
     * @param property edge property
     * @return new edge if added, or the existing edge
     */
    public abstract Edge<VertexId> addEdge(Edge<VertexId> edge, EdgeProperty property);
}
