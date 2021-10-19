package algolib.graphs;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/** Structure of directed simple graph */
public class DirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty>
        extends SimpleGraph<VertexId, VertexProperty, EdgeProperty>
        implements DirectedGraph<VertexId, VertexProperty, EdgeProperty>
{
    public DirectedSimpleGraph()
    {
        super();
    }

    public DirectedSimpleGraph(Collection<VertexId> vertexIds)
    {
        super(vertexIds);
    }

    @Override
    public int getEdgesCount()
    {
        return representation.getEdgesSet().mapToInt(Set::size).sum();
    }

    @Override
    public Collection<Edge<VertexId>> getEdges()
    {
        return representation.getEdges().collect(Collectors.toList());
    }

    @Override
    public int getOutputDegree(Vertex<VertexId> vertex)
    {
        return representation.getAdjacentEdges(vertex).mapToInt(edge -> 1).sum();
    }

    @Override
    public int getInputDegree(Vertex<VertexId> vertex)
    {
        return representation.getEdgesSet()
                             .flatMap(edges -> edges.stream()
                                                    .filter(edge -> edge.destination.equals(
                                                            vertex)))
                             .mapToInt(edge -> 1)
                             .sum();
    }

    @Override
    public Edge<VertexId> addEdge(Edge<VertexId> edge, EdgeProperty property)
    {
        if(getEdge(edge.source, edge.destination) != null)
            return null;

        representation.addEdgeToSource(edge);
        representation.setProperty(edge, property);
        return edge;
    }

    @Override
    public void reverse()
    {
        GraphRepresentation<VertexId, VertexProperty, EdgeProperty> newRepresentation =
                new GraphRepresentation<>(
                        getVertices().stream().map(v -> v.id).collect(Collectors.toList()));

        representation.getVertices().forEach(vertex -> {
            newRepresentation.setProperty(vertex, representation.getProperty(vertex));
        });
        representation.getEdges().forEach(edge -> {
            Edge<VertexId> newEdge = edge.reversed();

            newRepresentation.addEdgeToSource(newEdge);
            newRepresentation.setProperty(newEdge, representation.getProperty(edge));
        });
        representation = newRepresentation;
    }

    @Override
    public DirectedGraph<VertexId, VertexProperty, EdgeProperty> reversedCopy()
    {
        DirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> reversedGraph =
                new DirectedSimpleGraph<>(
                        getVertices().stream().map(v -> v.id).collect(Collectors.toList()));

        getVertices().forEach(
                vertex -> reversedGraph.getProperties().set(vertex, getProperties().get(vertex)));
        getEdges().forEach(
                edge -> reversedGraph.addEdge(edge.reversed(), getProperties().get(edge)));
        return reversedGraph;
    }
}
