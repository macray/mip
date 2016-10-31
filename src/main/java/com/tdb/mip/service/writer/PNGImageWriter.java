package com.tdb.mip.service.writer;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class PNGImageWriter implements ImageWriter {

    /*
     * (non-Javadoc)
     *
     * @see com.tdb.mip.service.writer.Saver#writer(java.awt.image.BufferedImage,
     * java.io.File)
     */
    @Override
    public void save(BufferedImage image, Path file) throws IOException {
        // create parent folder if needed
        Path parentFile = file.toAbsolutePath().normalize().getParent();
        if (parentFile != null) {
            Files.createDirectories(parentFile);
        }

        ImageIO.write(image, "png", file.toFile());
    }

}
