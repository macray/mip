package com.tdb.mip.service.sourcefilefinder;

import com.tdb.mip.config.PipelineConfig;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;

import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by mcy on 27/10/2016.
 */
public class SourceFilesFinderImpl implements SourceFilesFinder {

    private PipelineConfig config;

    @Inject
    public SourceFilesFinderImpl(PipelineConfig config) {
        this.config = config;
    }

    @Override
    @SneakyThrows
    public List<Path> findFiles() {
        Path sourceDir = config.sourceDir();
        int maxDepth = config.recursiveLoad() ? Integer.MAX_VALUE : 1;
        List<Path> files = Files.walk(sourceDir, maxDepth)
                .filter(f -> Files.isRegularFile(f) && isAcceptedExtension(f) && isNotIgnored(f))
                .collect(Collectors.toList());
        return files;
    }

    private boolean isNotIgnored(Path file) {
        String fileName = file.getFileName().toString();
        for (Pattern p : config.ignoredFilesPatterns()) {
            if (p.matcher(fileName).matches()) {
                return false;
            }
        }
        return true;
    }

    private boolean isAcceptedExtension(Path file) {
        String fileName = file.getFileName().toString();
        String extension = FilenameUtils.getExtension(fileName);
        return config.allowedExtensions().contains(extension);
    }
}
