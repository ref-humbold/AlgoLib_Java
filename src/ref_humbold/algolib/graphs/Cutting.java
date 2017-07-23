// WYSZUKIWANIE MOSTÓW I PUNKTÓW ARTYKULACJI W GRAFIE
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ref_humbold.algolib.structures.Pair;

public class Cutting
{
    private static class GraphCutting
    {
        /**
         * Reprezentacja grafu nieskierowanego.
         */
        private Graph graph;

        /**
         * Ojciec wierzchołka w drzewie DFS.
         */
        private List<Integer> dfsParents;

        /**
         * Lista synów w drzewie DFS.
         */
        private List<List<Integer>> dfsChildren;

        /**
         * Głębokość wierzchołka w drzewie DFS.
         */
        private List<Integer> dfsDepths;

        /**
         * Wartości funkcji LOW dla wierzchołków.
         */
        private List<Integer> lowValues;

        public GraphCutting(UndirectedGraph graph)
        {
            this.graph = graph;
            this.dfsParents = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), null));
            this.dfsDepths = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), null));
            this.lowValues = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), null));
            this.dfsChildren = new ArrayList<>();

            for(int i = 0; i < graph.getVerticesNumber(); ++i)
                this.dfsChildren.add(new ArrayList<>());
        }

        /**
         * Znajdowanie mostów w grafie.
         * @return lista krawędzi będących mostami
         */
        public Collection<Pair<Integer, Integer>> edgeCut()
        {
            List<Pair<Integer, Integer>> bridges = new ArrayList<>();

            for(Integer v : graph.getVertices())
                if(dfsDepths.get(v) == null)
                    dfs(v, null, 0);

            for(Integer v : graph.getVertices())
                if(hasBridge(v))
                    bridges.add(
                        Pair.make(Math.min(v, dfsParents.get(v)), Math.max(v, dfsParents.get(v))));

            return bridges;
        }

        /**
         * Znajdowanie punktów artykulacji.
         * @return lista punktów artykulacji
         */
        public Collection<Integer> vertexCut()
        {
            List<Integer> separators = new ArrayList<>();

            for(Integer v : graph.getVertices())
                if(dfsDepths.get(v) == null)
                    dfs(v, null, 0);

            for(Integer v : graph.getVertices())
                if(isSeparator(v))
                    separators.add(v);

            return separators;
        }

        /**
         * Sprawdzanie, czy od wierzchołka wychodzi krawędź będąca mostem
         * @param vertex wierzchołek
         * @return czy wierzchołek incydentny z mostem
         */
        private boolean hasBridge(Integer vertex)
        {
            return lowValues.get(vertex).equals(dfsDepths.get(vertex)) && !isDFSRoot(vertex);
        }

        /**
         * Sprawdzanie wierzchołka jako punktu artykulacji.
         * @param vertex wierzchołek
         * @return czy wierzchołek to punkt artykulacji
         */
        private boolean isSeparator(Integer vertex)
        {
            if(isDFSRoot(vertex))
                return dfsChildren.get(vertex).size() > 1;

            for(Integer ch : dfsChildren.get(vertex))
                if(lowValues.get(ch) >= dfsDepths.get(vertex))
                    return true;

            return false;
        }

        /**
         * Sprawdzanie, czy wierzchołek jest korzeniem drzewa DFS
         * @return czy wierzchołek to korzeń
         */
        private boolean isDFSRoot(Integer vertex)
        {
            return dfsDepths.get(vertex) == 0;
        }

        /**
         * Algorytm DFS wyliczający funkcję LOW.
         * @param vertex aktualny wierzchołek
         * @param parent ojciec wierzchołka
         * @param depth głębokość
         */
        private void dfs(Integer vertex, Integer parent, Integer depth)
        {
            dfsParents.set(vertex, parent);
            dfsDepths.set(vertex, depth);
            lowValues.set(vertex, depth);

            for(Integer neighbour : graph.getNeighbours(vertex))
                if(dfsDepths.get(neighbour) == null)
                {
                    dfsChildren.get(vertex).add(neighbour);
                    dfs(neighbour, vertex, depth + 1);
                    lowValues.set(vertex,
                                  Math.min(lowValues.get(vertex), lowValues.get(neighbour)));
                }
                else if(!neighbour.equals(parent))
                    lowValues.set(vertex,
                                  Math.min(lowValues.get(vertex), dfsDepths.get(neighbour)));
        }
    }

    /**
     * Wyznacza mosty w grafie.
     * @return lista krawędzi będących mostami
     */
    public static Collection<Pair<Integer, Integer>> findEdgeCut(UndirectedGraph ugraph)
    {
        return new GraphCutting(ugraph).edgeCut();
    }

    /**
     * Wyznaczanie punktów artykulacji.
     * @return lista punktów artykulacji
     */
    public static Collection<Integer> findVertexCut(UndirectedGraph ugraph)
    {
        return new GraphCutting(ugraph).vertexCut();
    }
}
