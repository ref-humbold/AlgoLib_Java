package ref_humbold.algolib.graphs;

public class GraphPartitionException
    extends RuntimeException
{
    private static final long serialVersionUID = -2107505164132960758L;

    public GraphPartitionException(String s)
    {
        super(s);
    }

    public GraphPartitionException(String s, Throwable t)
    {
        super(s, t);
    }
}
