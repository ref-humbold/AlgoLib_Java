package ref_humbold.algolib.graphs;

import ref_humbold.algolib.structures.Pair;

public interface WeightedGraph
    extends Graph
{
    /**
     * Wszystkie krawędzie z ich wagami.
     * @return lista krawędzi z wagami
     */
    Iterable<Pair<Pair<Integer, Integer>, Double>> getWeightedEdges();

    /**
     * Dodawanie nowej krawędzi z jej wagą.
     * @param vertex1 początkowy wierzchołek
     * @param vertex2 końcowy wierzchołek
     * @param weight waga krawędzi
     */
    void addEdge(Integer vertex1, Integer vertex2, Double weight);

    /**
     * Sąsiedzi wierzchołka z wagami krawędzi do nich.
     * @param vertex numer wierzchołka
     * @return lista sąsiadów wierzchołka z wagami
     */
    Iterable<Pair<Integer, Double>> getWeightedNeighbours(Integer vertex);
}
