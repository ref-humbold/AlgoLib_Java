package ref_humbold.algolib.structures;

public class NoSolutionException
    extends Exception
{
    private static final long serialVersionUID = 6507998164764315665L;

    public NoSolutionException()
    {
        super();
    }

    public NoSolutionException(String s)
    {
        super(s);
    }

    public NoSolutionException(Throwable t)
    {
        super(t);
    }

    public NoSolutionException(String s, Throwable t)
    {
        super(s, t);
    }

    public NoSolutionException(String s, Throwable t, boolean b1, boolean b2)
    {
        super(s, t, b1, b2);
    }

}
