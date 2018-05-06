package ref_humbold.algolib.graphs;

public interface UndirectedWeightedGraph
    extends UndirectedGraph, WeightedGraph
{
    /**
     * Zamiana krawędzi nieskierowanych na skierowane.
     * @return graf ze skierowanymi krawędziami
     */
    @Override
    DirectedWeightedGraph asDirected();
}
