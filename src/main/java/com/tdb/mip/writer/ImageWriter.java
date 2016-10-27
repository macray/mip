package com.tdb.mip.writer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageWriter {

    void save(BufferedImage image, File file) throws IOException;

}