package com.github.refhumbold.algolib.geometry;

import java.util.Comparator;

public class GeometryComparator
        implements Comparator<Double>
{
    private static final double EPSILON = 1e-12;

    @Override
    public int compare(Double d1, Double d2)
    {
        return Math.abs(d1 - d2) < EPSILON ? 0 : Double.compare(d1, d2);
    }
}
