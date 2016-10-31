package com.tdb.mip.service.filenamebuilder;

import com.tdb.mip.service.filenameparser.FileNameParser;
import com.tdb.mip.density.Density;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mcy on 30/10/2016.
 */
public class AndroidOutputFileNameBuilder implements OutputFileNameBuilder {
    @Override
    public Path buildOutputFileName(Path outputDir, Density density, FileNameParser.FileNameInfo inputFileNameInfo) {
        Path path = Paths.get(outputDir.toString(), "drawable-" + density.getSuffix(), inputFileNameInfo.getBaseName() + ".png");
        return path;
    }
}
