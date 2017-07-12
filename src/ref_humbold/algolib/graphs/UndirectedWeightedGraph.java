package ref_humbold.algolib.graphs;

public interface UndirectedWeightedGraph
    extends UndirectedGraph, WeightedGraph
{
    @Override
    public DirectedWeightedGraph asDirected();
}
