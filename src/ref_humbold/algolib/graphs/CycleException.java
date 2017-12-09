package ref_humbold.algolib.graphs;

public class CycleException
    extends RuntimeException
{
    private static final long serialVersionUID = -4104441005709091878L;

    public CycleException(String s)
    {
        super(s);
    }

    public CycleException(String s, Throwable t)
    {
        super(s, t);
    }
}
