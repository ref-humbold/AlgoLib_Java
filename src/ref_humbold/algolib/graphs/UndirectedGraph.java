package ref_humbold.algolib.graphs;

public interface UndirectedGraph
    extends Graph
{
    /**
     * Zamiana krawędzi nieskierowanych na skierowane.
     * @return graf ze skierowanymi krawędziami
     */
    public DirectedGraph asDirected();
}
