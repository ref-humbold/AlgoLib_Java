// Structure of directed simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectedSimpleGraph<V, E>
        extends SimpleGraph<V, E>
        implements DirectedGraph<V, E>
{
    public DirectedSimpleGraph()
    {
        super();
    }

    public DirectedSimpleGraph(Collection<V> properties)
    {
        super(properties);
    }

    public DirectedSimpleGraph(Graph<V, E> graph)
    {
        super(graph);
    }

    @Override
    public int getEdgesCount()
    {
        return graphRepresentation.getEdgesSet().mapToInt(Set::size).sum();
    }

    @Override
    public List<Edge<E, V>> getEdges()
    {
        return graphRepresentation.getEdges().sorted().collect(Collectors.toList());
    }

    @Override
    public int getOutputDegree(Vertex<V> vertex)
    {
        return graphRepresentation.getAdjacentEdges(vertex).size();
    }

    @Override
    public int getInputDegree(Vertex<V> vertex)
    {
        return graphRepresentation.getEdgesSet()
                                  .flatMap(edges -> edges.stream()
                                                         .filter(edge -> edge.destination.equals(
                                                                 vertex)))
                                  .mapToInt(edge -> 1)
                                  .sum();
    }

    @Override
    public Edge<E, V> addEdge(Vertex<V> source, Vertex<V> destination, E property)
    {
        Edge<E, V> edge = new Edge<>(source, destination, property);

        graphRepresentation.addEdgeToSource(edge);
        return edge;
    }

    @Override
    public void reverse()
    {
        List<Edge<E, V>> edges = getEdges();

        graphRepresentation = new GraphRepresentation<>(getVertices());
        edges.forEach(edge -> graphRepresentation.addEdgeToSource(
                new Edge<>(edge.destination, edge.source, edge.property)));
    }
}
