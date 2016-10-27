package com.tdb.mip.config;

import com.tdb.mip.Platform;
import com.tdb.mip.density.Density;
import org.aeonbits.owner.Config;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by mcy on 27/10/2016.
 */
public interface PipelineConfig extends Config {

    @Key("source.files.recursive")
    @DefaultValue("true")
    boolean recursiveLoad();

    @Key("source.files.ignoredFilesPatterns")
    @DefaultValue("\\._.*, \\.DS_Store")
    @ConverterClass(PatternConverter.class)
    List<Pattern> ignoredFilesPatterns();

    @Key("source.files.allowedExtensions")
    @DefaultValue("jpg, jpeg, png, svg")
    List<String> allowedExtensions();

    @Key("platform")
    Platform platform();

    @Key("source.density")
    @ConverterClass(DensityConverter.class)
    Density sourceDensity();

    @Key("source.dir")
    @ConverterClass(PathConverter.class)
    Path sourceDir();

    @Key("target.dir")
    @ConverterClass(PathConverter.class)
    Path targetDir();

    @Key("target.densities")
    @ConverterClass(DensityConverter.class)
    List<Density> targetDensities();
}
