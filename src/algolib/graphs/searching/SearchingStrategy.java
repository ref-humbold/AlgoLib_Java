package algolib.graphs.searching;

public interface SearchingStrategy
{
    void preprocess(int vertex);

    void forNeighbour(int vertex, int neighbour);

    void postprocess(int vertex);

    void onCycle(int vertex, int neighbour);
}
