// Structure of simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SimpleGraph<V, E>
        implements Graph<V, E>
{
    // Adjacency list
    protected GraphRepresentation<V, E> graphRepresentation;

    public SimpleGraph()
    {
        graphRepresentation = new GraphRepresentation<>();
    }

    public SimpleGraph(Collection<V> properties)
    {
        this();
        properties.forEach(property -> addVertex(property));
    }

    public SimpleGraph(Graph<V, E> graph)
    {
        graphRepresentation = new GraphRepresentation<>(graph.getVertices());
    }

    @Override
    public int getVerticesCount()
    {
        return graphRepresentation.size();
    }

    @Override
    public List<Vertex<V>> getVertices()
    {
        return graphRepresentation.getVertices().sorted().collect(Collectors.toList());
    }

    @Override
    public Vertex<V> getVertex(int index)
    {
        return graphRepresentation.getVertices()
                                  .filter(v -> index == v.index)
                                  .findFirst()
                                  .orElseThrow(() -> new IndexOutOfBoundsException(
                                          String.format("No vertex with index %d in this graph",
                                                        index)));
    }

    @Override
    public Collection<Vertex<V>> getNeighbours(Vertex<V> vertex)
    {
        return graphRepresentation.getAdjacentEdges(vertex)
                                  .stream()
                                  .map(edge -> edge.getNeighbour(vertex))
                                  .collect(Collectors.toSet());
    }

    @Override
    public Collection<Edge<E, V>> getAdjacentEdges(Vertex<V> vertex)
    {
        return graphRepresentation.getAdjacentEdges(vertex);
    }

    @Override
    public Edge<E, V> getEdge(Vertex<V> source, Vertex<V> destination)
    {
        return graphRepresentation.getAdjacentEdges(source)
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
        return graphRepresentation.addVertex(property);
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
