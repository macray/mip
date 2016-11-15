package com.tdb.mip.config;

import com.tdb.mip.model.Platform;
import com.tdb.mip.density.Density;
import com.tdb.mip.operation.OperationDescription;
import org.aeonbits.owner.Config;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by mcy on 27/10/2016.
 */
public interface PipelineConfig extends Config {

    @Key("global.pre.transformations")
    @DefaultValue("")
    @ConverterClass(OperationDescriptionConverter.class)
    List<OperationDescription> preTransformations();

    @Key("global.post.transformations")
    @DefaultValue("")
    @ConverterClass(OperationDescriptionConverter.class)
    List<OperationDescription> postTransformations();

    @Key("inkscape.path")
    @ConverterClass(PathConverter.class)
    Path inkscapePath();

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
