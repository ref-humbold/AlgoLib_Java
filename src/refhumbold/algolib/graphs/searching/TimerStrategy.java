package refhumbold.algolib.graphs.searching;

import java.util.Collections;
import java.util.List;

public class TimerStrategy
        implements SearchingStrategy
{
    private int timer = 0;
    private List<Integer> preTime;
    private List<Integer> postTime;

    public TimerStrategy(int verticesNumber)
    {
        preTime = Collections.nCopies(verticesNumber, 0);
        postTime = Collections.nCopies(verticesNumber, 0);
    }

    public int getPreTime(int vertex)
    {
        return preTime.get(vertex);
    }

    public int getPostTime(int vertex)
    {
        return postTime.get(vertex);
    }

    @Override
    public void preprocess(int vertex)
    {
        ++timer;
        preTime.set(vertex, timer);
    }

    @Override
    public void postprocess(int vertex)
    {
        ++timer;
        postTime.set(vertex, timer);
    }
}
