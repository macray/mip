package com.tdb.mip.service.reader;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

/**
 * Created by mcy on 29/10/2016.
 */
public interface ImageReader {
    BufferedImage read(Path path);

    boolean supportResizing();

    void setOutputSize(int w, int h);

    Size readSize(Path path);

    @Getter
    @Setter
    class Size {
        float height;
        float width;
    }
}
