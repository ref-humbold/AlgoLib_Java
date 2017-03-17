// STRUKTURA GRAFU PROSTEGO
package ref_humbold.algolib.graphs;

import java.util.List;
import java.util.ArrayList;

public abstract class SimpleGraph
    implements Graph
{
    /** Lista sąsiedztwa grafu. */
    protected List< List<Integer> > graphrepr;

    public SimpleGraph(int n)
    {
        graphrepr = new ArrayList< List<Integer> >();

        for(int i = 0; i < n; ++i)
            graphrepr.add(new ArrayList<Integer>());
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

    /** @see Graph#getNeighbours */
    public List<Integer> getNeighbours(Integer v)
    {
        return graphrepr.get(v);
    }
    
    /** @see Graph#getOutdegree */
    public int getOutdegree(Integer v)
    {
        return graphrepr.get(v).size();
    }

    /**
    Wyznaczanie listy sąsiedztwa grafu.
    @return lista sąsiedztwa
    */
    public List< List<Integer> > getAdjacencyList()
    {
        return graphrepr;
    }

    /**
    Wyznaczanie macierzy sąsiedztwa grafu.
    @return macierz sąsiedztwa
    */
    public boolean[][] getAdjacencyMatrix()
    {
    	int verticesNumber = getVerticesNumber();
        boolean[][] matrix = new boolean[verticesNumber][verticesNumber];

        for(int i = 0; i < verticesNumber; ++i)
            for(int j = 0; j < verticesNumber; ++j)
                matrix[i][j] = false;

        for(Integer v : getVertices())
            for( int u : getNeighbours(v) )
                matrix[v][u] = true;

        return matrix;
    }
}

