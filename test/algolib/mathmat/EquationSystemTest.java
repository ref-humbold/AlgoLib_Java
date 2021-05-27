package algolib.mathmat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Structure of linear equation system (with Gauss elimination algorithm)
public class EquationSystemTest
{
    @Test
    public void solve_WhenSingleSolution_ThenSolution()
    {
        // given
        EquationSystem testObject = EquationSystem.of(Equation.of(new double[]{2, 3, -2}, 15),
                                                      Equation.of(new double[]{7, -1, 0}, 4),
                                                      Equation.of(new double[]{-1, 6, 4}, 9));
        // when
        double[] result = new double[0];

        try
        {
            result = testObject.solve();
        }
        catch(InfiniteSolutionsException | NoSolutionException e)
        {
            Assertions.fail(
                    String.format("Unexpected exception: %s", e.getClass().getSimpleName()));
        }

        // then
        Assertions.assertThat(result).containsExactly(1, 3, -2);
        Assertions.assertThat(testObject.isSolution(result)).isTrue();
        Assertions.assertThat(testObject.isSolution(new double[]{-2, -18, -36.5})).isFalse();
    }

    @Test
    public void solve_WhenNoSolution_ThenNoSolutionException()
    {
        // given
        EquationSystem testObject = EquationSystem.of(Equation.of(new double[]{2, 3, -2}, 15),
                                                      Equation.of(new double[]{7, -1, 0}, 4),
                                                      Equation.of(new double[]{-1, -1.5, 1}, -1));

        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.solve());
        // then
        Assertions.assertThat(throwable).isInstanceOf(NoSolutionException.class);
        Assertions.assertThat(testObject.isSolution(new double[]{1, 3, -2})).isFalse();
        Assertions.assertThat(testObject.isSolution(new double[]{-2, -18, -36.5})).isFalse();
    }

    @Test
    public void solve_WhenInfiniteSolutions_ThenInfiniteSolutionsException()
    {
        // given
        EquationSystem testObject = EquationSystem.of(Equation.of(new double[]{2, 3, -2}, 15),
                                                      Equation.of(new double[]{7, -1, 0}, 4),
                                                      Equation.of(new double[]{4, 6, -4}, 30));

        // when
        Throwable throwable = Assertions.catchThrowable(() -> testObject.solve());
        // then
        Assertions.assertThat(throwable).isInstanceOf(InfiniteSolutionsException.class);
        Assertions.assertThat(testObject.isSolution(new double[]{1, 3, -2})).isTrue();
        Assertions.assertThat(testObject.isSolution(new double[]{-2, -18, -36.5})).isTrue();
    }
}
