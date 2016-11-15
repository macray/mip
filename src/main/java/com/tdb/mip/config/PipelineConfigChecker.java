package com.tdb.mip.config;

import com.tdb.mip.density.AndroidDensity;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.IOSDensity;
import com.tdb.mip.density.WindowsDensity;
import org.apache.commons.lang3.Validate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcy on 27/10/2016.
 */
public class PipelineConfigChecker {

    private PipelineConfigChecker() {
    }

    public static void check(PipelineConfig config) {
        Validate.notNull(config.platform(), "'platform' must be defined in the properties.");

        Validate.notNull(config.sourceDensity(), "'source.density' must be defined.");

        Validate.notEmpty(config.targetDensities(), "At least 1 density must be set in 'target.densities'.");

        Validate.isTrue(Files.isDirectory(config.sourceDir()), "'source.dir' -> " + config.sourceDir() + " must be a directory");

        Validate.notNull(config.inkscapePath(), "'inkscape.path' must be defined in the properties.");
        Validate.isTrue(Files.exists(config.inkscapePath()), "'inkscape.path' -> " + config.inkscapePath() + " does not exists");
        Validate.isTrue(Files.isExecutable(config.inkscapePath()), "inkscape.path' -> " + config.inkscapePath() + " must be an executable file");

        if (Files.exists(config.targetDir())) {
            Validate.isTrue(Files.isDirectory(config.targetDir()), "target.dir' -> " + config.targetDir() + " must be a directory");
        }

        // Density must come from the same platform
        List<Density> allDensitiesUsed = new ArrayList<>();
        allDensitiesUsed.add(config.sourceDensity());
        allDensitiesUsed.addAll(config.targetDensities());

        List<Density> allDensitiesForPlatform;
        switch (config.platform()) {
            case ANDROID:
                allDensitiesForPlatform = AndroidDensity.ALL;
                break;

            case IOS:
                allDensitiesForPlatform = IOSDensity.ALL;
                break;

            case WINDOWS_PHONE:
                allDensitiesForPlatform = WindowsDensity.WINDOWS_PHONE;
                break;

            default:
                throw new IllegalStateException("Unknown platform <" + config.platform() + ">");
        }

        for (Density d : allDensitiesUsed) {
            if (!allDensitiesForPlatform.contains(d)) {
                throw new IllegalStateException("Density <" + d.getHumanName() + "> is not allowed for platform <" + config.platform() + ">");
            }
        }
    }

}
