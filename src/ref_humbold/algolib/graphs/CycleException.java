package ref_humbold.algolib.graphs;

public class CycleException
    extends RuntimeException
{
    private static final long serialVersionUID = -4104441005709091878L;

    public CycleException()
    {
        super();
    }

    public CycleException(String s)
    {
        super(s);
    }

    public CycleException(Throwable t)
    {
        super(t);
    }

    public CycleException(String s, Throwable t)
    {
        super(s, t);
    }

    public CycleException(String s, Throwable t, boolean b1, boolean b2)
    {
        super(s, t, b1, b2);
    }
}
