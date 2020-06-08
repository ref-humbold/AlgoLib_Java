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
        return representation.getEdges().sorted().collect(Collectors.toList());
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
    public Edge<V> addEdge(V source, V destination)
    {
        Edge<V> existingEdge = getEdge(source, destination);

        if(existingEdge != null)
            return existingEdge;

        Edge<V> newEdge = new Edge<>(source, destination);

        representation.addEdgeToSource(newEdge);
        return newEdge;
    }

    @Override
    public void reverse()
    {
        Collection<Edge<V>> edges = getEdges();

        representation = new GraphRepresentation<>(getVertices());
        edges.forEach(edge -> representation.addEdgeToSource(edge.reversed()));
    }

    @Override
    public DirectedGraph<V, VP, EP> reversedCopy()
    {
        DirectedSimpleGraph<V, VP, EP> reversedGraph = new DirectedSimpleGraph<>(getVertices());

        getVertices().forEach(vertex -> reversedGraph.setProperty(vertex, getProperty(vertex)));
        getEdges().forEach(
                edge -> reversedGraph.addEdge(edge.destination, edge.source, getProperty(edge)));
        return reversedGraph;
    }
}
