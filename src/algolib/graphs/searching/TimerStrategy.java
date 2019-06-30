package algolib.graphs.searching;

import java.util.Collections;
import java.util.List;

public class TimerStrategy
        implements SearchingStrategy
{
    private int timer = 0;
    private List<Integer> preTimes;
    private List<Integer> postTimes;

    public TimerStrategy(int verticesNumber)
    {
        preTimes = Collections.nCopies(verticesNumber, 0);
        postTimes = Collections.nCopies(verticesNumber, 0);
    }

    public int getPreTime(int vertex)
    {
        return preTimes.get(vertex);
    }

    public int getPostTime(int vertex)
    {
        return postTimes.get(vertex);
    }

    @Override
    public void preprocess(int vertex)
    {
        preTimes.set(vertex, timer);
        ++timer;
    }

    @Override
    public void postprocess(int vertex)
    {
        postTimes.set(vertex, timer);
        ++timer;
    }

    @Override
    public void onCycle(int vertex, int neighbour)
    {
    }
}
