package com.tdb.mip.writer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class PNGImageWriter implements ImageWriter {

    /*
     * (non-Javadoc)
     *
     * @see com.tdb.mip.writer.Saver#writer(java.awt.image.BufferedImage,
     * java.io.File)
     */
    @Override
    public void save(BufferedImage image, File file) throws IOException {
        // create parent folder if needed
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }

        ImageIO.write(image, "png", file);
    }

}
