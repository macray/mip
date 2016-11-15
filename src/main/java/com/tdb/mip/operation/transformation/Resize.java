package com.tdb.mip.operation.transformation;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdb.mip.service.pixelrounding.PixelRounding;

import java.awt.image.BufferedImage;

public class Resize implements Transformation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Resize.class);

    private int w;
    private int h;
    private PixelRounding pixelRounding;

    public Resize(int w, int h, PixelRounding pixelRounding) {
        this.w = w;
        this.h = h;
        this.pixelRounding = pixelRounding;
    }

    public void updateTargetWidthOrHeightIfNeeded(int imgW, int imgH) {
        updateTargetWidthOrHeightIfNeeded((float) imgW, (float) imgH);
    }

    // Visible for testing purpose
    public void updateTargetWidthOrHeightIfNeeded(float imgW, float imgH) {
        if (w == -1 && h == -1) {
            throw new RuntimeException("need at least one constraint for resizing");
        }

        if (w == -1) {
            float ratio = imgH / (float) h;
            w = pixelRounding.round(imgW / ratio);
        }

        if (h == -1) {
            float ratio = imgW / (float) w;
            h = pixelRounding.round(imgH / ratio);
        }
    }

    @Override
    public BufferedImage applyTo(BufferedImage image) {
        updateTargetWidthOrHeightIfNeeded(image.getWidth(), image.getHeight());

        if (h > image.getHeight() || w > image.getWidth()) {
            throw new IllegalArgumentException("Upscaling is not allowed - source [w=" + image.getWidth() + "h=" + image.getHeight()
                    + "] target [w=" + w + " h=" + h + "]");
        }

        LOGGER.debug("resize to w=" + w + " h=" + h);

        return Scalr.resize(image, Method.ULTRA_QUALITY, Mode.FIT_EXACT, w,
                h);
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    @Override
    public Transformation copy() {
        return new Resize(w, h, pixelRounding);
    }
}
