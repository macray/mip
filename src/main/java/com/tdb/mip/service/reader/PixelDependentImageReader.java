package com.tdb.mip.service.reader;

import com.tdb.mip.exception.MethodNotSupportedException;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

/**
 * Created by mcy on 29/10/2016.
 */
public class PixelDependentImageReader implements ImageReader {

    @Override
    @SneakyThrows
    public BufferedImage read(Path path) {
        return ImageIO.read(path.toFile());
    }

    @Override
    public boolean supportResizing() {
        return false;
    }

    @Override
    public void setOutputSize(int w, int h) {
        throw new MethodNotSupportedException();
    }

    @Override
    public Size readSize(Path path) {
        throw new MethodNotSupportedException();
    }
}
