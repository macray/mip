package com.tdb.mip.service.writer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public interface ImageWriter {

    void save(BufferedImage image, Path file) throws IOException;

}