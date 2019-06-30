package algolib.graphs.searching;

public interface SearchingStrategy
{
    void preprocess(int vertex);

    void postprocess(int vertex);

    void onCycle(int vertex, int neighbour);
}
