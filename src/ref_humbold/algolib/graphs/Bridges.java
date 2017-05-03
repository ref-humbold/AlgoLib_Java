// ALGORYTM: WYSZUKIWANIE MOSTÓW W GRAFIE
package ref_humbold.algolib.graphs;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

public class Bridges
{
    /** Onaczenie braku wartości w liście. */
    private static Integer NO_VALUE = null;

    /** Graf. */
    private Graph graph;

    /** Ojciec wierzchołka w drzewie DFS. */
    private List<Integer> parentDFS = new ArrayList<>();

    /** Głębokość wierzchołka w drzewie DFS. */
    private List<Integer> depthsDFS = new ArrayList<>();

    /** Wartości funkcji LOW dla wierzchołków. */
    private List<Integer> valuesLOW = new ArrayList<>();

    public Bridges(Graph graph)
    {
        this.graph = graph;

        for(int i = 0; i < graph.getVerticesNumber(); ++i)
        {
            parentDFS.add(NO_VALUE);
            depthsDFS.add(NO_VALUE);
            valuesLOW.add(NO_VALUE);
        }
    }

    /**
     * Wyznacza mosty w grafie.
     * @return lista krawędzi będących mostami
     */
    public List<Pair<Integer, Integer>> algorithm()
    {
        List<Pair<Integer, Integer>> bridges = new ArrayList<Pair<Integer, Integer>>();

        depthsDFS.set(0, 0);

        for(int v = 0; v < graph.getVerticesNumber(); ++v)
            if(depthsDFS.get(v) == NO_VALUE)
            {
                parentDFS.set(v, 0);
                dfs(v);
            }

        for(int v = 1; v < graph.getVerticesNumber(); ++v)
            if(valuesLOW.get(v).equals(depthsDFS.get(v)) && parentDFS.get(v) > 0)
                bridges.add(new Pair<Integer, Integer>(v, parentDFS.get(v)));

        return bridges;
    }

    /**
     * Algorytm DFS wyliczający funkcję LOW.
     * @param vertex aktualny wierzchołek
     */
    private void dfs(int vertex)
    {
        depthsDFS.set(vertex, depthsDFS.get(parentDFS.get(vertex)) + 1);
        valuesLOW.set(vertex, depthsDFS.get(vertex));

        for(Integer neghbour : graph.getNeighbours(vertex))
            if(depthsDFS.get(neghbour) == NO_VALUE)
            {
                parentDFS.set(neghbour, vertex);
                dfs(neghbour);
                valuesLOW.set(vertex, Math.min(valuesLOW.get(vertex), valuesLOW.get(neghbour)));
            }
            else if(!neghbour.equals(parentDFS.get(vertex)))
                valuesLOW.set(vertex, Math.min(valuesLOW.get(vertex), depthsDFS.get(neghbour)));
    }
}
