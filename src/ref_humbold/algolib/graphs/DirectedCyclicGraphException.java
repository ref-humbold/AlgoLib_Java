package ref_humbold.algolib.graphs;

public class DirectedCyclicGraphException
    extends Exception
{
    private static final long serialVersionUID = -4104441005709091878L;

    public DirectedCyclicGraphException(String s)
    {
        super(s);
    }

    public DirectedCyclicGraphException(String s, Throwable t)
    {
        super(s, t);
    }
}
