// STRUKTURA GRAFU WAŻONEGO
package ref_humbold.algolib.graphs;

import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

public abstract class WeightedGraph
    implements Graph
{
    /** oznaczenie nieskończoności */
    public static double INF = Double.POSITIVE_INFINITY;

    /** Lista sąsiedztwa grafu ważonego. */
    protected List<List<Pair<Integer, Double>>> graphrepr;

    public WeightedGraph(int n)
    {
        graphrepr = new ArrayList<List<Pair<Integer, Double>>>();

        for(int i = 0; i < n; ++i)
            graphrepr.add(new ArrayList<Pair<Integer, Double>>());
    }

    /** @see Graph#getVerticesNumber */
    public int getVerticesNumber()
    {
        return graphrepr.size();
    }

    /** @see Graph#getVertices */
    public List<Integer> getVertices()
	{
		List<Integer> vertices = new ArrayList<>();

		for(int i = 0; i < getVerticesNumber(); ++i)
			vertices.add(i);

		return vertices;
	}

    /**
    Wszystkie krawędzie wraz z wagami.
    @return lista krawędzi z ich wagami
    */
    public abstract List< Pair<Pair<Integer, Integer>, Double> > getWeightedEdges();

    /** @see Graph#getNeighbours */
    public List<Integer> getNeighbours(Integer v)
    {
        List<Integer> neighbours = new ArrayList<Integer>();

        for(Pair<Integer, Double> e : graphrepr.get(v))
            neighbours.add(e.first);

        return neighbours;
    }

    /**
    Sąsiedzi wierzchołka wraz z wagami.
    @param v numer wierzchołka
    @return lista sąsiadów wierzchołka wraz z wagami krawędzi
    */
    public List< Pair<Integer, Double> > getWeightedNeighbours(Integer v)
    {
        return graphrepr.get(v);
    }

    /**
    Wyznaczanie listy sąsiedztwa grafu.
    @return lista sąsiedztwa
    */
    public List< List< Pair<Integer, Double> > > getAdjacencyList()
    {
        return graphrepr;
    }

    /** @see Graph#getOutdegree */
    public int getOutdegree(Integer v)
    {
        return graphrepr.get(v).size();
    }

    /**
    Wyznaczanie macierzy sąsiedztwa grafu.
    @return macierz sąsiedztwa
    */
    public double[][] getAdjacencyMatrix()
    {
    	int verticesNumber = getVerticesNumber();
        double[][] matrix = new double[verticesNumber][verticesNumber];

        for(int i = 0; i < verticesNumber; ++i)
        {
            for(int j = 0; j < verticesNumber; ++j)
                matrix[i][j] = INF;

            matrix[i][i] = 0.0;
        }

        for(Integer v : getVertices())
            for(Pair<Integer, Double> e : getWeightedNeighbours(v))
                matrix[v][e.first] = e.second;

        return matrix;
    }
}

