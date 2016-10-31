package com.tdb.mip.service.filenamebuilder;

import com.tdb.mip.service.filenameparser.FileNameParser;
import com.tdb.mip.density.Density;

import java.nio.file.Path;

/**
 * Created by mcy on 30/10/2016.
 */
public interface OutputFileNameBuilder {
    Path buildOutputFileName(Path outputDir, Density density, FileNameParser.FileNameInfo inputFileNameInfo);
}
