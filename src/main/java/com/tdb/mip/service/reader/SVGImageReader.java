package com.tdb.mip.service.reader;

import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.service.reader.ImageReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mcy on 29/10/2016.
 */
@Slf4j
public class SVGImageReader implements ImageReader {
    private int width = -1;
    private int height = -1;

    private PipelineConfig config;

    @Inject
    public SVGImageReader(PipelineConfig config) {
        this.config = config;
    }

    @Override
    @SneakyThrows
    public BufferedImage read(Path path) {
        Path tempFile = Files.createTempFile("inkscape", "tmp");
        List<String> commands = new LinkedList<>();

        commands.add(config.inkscapePath());
        commands.add("--without-gui");
        commands.add("--export-area-page");
        commands.add("--export-png=" + tempFile.toAbsolutePath().normalize());
        if (width != -1 && height != -1) {
            commands.add("--export-width=" + Integer.toString(width));
            commands.add("--export-height=" + Integer.toString(height));
        }
        commands.add(path.toAbsolutePath().normalize().toString());

        ProcessBuilder pb = new ProcessBuilder(commands);

        Process p = pb.start();
        p.waitFor(20, TimeUnit.SECONDS);

        String output = IOUtils.toString(pb.start().getInputStream(), StandardCharsets.UTF_8);
        if (StringUtils.isNoneBlank(output)) {
            log.info("Inkscape output: " + output);
        }

        String error = IOUtils.toString(pb.start().getErrorStream(), StandardCharsets.UTF_8);
        if (StringUtils.isNoneBlank(error)) {
            log.error("Inkscape error: " + error);
        }

        BufferedImage bufferedImage = ImageIO.read(tempFile.toFile());

        Files.deleteIfExists(tempFile);
        return bufferedImage;
    }

    @Override
    public boolean supportResizing() {
        return true;
    }

    @Override
    public void setOutputSize(int w, int h) {
        width = w;
        height = h;
    }

    @Override
    @SneakyThrows
    public Size readSize(Path path) {
        Size size = new Size();
        List<String> commands = new LinkedList<>();
        commands.add(config.inkscapePath());
        commands.add("--without-gui");
        commands.add("--query-all");
        commands.add(path.toAbsolutePath().normalize().toString());

        ProcessBuilder pb = new ProcessBuilder(commands);

        Process p = pb.start();
        p.waitFor(20, TimeUnit.SECONDS);

        String output = IOUtils.toString(pb.start().getInputStream(), StandardCharsets.UTF_8);
        String firstLine = StringUtils.substringBefore(output, System.lineSeparator());
        String[] split = StringUtils.split(firstLine, ',');
        size.setWidth(Float.parseFloat(split[3]));
        size.setHeight(Float.parseFloat(split[4]));

        if (StringUtils.isNoneBlank(output)) {
            log.debug("Inkscape output: " + output);
        }

        String error = IOUtils.toString(pb.start().getErrorStream(), StandardCharsets.UTF_8);
        if (StringUtils.isNoneBlank(error)) {
            log.error("Inkscape error: " + error);
        }

        return size;
    }
}
