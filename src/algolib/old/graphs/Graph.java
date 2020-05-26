// STRUKTURA GRAFU
package algolib.old.graphs;

import java.util.Collection;
import java.util.Collections;

import algolib.tuples.ComparablePair;

public interface Graph
{
    /**
     * Oznaczenie nieskończoności
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
     * @return liczba krawędzi
     */
    int getEdgesNumber();

    /**
     * @return lista krawędzi
     */
    Collection<ComparablePair<Integer, Integer>> getEdges();

    /**
     * Dodawanie nowego wierzchołka
     * @return oznaczenie wierzchołka
     */
    default Integer addVertex()
    {
        return addVertex(Collections.emptyList());
    }

    /**
     * Dodawanie nowego wierzchołka
     * @param neighbours sąsiedzi nowego wierzchołka
     * @return oznaczenie wierzchołka
     */
    Integer addVertex(Collection<Integer> neighbours);

    /**
     * Dodawanie nowej krawędzi
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
