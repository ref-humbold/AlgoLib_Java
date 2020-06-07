// Structure of simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SimpleGraph<V, E>
        implements Graph<V, E>
{
    protected GraphRepresentation<V, E> representation;

    public SimpleGraph()
    {
        representation = new GraphRepresentation<>();
    }

    public SimpleGraph(Collection<V> properties)
    {
        this();
        properties.forEach(property -> addVertex(property));
    }

    public SimpleGraph(Graph<V, E> graph)
    {
        representation = new GraphRepresentation<>(graph.getVertices());
    }

    @Override
    public int getVerticesCount()
    {
        return representation.size();
    }

    @Override
    public List<Vertex<V>> getVertices()
    {
        return representation.getVertices().sorted().collect(Collectors.toList());
    }

    @Override
    public Vertex<V> getVertex(int index)
    {
        return representation.getVertices()
                             .filter(v -> index == v.index)
                             .findFirst()
                             .orElseThrow(() -> new IndexOutOfBoundsException(
                                     String.format("No vertex with index %d in this graph",
                                                   index)));
    }

    @Override
    public Collection<Vertex<V>> getNeighbours(Vertex<V> vertex)
    {
        return representation.getAdjacentEdges(vertex)
                             .stream()
                             .map(edge -> edge.getNeighbour(vertex))
                             .collect(Collectors.toSet());
    }

    @Override
    public Collection<Edge<E, V>> getAdjacentEdges(Vertex<V> vertex)
    {
        return representation.getAdjacentEdges(vertex);
    }

    @Override
    public Edge<E, V> getEdge(Vertex<V> source, Vertex<V> destination)
    {
        return representation.getAdjacentEdges(source)
                             .stream()
                             .filter(edge -> edge.getNeighbour(source) == destination)
                             .findFirst()
                             .orElse(null);
    }

    /**
     * Adds a new vertex with given property to this graph.
     * @param property a vertex property
     * @return the new vertex
     */
    public Vertex<V> addVertex(V property)
    {
        return representation.addVertex(property);
    }

    /**
     * Adds a new edge with given properties to this graph.
     * @param source a source vertex
     * @param destination a destination vertex
     * @param property an edge property
     * @return the new edge
     */
    public abstract Edge<E, V> addEdge(Vertex<V> source, Vertex<V> destination, E property);
}
