package ref_humbold.algolib.graphs;

import java.util.Collection;

import ref_humbold.algolib.tuples.ComparablePair;
import ref_humbold.algolib.tuples.Triple;

public interface WeightedGraph
    extends Graph
{
    /**
     * @return lista krawędzi z wagami
     */
    Collection<Triple<Integer, Integer, Double>> getWeightedEdges();

    /**
     * Dodawanie nowej krawędzi z jej wagą.
     * @param vertex1 początkowy wierzchołek
     * @param vertex2 końcowy wierzchołek
     * @param weight waga krawędzi
     */
    void addWeightedEdge(Integer vertex1, Integer vertex2, Double weight)
        throws NoSuchVertexException;

    /**
     * @param vertex numer wierzchołka
     * @return lista sąsiadów wierzchołka wraz z wagami krawędzi
     */
    Collection<ComparablePair<Integer, Double>> getWeightedNeighbours(Integer vertex);
}
