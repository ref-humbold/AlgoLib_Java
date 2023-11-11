package algolib.graphs;

import java.io.Serial;

public class GraphPartitionException
        extends RuntimeException
{
    @Serial private static final long serialVersionUID = -2107505164132960758L;

    public GraphPartitionException(String s)
    {
        super(s);
    }
}
