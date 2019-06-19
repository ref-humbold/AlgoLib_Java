package refhumbold.algolib.graphs.exceptions;

public class NotConnectedException
        extends RuntimeException
{
    private static final long serialVersionUID = -4449445606188049718L;

    public NotConnectedException(String s)
    {
        super(s);
    }
}
