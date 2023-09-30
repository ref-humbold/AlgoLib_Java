package algolib.text;

import java.util.stream.IntStream;

/** Algorithms for edit distance */
public final class EditDistance
{
    /**
     * Computes cost of Levenshtein edit distance between given texts.
     * @param source the initial text
     * @param destination the final text
     * @return the cost of edit distance
     */
    public static double countLevenshtein(String source, String destination)
    {
        return countLevenshtein(source, destination, 1.0, 1.0, 1.0);
    }

    /**
     * Computes cost of Levenshtein edit distance between given texts.
     * @param source the initial text
     * @param destination the final text
     * @param insertionCost the cost of insertion operation
     * @param deletionCost the cost of deletion operation
     * @param substitutionCost the cost of substitution operation
     * @return the cost of edit distance
     */
    public static double countLevenshtein(
            String source,
            String destination,
            double insertionCost,
            double deletionCost,
            double substitutionCost)
    {
        if(insertionCost < 0 || deletionCost < 0 || substitutionCost < 0)
            throw new IllegalArgumentException("Cost cannot be negative");

        double[] distance = IntStream.range(0, destination.length() + 1)
                                     .mapToDouble(i -> i * insertionCost)
                                     .toArray();

        for(char element : source.toCharArray())
        {
            double previousAbove = distance[0];

            distance[0] += deletionCost;

            for(int i = 0; i < destination.length(); ++i)
            {
                double previousDiagonal = previousAbove;

                previousAbove = distance[i + 1];
                distance[i + 1] = element == destination.charAt(i)
                                  ? previousDiagonal
                                  : Math.min(Math.min(previousAbove + deletionCost,
                                                      distance[i] + insertionCost),
                                             previousDiagonal + substitutionCost);
            }
        }

        return distance[distance.length - 1];
    }

    /**
     * Computes cost of LCS edit distance between given texts.
     * @param source the initial text
     * @param destination the final text
     * @return the cost of edit distance
     */
    public static double countLcs(String source, String destination)
    {
        return countLcs(source, destination, 1.0, 1.0);
    }

    /**
     * Computes cost of LCS edit distance between given texts.
     * @param source the initial text
     * @param destination the final text
     * @param insertionCost the cost of insertion operation
     * @param deletionCost the cost of deletion operation
     * @return the cost of edit distance
     */
    public static double countLcs(
            String source, String destination, double insertionCost, double deletionCost)
    {
        if(insertionCost < 0 || deletionCost < 0)
            throw new IllegalArgumentException("Cost cannot be negative");

        double[] distance = IntStream.range(0, destination.length() + 1)
                                     .mapToDouble(i -> i * insertionCost)
                                     .toArray();

        for(char element : source.toCharArray())
        {
            double previousAbove = distance[0];

            distance[0] += deletionCost;

            for(int i = 0; i < destination.length(); ++i)
            {
                double previousDiagonal = previousAbove;

                previousAbove = distance[i + 1];
                distance[i + 1] = element == destination.charAt(i)
                                  ? previousDiagonal
                                  : Math.min(previousAbove + deletionCost,
                                             distance[i] + insertionCost);
            }
        }

        return distance[distance.length - 1];
    }

    /**
     * Computes cost of Hamming edit distance between given texts of equal length.
     * @param source the initial text
     * @param destination the final text
     * @return the cost of edit distance
     */
    public static double countHamming(String source, String destination)
    {
        return countHamming(source, destination, 1.0);
    }

    /**
     * Computes cost of Hamming edit distance between given texts of equal length.
     * @param source the initial text
     * @param destination the final text
     * @param substitutionCost the cost of substitution operation
     * @return the cost of edit distance
     */
    public static double countHamming(String source, String destination, double substitutionCost)
    {
        if(substitutionCost < 0)
            throw new IllegalArgumentException("Cost cannot be negative");

        if(source.length() != destination.length())
            throw new IllegalArgumentException("Texts should have equal length");

        return IntStream.range(0, source.length())
                        .filter(i -> source.charAt(i) != destination.charAt(i))
                        .mapToDouble(i -> substitutionCost)
                        .sum();
    }
}
