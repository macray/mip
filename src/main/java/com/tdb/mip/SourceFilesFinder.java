package com.tdb.mip;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
public interface SourceFilesFinder {
    List<Path> findFiles();
}
