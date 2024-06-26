package algolib.maths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Structure of linear equations system.
public class EquationSystemTest
{
    @Test
    public void toString_ThenStringRepresentation()
    {
        // given
        var testObject = EquationSystem.of(Equation.of(new double[]{2.0, 3.0, -2.0}, 15),
                                           Equation.of(new double[]{7.0, -1.0, 0.0}, 4),
                                           Equation.of(new double[]{-1.0, 6.0, 4.0}, 9));

        // when
        String result = testObject.toString();

        // then
        Assertions.assertThat(result)
                  .isEqualTo("{ 2 x_0 + 3 x_1 + -2 x_2 = 15 ; 7 x_0 + -1 x_1 = 4 ; "
                                     + "-1 x_0 + 6 x_1 + 4 x_2 = 9 }");
    }

    @Test
    public void solve_WhenSingleSolution_ThenSolution()
            throws Exception
    {
        // given
        var testObject = EquationSystem.of(Equation.of(new double[]{2, 3, -2}, 15),
                                           Equation.of(new double[]{7, -1, 0}, 4),
                                           Equation.of(new double[]{-1, 6, 4}, 9));

        // whens
        double[] result = testObject.solve();

        // then
        Assertions.assertThat(result).containsExactly(1, 3, -2);
        Assertions.assertThat(testObject.hasSolution(result)).isTrue();
        Assertions.assertThat(testObject.hasSolution(new double[]{-2, -18, -36.5})).isFalse();
    }

    @Test
    public void solve_WhenNoSolution_ThenNoSolutionException()
    {
        // given
        var testObject = EquationSystem.of(Equation.of(new double[]{2, 3, -2}, 15),
                                           Equation.of(new double[]{7, -1, 0}, 4),
                                           Equation.of(new double[]{-1, -1.5, 1}, -1));

        // then
        Assertions.assertThatThrownBy(testObject::solve).isInstanceOf(NoSolutionException.class);
        Assertions.assertThat(testObject.hasSolution(new double[]{1, 3, -2})).isFalse();
        Assertions.assertThat(testObject.hasSolution(new double[]{-2, -18, -36.5})).isFalse();
    }

    @Test
    public void solve_WhenInfiniteSolutions_ThenInfiniteSolutionsException()
    {
        // given
        var testObject = EquationSystem.of(Equation.of(new double[]{2, 3, -2}, 15),
                                           Equation.of(new double[]{7, -1, 0}, 4),
                                           Equation.of(new double[]{4, 6, -4}, 30));

        // then
        Assertions.assertThatThrownBy(testObject::solve)
                  .isInstanceOf(InfiniteSolutionsException.class);
        Assertions.assertThat(testObject.hasSolution(new double[]{1, 3, -2})).isTrue();
        Assertions.assertThat(testObject.hasSolution(new double[]{-2, -18, -36.5})).isTrue();
    }

    @Test
    public void swap_ThenEquationsSwapped()
    {
        // given
        var testObject = EquationSystem.of(Equation.of(new double[]{2, 3, -2}, 15),
                                           Equation.of(new double[]{7, -1, 0}, 4),
                                           Equation.of(new double[]{-1, 6, 4}, 9));

        // when
        testObject.swap(0, 2);

        // then
        Assertions.assertThat(testObject.getEquation(0)).hasToString("-1 x_0 + 6 x_1 + 4 x_2 = 9");
        Assertions.assertThat(testObject.getEquation(2)).hasToString("2 x_0 + 3 x_1 + -2 x_2 = 15");
    }
}
