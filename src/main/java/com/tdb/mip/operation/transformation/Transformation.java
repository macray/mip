package com.tdb.mip.operation.transformation;

import java.awt.image.BufferedImage;

/**
 * Created by mcy on 28/10/2016.
 */
public interface Transformation {

    /**
     * DEV NOTE: The filter must not alter the original image
     *
     * @param image original image
     * @return return the filtered new image
     */
    BufferedImage applyTo(BufferedImage image);

    Transformation copy();
}
