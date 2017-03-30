// ALGORYTM: ZNAJDOWANIE NAJNIŻSZEGO WSPÓLNEGO PRZODKA DWÓCH WIERZCHOŁKÓW W DRZEWIE
package ref_humbold.algolib.graphs;

import java.lang.Math;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

class TreeGraph
{
    /** Skompresowane ścieżki do korzenia drzewa. */
    private List< List<Integer> > paths;

    /** Tablica czasów wejścia i wyjścia dla wierzchołka. */
    private List< Pair<Integer, Integer> > prePostTimes;

    /** Liczba wierzchołków grafu. */
    private int verticesNumber;

    /** Czy wierzchołek odwiedzony. */
    private List<Boolean> isVisited;

    /** Lista sąsiedztwa grafu. */
    private List< List<Integer> > graphrepr;

    public TreeGraph(int n)
    {
        verticesNumber = n;
        isVisited = new ArrayList<Boolean>();
        graphrepr = new ArrayList< List<Integer> >();

        for(int i = 0; i <= n; ++i)
        {
            isVisited.add(false);
            graphrepr.add(new ArrayList<Integer>());
        }
    }

    /**
    Znajduje najniższego wspólnego przodka.
    @param vertex1 wierzchołek 1
    @param vertex2 wierzchołek 2
    @return najniższy wspólny przodek
    */
    public int findLca(int vertex1, int vertex2)
    {
    	return findLca(vertex1, vertex2, 1);
    }

    /**
    Znajduje najniższego wspólnego przodka.
    @param vertex1 wierzchołek 1
    @param vertex2 wierzchołek 2
    @param root korzeń drzewa
    @return najniższy wspólny przodek
    */
    public int findLca(int vertex1, int vertex2, int root)
    {
        paths = new ArrayList< List<Integer> >();
        prePostTimes = new ArrayList< Pair<Integer, Integer> >(Collections.nCopies(verticesNumber+1, (Pair<Integer, Integer>)null));

        for(int i = 0; i <= verticesNumber; ++i)
            paths.add(new ArrayList<Integer>());

        dfs(root, root, 0);

        for(int i = 1; i <= Math.log(verticesNumber)/Math.log(2)+2; ++i)
            for(int w = 1; w <= verticesNumber; ++w)
                paths.get(w).add(paths.get(paths.get(w).get(i-1)).get(i-1));

        return searchLca(vertex1, vertex2);
    }

    /**
    Algorytm dfs z licznikiem czasu wyznaczający kolejne wierzchołki na ścieżce do korzenia.
    @param vertex aktualny wierzchołek
    @param parent ojciec wierzchołka
    @param timer aktualny czasu
    @return nowy czas po przetworzeniu wierzchołka
    */
    private int dfs(int vertex, int parent, int timer)
    {
        int preTime = timer;

        isVisited.set(vertex, true);

        paths.get(vertex).add(parent);
        ++timer;

        for(Integer neighbour : graphrepr.get(vertex))
            if(!isVisited.get(neighbour))
                timer = dfs(neighbour, vertex, timer);

        prePostTimes.set(vertex, new Pair<Integer, Integer>(preTime, timer));

        return timer+1;
    }

    /**
    Wyszukuje najniższego wspólnego przodka.
    @param vertex1 wierzchołek 1
    @param vertex2 wierzchołek 2
    @return najniższy wspólny przodek
    */
    private int searchLca(int vertex1, int vertex2)
    {
        if(isOffspring(vertex1, vertex2))
            return vertex2;

        if(isOffspring(vertex2, vertex1))
            return vertex1;

        for(int i = paths.get(vertex1).size()-1; i > 0; --i)
        {
            int candidate = paths.get(vertex1).get(i);

            if(!isOffspring(vertex2, candidate))
                return searchLca(candidate, vertex2);
        }

        return searchLca(paths.get(vertex1).get(0), vertex2);
    }

    /**
    Sprawdza, czy wierzchołki są potomkami.
    @param vertex1 wierzchołek 1
    @param vertex2 wierzchołek 2
    @return czy wierzchołek 1 jest potomkiem wierzchołka 2
    */
    private boolean isOffspring(int vertex1, int vertex2)
    {
        return prePostTimes.get(vertex1).first >= prePostTimes.get(vertex2).first
            && prePostTimes.get(vertex1).second <= prePostTimes.get(vertex2).second;
    }
}

