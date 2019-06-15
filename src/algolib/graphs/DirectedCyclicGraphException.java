package algolib.graphs;

public class DirectedCyclicGraphException
        extends RuntimeException
{
    private static final long serialVersionUID = -4104441005709091878L;

    public DirectedCyclicGraphException(String s)
    {
        super(s);
    }
}
