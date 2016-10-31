package com.tdb.mip.operation.transformation;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class BlackAndWhite implements Transformation {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlackAndWhite.class);

    @Override
    public BufferedImage applyTo(BufferedImage image) {
        LOGGER.debug("black and white");
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        return Scalr.apply(image, op);
    }

    @Override
    public Transformation copy() {
        return new BlackAndWhite();
    }
}
