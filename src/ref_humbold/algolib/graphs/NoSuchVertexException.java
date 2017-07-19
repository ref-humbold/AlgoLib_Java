package ref_humbold.algolib.graphs;

public class NoSuchVertexException
    extends RuntimeException
{
    private static final long serialVersionUID = -5380156876701376673L;

    public NoSuchVertexException()
    {
        super();
    }

    public NoSuchVertexException(String s)
    {
        super(s);
    }

    public NoSuchVertexException(Throwable t)
    {
        super(t);
    }

    public NoSuchVertexException(String s, Throwable t)
    {
        super(s, t);
    }
}
