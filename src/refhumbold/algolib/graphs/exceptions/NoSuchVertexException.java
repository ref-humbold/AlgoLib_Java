package refhumbold.algolib.graphs.exceptions;

public class NoSuchVertexException
        extends RuntimeException
{
    private static final long serialVersionUID = -5380156876701376673L;

    public NoSuchVertexException(String s)
    {
        super(s);
    }
}
