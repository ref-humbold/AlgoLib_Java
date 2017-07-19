package ref_humbold.algolib.graphs;

public class DirectedCyclicGraphException
    extends Exception
{
    private static final long serialVersionUID = -4104441005709091878L;

    public DirectedCyclicGraphException()
    {
        super();
    }

    public DirectedCyclicGraphException(String s)
    {
        super(s);
    }

    public DirectedCyclicGraphException(Throwable t)
    {
        super(t);
    }

    public DirectedCyclicGraphException(String s, Throwable t)
    {
        super(s, t);
    }
}
