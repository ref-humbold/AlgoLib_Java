package refhumbold.algolib.structures;

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
}
