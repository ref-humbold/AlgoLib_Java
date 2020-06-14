// Structure of directed simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectedSimpleGraph<V, VP, EP>
        extends SimpleGraph<V, VP, EP>
        implements DirectedGraph<V, VP, EP>
{
    public DirectedSimpleGraph()
    {
        super();
    }

    public DirectedSimpleGraph(Collection<V> vertices)
    {
        super(vertices);
    }

    @Override
    public int getEdgesCount()
    {
        return representation.getEdgesSet().mapToInt(Set::size).sum();
    }

    @Override
    public Collection<Edge<V>> getEdges()
    {
        return representation.getEdges().collect(Collectors.toList());
    }

    @Override
    public int getOutputDegree(V vertex)
    {
        return representation.getAdjacentEdges(vertex).mapToInt(edge -> 1).sum();
    }

    @Override
    public int getInputDegree(V vertex)
    {
        return representation.getEdgesSet()
                             .flatMap(edges -> edges.stream()
                                                    .filter(edge -> edge.destination.equals(
                                                            vertex)))
                             .mapToInt(edge -> 1)
                             .sum();
    }

    @Override
    public Edge<V> addEdge(Edge<V> edge, EP property)
    {
        Edge<V> existingEdge = getEdge(edge.source, edge.destination);

        if(existingEdge != null)
            return existingEdge;

        representation.addEdgeToSource(edge);
        representation.setProperty(edge, property);
        return edge;
    }

    @Override
    public void reverse()
    {
        GraphRepresentation<V, VP, EP> newRepresentation = new GraphRepresentation<>(getVertices());
        representation.getVertices().forEach(vertex -> {
            newRepresentation.setProperty(vertex, representation.getProperty(vertex));
        });
        representation.getEdges().forEach(edge -> {
            Edge<V> newEdge = edge.reversed();

            newRepresentation.addEdgeToSource(newEdge);
            newRepresentation.setProperty(newEdge, representation.getProperty(edge));
        });
        representation = newRepresentation;
    }

    @Override
    public DirectedGraph<V, VP, EP> reversedCopy()
    {
        DirectedSimpleGraph<V, VP, EP> reversedGraph = new DirectedSimpleGraph<>(getVertices());

        getVertices().forEach(vertex -> reversedGraph.setProperty(vertex, getProperty(vertex)));
        getEdges().forEach(edge -> reversedGraph.addEdge(edge.reversed(), getProperty(edge)));
        return reversedGraph;
    }
}
