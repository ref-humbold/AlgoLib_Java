package algolib.graphs.algorithms.strategy;

import java.util.HashMap;
import java.util.Map;

import algolib.graphs.Graph;
import algolib.graphs.Vertex;

public class TimerStrategy<V>
        implements SearchingStrategy<V>
{
    private final Map<Vertex<V>, Integer> preTimes = new HashMap<>();
    private final Map<Vertex<V>, Integer> postTimes = new HashMap<>();
    private int timer;

    public <E> TimerStrategy(Graph<V, E> graph)
    {
        timer = 1;
        graph.getVertices().forEach(vertex -> {
            preTimes.put(vertex, 0);
            postTimes.put(vertex, 0);
        });
    }

    public int getPreTime(Vertex<V> vertex)
    {
        return preTimes.get(vertex);
    }

    public int getPostTime(Vertex<V> vertex)
    {
        return postTimes.get(vertex);
    }

    @Override
    public void preProcess(Vertex<V> vertex)
    {
        preTimes.put(vertex, timer);
        ++timer;
    }

    @Override
    public void forNeighbour(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }

    @Override
    public void postProcess(Vertex<V> vertex)
    {
        postTimes.put(vertex, timer);
        ++timer;
    }

    @Override
    public void onCycle(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }
}
