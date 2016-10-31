package com.tdb.mip.service.filenamebuilder;

import com.tdb.mip.service.filenameparser.FileNameParser;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.IOSDensity;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mcy on 30/10/2016.
 */
public class IOSOutFileNameBuilder implements OutputFileNameBuilder {
    @Override
    public Path buildOutputFileName(Path outputDir, Density density, FileNameParser.FileNameInfo inputFileNameInfo) {
        String fileName;
        if (density.equals(IOSDensity.X1)) {
            fileName = inputFileNameInfo.getBaseName() + ".png";
        } else {
            fileName = inputFileNameInfo.getBaseName() + density.getSuffix() + ".png";
        }
        Path path = Paths.get(outputDir.toString(), fileName);
        return path;
    }
}
