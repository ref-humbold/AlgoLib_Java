package algolib.graphs;

import java.util.Collection;
import java.util.stream.Collectors;

/** Structure of undirected simple graph. */
public class UndirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty>
        extends SimpleGraph<VertexId, VertexProperty, EdgeProperty>
        implements UndirectedGraph<VertexId, VertexProperty, EdgeProperty>
{
    public UndirectedSimpleGraph()
    {
        super();
    }

    public UndirectedSimpleGraph(Collection<VertexId> vertexIds)
    {
        super(vertexIds);
    }

    @Override
    public int getEdgesCount()
    {
        return representation.getEdges().distinct().mapToInt(edge -> 1).sum();
    }

    @Override
    public Collection<Edge<VertexId>> getEdges()
    {
        return representation.getEdges().distinct().collect(Collectors.toList());
    }

    @Override
    public int getOutputDegree(Vertex<VertexId> vertex)
    {
        return representation.getAdjacentEdges(vertex).mapToInt(edge -> 1).sum();
    }

    @Override
    public int getInputDegree(Vertex<VertexId> vertex)
    {
        return representation.getAdjacentEdges(vertex).mapToInt(edge -> 1).sum();
    }

    @Override
    public Edge<VertexId> addEdge(Edge<VertexId> edge, EdgeProperty property)
    {
        if(getEdge(edge.source, edge.destination) != null)
            return null;

        representation.addEdgeToSource(edge);
        representation.addEdgeToDestination(edge);
        representation.setProperty(edge, property);
        return edge;
    }

    @Override
    public DirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> asDirected()
    {
        DirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> directedSimpleGraph =
                new DirectedSimpleGraph<>(
                        getVertices().stream().map(v -> v.id).collect(Collectors.toList()));

        getVertices().forEach(vertex -> directedSimpleGraph.getProperties()
                                                           .set(vertex,
                                                                getProperties().get(vertex)));
        getEdges().forEach(edge -> {
            directedSimpleGraph.addEdge(edge, getProperties().get(edge));
            directedSimpleGraph.addEdge(edge.reversed(), getProperties().get(edge));
        });

        return directedSimpleGraph;
    }
}
