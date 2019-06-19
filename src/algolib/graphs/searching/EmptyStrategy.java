package algolib.graphs.searching;

public class EmptyStrategy
        implements SearchingStrategy
{
    @Override
    public void preprocess(int vertex)
    {
    }

    @Override
    public void postprocess(int vertex)
    {
    }

    @Override
    public void onCycle(int vertex, int neighbour)
    {
    }
}
