// STRUKTURA GRAFU DWUDZIELNEGO
package ref_humbold.algolib.graphs;

import java.util.List;

public interface BipartiteGraph
    extends Graph
{
    /**
    Wierzchołki pierwszej grupy.
    @return lista wierzchołków z pierwszej grupy
    */
    public List<Integer> getFirstPart();

    /**
    Wierzchołki drugiej grupy.
    @return lista wierzchołków z drugiej grupy
    */
    public List<Integer> getSecondPart();

    /**
    Sprawdza, czy wierzchołek nalezy do pierwszej grupy.
    @param vertex wierzchołek
    @return czy wierzchołek jest w pierwszej grupie
    */
    public boolean inFirstPart(int vertex);

    /**
    Sprawdza, czy wierzchołek nalezy do drugiej grupy.
    @param vertex wierzchołek
    @return czy wierzchołek jest w drugiej grupie
    */
    public boolean inSecondPart(int vertex);

    /**
    Sprawdza, czy wierzchołki są w różnych grupach.
    @param vertex1 pierwszy wierzchołek
    @param vertex2 drugi wierzchołek
    @return czy wierzchołki są w różnych grupach
    */
    public boolean isPartDifferent(int vertex1, int vertex2);

    /** Rzucanie wyjątku przy naruszeniu dwudzielności. */
    default protected void throwPartitionError()
        throws IllegalStateException
    {
        throw new IllegalStateException("Graph is not bipartite");
    }
}
