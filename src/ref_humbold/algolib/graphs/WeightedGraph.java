package ref_humbold.algolib.graphs;

import java.util.Collection;

import ref_humbold.algolib.structures.Pair;
import ref_humbold.algolib.structures.Triple;

public interface WeightedGraph
    extends Graph
{
    /**
     * Wszystkie krawędzie z ich wagami.
     * @return lista krawędzi z wagami
     */
    Collection<Triple<Integer, Integer, Double>> getWeightedEdges();

    /**
     * Dodawanie nowej krawędzi z jej wagą.
     * @param vertex1 początkowy wierzchołek
     * @param vertex2 końcowy wierzchołek
     * @param weight waga krawędzi
     */
    void addWeightedEdge(Integer vertex1, Integer vertex2, Double weight);

    /**
     * Sąsiedzi wierzchołka z wagami krawędzi do nich.
     * @param vertex numer wierzchołka
     * @return lista sąsiadów wierzchołka z wagami
     */
    Collection<Pair<Integer, Double>> getWeightedNeighbours(Integer vertex);
}
