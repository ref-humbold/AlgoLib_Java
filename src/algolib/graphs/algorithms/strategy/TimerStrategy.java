package algolib.graphs.algorithms.strategy;

import java.util.HashMap;
import java.util.Map;

import algolib.graphs.Vertex;

public class TimerStrategy<V>
        implements DFSStrategy<V>
{
    private final Map<Vertex<V>, Integer> preTimes = new HashMap<>();
    private final Map<Vertex<V>, Integer> postTimes = new HashMap<>();
    private int timer = 1;

    public int getPreTime(Vertex<V> vertex)
    {
        return preTimes.get(vertex);
    }

    public int getPostTime(Vertex<V> vertex)
    {
        return postTimes.get(vertex);
    }

    @Override
    public void forRoot(Vertex<V> root)
    {
    }

    @Override
    public void preProcess(Vertex<V> vertex)
    {
        preTimes.put(vertex, timer);
        ++timer;
    }

    @Override
    public void forNext(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }

    @Override
    public void postProcess(Vertex<V> vertex)
    {
        postTimes.put(vertex, timer);
        ++timer;
    }

    @Override
    public void forVisited(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }
}
