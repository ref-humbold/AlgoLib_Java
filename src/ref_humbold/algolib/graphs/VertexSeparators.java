// ALGORYTM: WYSZUKIWANIE PUNKTÓW ARTYKULACJI W GRAFIE
package ref_humbold.algolib.graphs;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class VertexSeparators
{
    /** Onaczenie braku wartości w liście. */
    private static Integer NO_VALUE = null;

    /** Graf. */
    private Graph graph;

    /** Lista synów w drzewie DFS. */
    private List<List<Integer>> childsDFS = new ArrayList<List<Integer>>();

    /** Głębokość wierzchołka w drzewie DFS. */
    private List<Integer> depthsDFS = new ArrayList<Integer>();

    /** Wartości funkcji LOW dla wierzchołków. */
    private List<Integer> valuesLOW = new ArrayList<Integer>();

    public VertexSeparators(Graph graph)
    {
        this.graph = graph;

        for(int i = 0; i < graph.getVerticesNumber(); ++i)
        {
            childsDFS.add(new ArrayList<Integer>());
            depthsDFS.add(NO_VALUE);
            valuesLOW.add(NO_VALUE);
        }
    }

    /**
     * Wyznaczanie punktów artykulacji.
     * @return lista punktów artykulacji
     */
    public List<Integer> algorithm()
    {
        List<Integer> separators = new ArrayList<>();
        depthsDFS.set(0, 0);

        for(int v = 0; v < graph.getVerticesNumber(); ++v)
            if(depthsDFS.get(v) == NO_VALUE)
                dfs(v, 0);

        for(int v = 0; v < graph.getVerticesNumber(); ++v)
            if(isSeparator(v))
                separators.add(v);

        return separators;
    }

    /**
     * Algorytm DFS wyliczający funkcję low.
     * @param vertex aktualny wierzchołek
     * @param parent ojciec wierzchołka
     */
    private void dfs(int vertex, int parent)
    {
        depthsDFS.set(vertex, depthsDFS.get(parent) + 1);
        valuesLOW.set(vertex, depthsDFS.get(vertex));

        for(Integer neighbour : graph.getNeighbours(vertex))
            if(depthsDFS.get(neighbour) == NO_VALUE)
            {
                childsDFS.get(vertex).add(neighbour);
                dfs(neighbour, vertex);
                valuesLOW.set(vertex, Math.min(valuesLOW.get(vertex), valuesLOW.get(neighbour)));
            }
            else if(!neighbour.equals(parent))
                valuesLOW.set(vertex, Math.min(valuesLOW.get(vertex), depthsDFS.get(neighbour)));
    }

    /**
     * Sprawdzanie wierzchołka jako punktu artykulacji.
     * @param vertex wierzchołek
     * @return czy wierzchołek to punkt artykulacji
     */
    private boolean isSeparator(int vertex)
    {
        if(depthsDFS.get(vertex).equals(1))
            return childsDFS.get(vertex).size() > 1;

        for(Integer ch : childsDFS.get(vertex))
            if(valuesLOW.get(ch) >= depthsDFS.get(vertex))
                return true;

        return false;
    }
}
