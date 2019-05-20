package refhumbold.algolib.graphs.searching;

public interface SearchingStrategy
{
    void preprocess(int vertex);

    void postprocess(int vertex);

    void checkNeighbour(int neighbour);

    void onCycle(int vertex, int neighbour);
}
