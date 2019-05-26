package refhumbold.algolib.graphs;

import java.util.Collection;

import refhumbold.algolib.graphs.exceptions.NoSuchVertexException;
import refhumbold.algolib.tuples.ComparablePair;
import refhumbold.algolib.tuples.ImmutableTriple;

public interface WeightedGraph
        extends Graph
{
    /**
     * @return lista krawędzi z wagami
     */
    Collection<ImmutableTriple<Integer, Integer, Double>> getWeightedEdges();

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
