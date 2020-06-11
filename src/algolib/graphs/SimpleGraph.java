// Structure of simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class SimpleGraph<V, VP, EP>
        implements Graph<V, VP, EP>
{
    protected GraphRepresentation<V, VP, EP> representation;

    public SimpleGraph()
    {
        representation = new GraphRepresentation<>();
    }

    public SimpleGraph(Collection<V> vertices)
    {
        representation = new GraphRepresentation<>(vertices);
    }

    @Override
    public int getVerticesCount()
    {
        return representation.size();
    }

    @Override
    public Collection<V> getVertices()
    {
        return representation.getVertices().collect(Collectors.toList());
    }

    @Override
    public VP getProperty(V vertex)
    {
        return representation.getProperty(vertex);
    }

    @Override
    public void setProperty(V vertex, VP property)
    {
        representation.setProperty(vertex, property);
    }

    @Override
    public EP getProperty(Edge<V> edge)
    {
        return representation.getProperty(edge);
    }

    @Override
    public void setProperty(Edge<V> edge, EP property)
    {
        representation.setProperty(edge, property);
    }

    @Override
    public Collection<V> getNeighbours(V vertex)
    {
        return representation.getAdjacentEdges(vertex)
                             .map(edge -> edge.getNeighbour(vertex))
                             .collect(Collectors.toSet());
    }

    @Override
    public Collection<Edge<V>> getAdjacentEdges(V vertex)
    {
        return representation.getAdjacentEdges(vertex).collect(Collectors.toSet());
    }

    @Override
    public Edge<V> getEdge(V source, V destination)
    {
        return representation.getAdjacentEdges(source)
                             .filter(edge -> edge.getNeighbour(source).equals(destination))
                             .findFirst()
                             .orElse(null);
    }

    /**
     * Adds a new vertex to this graph.
     * @param vertex a new vertex
     * @return {@code true} if the vertex was added, otherwise {@code false}
     */
    public boolean addVertex(V vertex)
    {
        return addVertex(vertex, null);
    }

    /**
     * Adds a new vertex with given property to this graph.
     * @param vertex a new vertex
     * @param property vertex property
     * @return {@code true} if the vertex was added, otherwise {@code false}
     */
    public boolean addVertex(V vertex, VP property)
    {
        boolean wasAdded = representation.addVertex(vertex);

        if(wasAdded)
            representation.setProperty(vertex, property);

        return wasAdded;
    }

    /**
     * Adds a new edge to this graph.
     * @param source a source vertex
     * @param destination a destination vertex
     * @return the new edge if added, or the existing edge
     */
    public Edge<V> addEdge(V source, V destination)
    {
        return addEdge(source, destination, null);
    }

    /**
     * Adds a new edge with given property to this graph.
     * @param source a source vertex
     * @param destination a destination vertex
     * @param property edge property
     * @return the new edge if added, or the existing edge
     */
    public abstract Edge<V> addEdge(V source, V destination, EP property);
}
