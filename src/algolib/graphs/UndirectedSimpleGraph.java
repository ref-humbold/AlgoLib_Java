// Structure of undirected simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;

public class UndirectedSimpleGraph<V, VP, EP>
        extends SimpleGraph<V, VP, EP>
        implements UndirectedGraph<V, VP, EP>
{
    public UndirectedSimpleGraph()
    {
        super();
    }

    public UndirectedSimpleGraph(Collection<V> vertices)
    {
        super(vertices);
    }

    @Override
    public int getEdgesCount()
    {
        return representation.getEdges().distinct().mapToInt(edge -> 1).sum();
    }

    @Override
    public Collection<Edge<V>> getEdges()
    {
        return representation.getEdges().distinct().collect(Collectors.toList());
    }

    @Override
    public int getOutputDegree(V vertex)
    {
        return representation.getAdjacentEdges(vertex).mapToInt(edge -> 1).sum();
    }

    @Override
    public int getInputDegree(V vertex)
    {
        return representation.getAdjacentEdges(vertex).mapToInt(edge -> 1).sum();
    }

    @Override
    public Edge<V> addEdge(Edge<V> edge, EP property)
    {
        Edge<V> existingEdge = getEdge(edge.source, edge.destination);

        if(existingEdge != null)
            return existingEdge;

        representation.addEdgeToSource(edge);
        representation.addEdgeToDestination(edge);
        representation.setProperty(edge, property);
        return edge;
    }

    /**
     * Converts this graph to a directed graph with the same vertices.
     * @return directed graph
     */
    public DirectedSimpleGraph<V, VP, EP> asDirected()
    {
        DirectedSimpleGraph<V, VP, EP> directedSimpleGraph =
                new DirectedSimpleGraph<>(getVertices());

        getVertices().forEach(
                vertex -> directedSimpleGraph.setProperty(vertex, getProperty(vertex)));
        getEdges().forEach(edge -> {
            directedSimpleGraph.addEdge(edge, getProperty(edge));
            directedSimpleGraph.addEdge(edge.reversed(), getProperty(edge));
        });

        return directedSimpleGraph;
    }
}
