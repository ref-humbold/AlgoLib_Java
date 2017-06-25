// STRUKTURA GRAFU
package ref_humbold.algolib.graphs;

import java.util.Collection;

import ref_humbold.algolib.structures.Pair;

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
     * @return liczba krawędzi
     */
    int getEdgesNumber();

    /**
     * @return lista wierzchołków
     */
    Collection<Integer> getVertices();

    /**
     * @return lista krawędzi
     */
    Collection<Pair<Integer, Integer>> getEdges();

    /**
     * Dodawanie nowego wierzchołka.
     * @return oznaczenie wierzchołka
     */
    Integer addVertex();

    /**
     * Dodawanie nowej krawędzi.
     * @param vertex1 początkowy wierzchołek
     * @param vertex2 końcowy wierzchołek
     */
    void addEdge(Integer vertex1, Integer vertex2);

    /**
     * Sąsiedzi wierzchołka.
     * @param vertex numer wierzchołka
     * @return lista sąsiadów wierzchołka
     */
    Collection<Integer> getNeighbours(Integer vertex);

    /**
     * Stopień wyjściowy wierzchołka.
     * @param vertex numer wierzchołka
     * @return wartość stopnia wyjściowego wierzchołka
     */
    int getOutdegree(Integer vertex);

    /**
     * Stopień wejściowy wierzchołka.
     * @param vertex numer wierzchołka
     * @return wartość stopnia wejściowego wierzchołka
     */
    int getIndegree(Integer vertex);
}
