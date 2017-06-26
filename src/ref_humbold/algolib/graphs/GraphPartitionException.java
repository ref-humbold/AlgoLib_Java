package ref_humbold.algolib.graphs;

public class GraphPartitionException
    extends RuntimeException
{
    private static final long serialVersionUID = -2107505164132960758L;

    public GraphPartitionException()
    {
        super();
    }

    public GraphPartitionException(String s)
    {
        super(s);
    }

    public GraphPartitionException(Throwable t)
    {
        super(t);
    }

    public GraphPartitionException(String s, Throwable t)
    {
        super(s, t);
    }

    public GraphPartitionException(String s, Throwable t, boolean b1, boolean b2)
    {
        super(s, t, b1, b2);
    }
}
