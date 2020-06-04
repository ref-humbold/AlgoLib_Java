package algolib.graphs.algorithms;

public class DirectedCyclicGraphException
        extends RuntimeException
{
    private static final long serialVersionUID = 3228575376475745210L;

    public DirectedCyclicGraphException(String s)
    {
        super(s);
    }
}
