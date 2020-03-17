package algolib.mathmat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EquationSystemTest
{
    private EquationSystem testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new EquationSystem(new Equation[]{new Equation(new double[]{2, 3, -2}, 15),
                                                       new Equation(new double[]{7, -1, 0}, 4),
                                                       new Equation(new double[]{-1, 6, 4}, 9)});
    }

    @AfterEach
    public void tearDown()
    {
        testObject = null;
    }

    @Test
    public void solve_WhenSingleSolution_ThenSolution()
    {
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
        Assertions.assertArrayEquals(new double[]{1, 3, -2}, result);
        Assertions.assertTrue(testObject.isSolution(result));
        Assertions.assertFalse(testObject.isSolution(new double[]{-2, -18, -36.5}));
    }

    @Test
    public void solve_WhenNoSolution_ThenNoSolutionException()
    {
        // given
        testObject = new EquationSystem(new Equation[]{new Equation(new double[]{2, 3, -2}, 15),
                                                       new Equation(new double[]{7, -1, 0}, 4),
                                                       new Equation(new double[]{-1, -1.5, 1},
                                                                    -1)});

        // when - then
        Assertions.assertThrows(NoSolutionException.class, () -> testObject.solve());
        Assertions.assertFalse(testObject.isSolution(new double[]{1, 3, -2}));
        Assertions.assertFalse(testObject.isSolution(new double[]{-2, -18, -36.5}));
    }

    @Test
    public void solve_WhenInfiniteSolutions_ThenInfiniteSolutionsException()
    {
        // given
        testObject = new EquationSystem(new Equation[]{new Equation(new double[]{2, 3, -2}, 15),
                                                       new Equation(new double[]{7, -1, 0}, 4),
                                                       new Equation(new double[]{4, 6, -4}, 30)});

        // when - then
        Assertions.assertThrows(InfiniteSolutionsException.class, () -> testObject.solve());
        Assertions.assertTrue(testObject.isSolution(new double[]{1, 3, -2}));
        Assertions.assertTrue(testObject.isSolution(new double[]{-2, -18, -36.5}));
    }
}
