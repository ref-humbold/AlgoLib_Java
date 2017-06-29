// STRUKTURA GRAFU DWUDZIELNEGO
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ref_humbold.algolib.structures.Pair;

public class MultipartiteGraph
    implements UndirectedGraph
{
    /**
     * Struktura grafu wielodzielnego.
     */
    private UndirectedGraph graph;

    /**
     * Maksymalna liczba grup wierzchołków.
     */
    private Integer groupsNumber;

    /**
     * Numery grup wierzchołków.
     */
    private List<Integer> groups;

    public MultipartiteGraph(Integer group, Integer n)
    {
        this.groupsNumber = group;
        this.graph = new UndirectedSimpleGraph(n);
        this.groups = new ArrayList<>(Collections.nCopies(getVerticesNumber(), 1));
    }

    public Integer getGroupsNumber()
    {
        return groupsNumber;
    }

    @Override
    public int getVerticesNumber()
    {
        return graph.getVerticesNumber();
    }

    @Override
    public int getEdgesNumber()
    {
        return graph.getEdgesNumber();
    }

    @Override
    public Collection<Integer> getVertices()
    {
        return graph.getVertices();
    }

    @Override
    public Collection<Pair<Integer, Integer>> getEdges()
    {
        return graph.getEdges();
    }

    @Override
    public Integer addVertex()
    {
        return addVertex(1);
    }

    /**
     * Dodawanie nowego wierzchołka.
     * @param group czy wierzchołek w pierwszej grupie
     * @return oznaczenie wierzchołka
     */
    public Integer addVertex(Integer group)
    {
        groups.add(group);

        return graph.addVertex();
    }

    @Override
    public void addEdge(Integer vertex1, Integer vertex2)
    {
        if(vertex1 < 0 || vertex1 > getVerticesNumber() || vertex2 < 0
           || vertex2 > getVerticesNumber())
            throw new IllegalArgumentException("No such vertex.");

        if(isSameGroup(vertex1, vertex2))
            throw new GraphPartitionException("Vertices in the same part.");

        graph.addEdge(vertex1, vertex2);
    }

    @Override
    public Collection<Integer> getNeighbours(Integer vertex)
    {
        return graph.getNeighbours(vertex);
    }

    @Override
    public int getOutdegree(Integer vertex)
    {
        return graph.getOutdegree(vertex);
    }

    @Override
    public int getIndegree(Integer vertex)
    {
        return graph.getIndegree(vertex);
    }

    @Override
    public DirectedGraph asDirected()
    {
        return graph.asDirected();
    }

    /**
     * Wierzchołki zadanej grupy.
     * @param group numer grupy
     * @return lista wierzchołków grupy
     */
    public Iterable<Integer> getGroup(Integer group)
    {
        List<Integer> part = new ArrayList<>();

        for(Integer v : graph.getVertices())
            if(groups.get(v).equals(group))
                part.add(v);

        return part;
    }

    /**
     * Sprawdza, czy wierzchołek należy do zadanej grupy.
     * @param vertex wierzchołek
     * @param group numer grupy
     * @return czy wierzchołek jest w grupie
     */
    public boolean isInGroup(Integer vertex, Integer group)
    {
        return groups.get(vertex).equals(group);
    }

    /**
     * Sprawdza, czy wierzchołki są w różnych grupach.
     * @param vertex1 pierwszy wierzchołek
     * @param vertex2 drugi wierzchołek
     * @return czy wierzchołki są w różnych grupach
     */
    public boolean isSameGroup(Integer vertex1, Integer vertex2)
    {
        return groups.get(vertex1).equals(groups.get(vertex2));
    }
}