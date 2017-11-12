// STRUKTURA GRAFU
package ref_humbold.algolib.graphs;

import java.util.Collection;

import ref_humbold.algolib.tuples.ComparablePair;

public interface Graph
{
    /**
     * Oznaczenie nieskończoności.
     */
    double INF = Double.POSITIVE_INFINITY;

    /**
     * @return liczba wierzchołków
     */
    int getVerticesNumber();

    /**
     * @return lista wierzchołków
     */
    Collection<Integer> getVertices();

    /**
     * Dodawanie nowego wierzchołka.
     * @return oznaczenie wierzchołka
     */
    Integer addVertex();

    /**
     * @return liczba krawędzi
     */
    int getEdgesNumber();

    /**
     * @return lista krawędzi
     */
    Collection<ComparablePair<Integer, Integer>> getEdges();

    /**
     * Dodawanie nowej krawędzi.
     * @param vertex1 początkowy wierzchołek
     * @param vertex2 końcowy wierzchołek
     */
    void addEdge(Integer vertex1, Integer vertex2);

    /**
     * @param vertex numer wierzchołka
     * @return lista sąsiadów wierzchołka
     */
    Collection<Integer> getNeighbours(Integer vertex);

    /**
     * @param vertex numer wierzchołka
     * @return stopień wyjściowy wierzchołka
     */
    int getOutdegree(Integer vertex);

    /**
     * @param vertex numer wierzchołka
     * @return stopień wejściowy wierzchołka
     */
    int getIndegree(Integer vertex);
}
