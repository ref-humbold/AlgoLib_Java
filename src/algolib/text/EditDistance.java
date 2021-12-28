package algolib.text;

import java.util.stream.IntStream;

public final class EditDistance
{
    public static double countLevenshtein(String source, String destination)
    {
        return countLevenshtein(source, destination, 1.0, 1.0, 1.0);
    }

    public static double countLevenshtein(String source, String destination, double insertionCost,
                                          double deletionCost, double substitutionCost)
    {
        double[] distance = IntStream.range(0, destination.length() + 1)
                                     .mapToDouble(i -> i * insertionCost)
                                     .toArray();

        for(char element : source.toCharArray())
        {
            double previousAbove = distance[0];
            distance[0] = previousAbove + deletionCost;

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
}
