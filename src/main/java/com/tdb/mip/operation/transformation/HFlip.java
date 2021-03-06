package com.tdb.mip.operation.transformation;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class HFlip implements Transformation {

    private static final Logger LOGGER = LoggerFactory.getLogger(HFlip.class);

    @Override
    public BufferedImage applyTo(BufferedImage image) {
        LOGGER.debug("horizontal flip");
        return Scalr.rotate(image, Rotation.FLIP_HORZ);
    }

    @Override
    public Transformation copy() {
        return new HFlip();
    }
}
