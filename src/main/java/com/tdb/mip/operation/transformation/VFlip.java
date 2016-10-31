package com.tdb.mip.operation.transformation;

import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

@Slf4j
public class VFlip implements Transformation {


    @Override
    public BufferedImage applyTo(BufferedImage image) {
        log.debug("vertical flip");
        return Scalr.rotate(image, Rotation.FLIP_VERT);
    }

    @Override
    public Transformation copy() {
        return new VFlip();
    }
}
