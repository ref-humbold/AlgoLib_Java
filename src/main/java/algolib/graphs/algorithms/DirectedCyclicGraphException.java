package algolib.graphs.algorithms;

import java.io.Serial;

public class DirectedCyclicGraphException
        extends RuntimeException
{
    @Serial private static final long serialVersionUID = 3228575376475745210L;

    public DirectedCyclicGraphException(String s)
    {
        super(s);
    }
}
