package algolib.graphs.algorithms;

import algolib.graphs.Edge;
import algolib.graphs.TreeGraph;
import algolib.graphs.Vertex;
import algolib.graphs.properties.Weighted;
import algolib.tuples.Pair;

/** Algorithm for counting diameter of a tree */
public final class TreeDiameter
{
    /**
     * Computes length of diameter of given tree.
     * @param tree the tree graph
     * @return diameter length
     */
    public static <VertexId, VertexProperty, EdgeProperty extends Weighted> double countDiameter(
            TreeGraph<VertexId, VertexProperty, EdgeProperty> tree)
    {
        return tree.getVertices()
                   .stream()
                   .max((v1, v2) -> Integer.compare(tree.getOutputDegree(v1),
                                                    tree.getOutputDegree(v2)))
                   .map(root -> dfs(tree, root, root).second)
                   .orElse(0.0);
    }

    private static <VertexId, VertexProperty, EdgeProperty extends Weighted> Pair<Double, Double> dfs(
            TreeGraph<VertexId, VertexProperty, EdgeProperty> tree, Vertex<VertexId> vertex,
            Vertex<VertexId> parent)
    {
        double pathFrom = 0.0;
        double pathSubtree = 0.0;
        double pathThrough = 0.0;

        for(Edge<VertexId> edge : tree.getAdjacentEdges(vertex))
        {
            Vertex<VertexId> neighbour = edge.getNeighbour(vertex);

            if(!neighbour.equals(parent))
            {
                double weight = tree.getProperties().get(edge).getWeight();
                Pair<Double, Double> result = dfs(tree, neighbour, vertex);

                pathThrough = Math.max(pathThrough, pathFrom + result.first + weight);
                pathSubtree = Math.max(pathSubtree, result.second);
                pathFrom = Math.max(pathFrom, result.first + weight);
            }
        }

        return Pair.of(pathFrom, Math.max(pathThrough, pathSubtree));
    }
}
