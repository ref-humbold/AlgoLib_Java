package algolib.graphs;

import java.util.Objects;

/** Structure of graph vertex. */
public class Vertex<VertexId>
{
    public final VertexId id;

    public Vertex(VertexId id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Vertex<?> other))
            return false;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        return "Vertex(%s)".formatted(id);
    }
}
