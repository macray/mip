package com.tdb.mip.operation.transformation;

import java.awt.image.BufferedImage;

public interface Filter {

    /**
     * DEV NOTE: The filter must not alter the original image
     *
     * @param image original image
     * @return return the filtered new image
     */
    BufferedImage applyTo(BufferedImage image);

    Filter copy();
}