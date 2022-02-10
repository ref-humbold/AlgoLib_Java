// Tests: Algorithm for counting diameter of a tree
package algolib.graphs.algorithms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import algolib.graphs.TreeGraph;
import algolib.graphs.properties.Weighted;

public class TreeDiameterTest
{
    @Test
    public void findDiameter_WhenOneVertex_ThenZero()
    {
        // given
        TreeGraph<Integer, Void, Weight> tree = new TreeGraph<>(0);
        // when
        double result = TreeDiameter.findDiameter(tree);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void findDiameter_WhenAllWeightsEqual_ThenDiameterLength()
    {
        // given
        Weight weight = new Weight(1);
        TreeGraph<Integer, Void, Weight> tree = new TreeGraph<>(0);
        tree.addVertex(1, tree.getVertex(0), null, weight);
        tree.addVertex(2, tree.getVertex(0), null, weight);
        tree.addVertex(3, tree.getVertex(1), null, weight);
        tree.addVertex(4, tree.getVertex(1), null, weight);
        tree.addVertex(5, tree.getVertex(1), null, weight);
        tree.addVertex(6, tree.getVertex(2), null, weight);
        tree.addVertex(7, tree.getVertex(4), null, weight);
        tree.addVertex(8, tree.getVertex(6), null, weight);
        tree.addVertex(9, tree.getVertex(6), null, weight);
        // when
        double result = TreeDiameter.findDiameter(tree);
        // then
        Assertions.assertThat(result).isEqualTo(6);
    }

    @Test
    public void findDiameter_WhenEdgeWithBigWeight_ThenLongestPath()
    {
        // given
        TreeGraph<Integer, Void, Weight> tree = new TreeGraph<>(0);
        tree.addVertex(1, tree.getVertex(0), null, new Weight(1000));
        tree.addVertex(2, tree.getVertex(1), null, new Weight(10));
        tree.addVertex(3, tree.getVertex(1), null, new Weight(10));
        tree.addVertex(4, tree.getVertex(2), null, new Weight(5));
        tree.addVertex(5, tree.getVertex(3), null, new Weight(5));
        // when
        double result = TreeDiameter.findDiameter(tree);
        // then
        Assertions.assertThat(result).isEqualTo(1015);
    }

    private static final class Weight
            implements Weighted
    {
        private final double weight;

        private Weight(double weight)
        {
            this.weight = weight;
        }

        @Override
        public double getWeight()
        {
            return weight;
        }
    }
}
