package com.tdb.mip.density;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DensityUtils {

    public static float getRatio(Density source, Density target) {
        return target.getRatio() / source.getRatio();
    }

    public static Density valueOf(List<Density> allDensitiesForPlatform, String densityHumanName) {
        for (Density d : allDensitiesForPlatform) {
            if (d.getHumanName().equalsIgnoreCase(densityHumanName)) {
                return d;
            }
        }

        return null;
    }

    public static Density findHighestDensity(Collection<Density> densities) {
        Density highestDensity = null;
        for (Density density : densities) {
            if (highestDensity == null) {
                highestDensity = density;
            }
            if (density.getRatio() > highestDensity.getRatio()) {
                highestDensity = density;
            }
        }

        return highestDensity;
    }

    public static Density closestUpperDensity(List<Density> allDensitiesForPlatform, Density refDensity) {
        // make a sub list with all the higher densities
        List<Density> higherDensity = allDensitiesForPlatform.stream()
                .filter(d -> d.getRatio() > refDensity.getRatio())
                .collect(Collectors.toList());

        if (!higherDensity.isEmpty()) {
            // if not empty find the min in the sub list
            Density min = higherDensity.get(0);
            for (Density d : higherDensity) {
                if (min.getRatio() > d.getRatio()) {
                    min = d;
                }
            }
            return min;
        } else {
            // if empty return the density itself
            return refDensity;
        }

    }

}
