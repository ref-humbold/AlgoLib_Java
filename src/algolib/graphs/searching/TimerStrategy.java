package algolib.graphs.searching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algolib.graphs.Graph;

public class TimerStrategy
        implements SearchingStrategy
{
    private int timer;
    private List<Integer> preTimes;
    private List<Integer> postTimes;

    public TimerStrategy(Graph graph)
    {
        timer = 1;
        preTimes = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), 0));
        postTimes = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), 0));
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
    public void forNeighbour(int vertex, int neighbour)
    {
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
