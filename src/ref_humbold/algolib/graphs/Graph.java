// STRUKTURA GRAFU
package ref_humbold.algolib.graphs;

import java.util.List;

import ref_humbold.algolib.structures.Pair;

public interface Graph
{
    /**
    Getter dla liczby wierzchołków.
    @return liczba wierzchołków
    */
    public int getVerticesNumber();

    /**
    Getter dla liczby krawędzi.
    @return liczba krawędzi
    */
    public int getEdgesNumber();

    /**
    Wszystkie wierzchołki.
    @return lista wierzchołków
    */
    public List<Integer> getVertices();

    /**
    Wszystkie krawędzie.
    @return lista krawędzi
    */
    public List< Pair<Integer, Integer> > getEdges();

    /**
    Sąsiedzi wierzchołka.
    @param v numer wierzchołka
    @return lista sąsiadów wierzchołka
    */
    public List<Integer> getNeighbours(Integer v);
}

