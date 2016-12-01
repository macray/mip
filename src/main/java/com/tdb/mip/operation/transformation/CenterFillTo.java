package com.tdb.mip.operation.transformation;

import com.tdb.mip.util.DimensionUtils;
import com.tdb.mip.service.pixelrounding.PixelRounding;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CenterFillTo implements Transformation {

    private static final Logger LOGGER = LoggerFactory.getLogger(CenterFillTo.class);

    private int targetW;
    private int targetH;
    private int iconW;
    private int iconH;
    private Color color;
    private PixelRounding pixelRounding;

    public CenterFillTo(int iconW, int iconH, int targetW, int targetH, Color color, PixelRounding pixelRounding) {
        this.targetH = targetH;
        this.targetW = targetW;
        this.iconW = iconW;
        this.iconH = iconH;
        this.color = color;
        this.pixelRounding = pixelRounding;
    }

    @Override
    public BufferedImage applyTo(BufferedImage image) {
        LOGGER.debug("CenterFillTo to");

        int w = image.getWidth();
        int h = image.getHeight();

        if(iconH == -1) {
            iconH = DimensionUtils.computeHeightBasedOn(pixelRounding, w, h, iconW);
        }
        if(iconW == -1) {
            iconW = DimensionUtils.computeHeightBasedOn(pixelRounding, w, h, iconH);
        }
        if(targetH == -1) {
            targetH = DimensionUtils.computeHeightBasedOn(pixelRounding, w, h, targetW);
        }
        if(targetW == -1) {
            targetW = DimensionUtils.computeHeightBasedOn(pixelRounding, w, h, targetH);
        }

        if (targetW < w || targetH < h) {
        //    throw new DownscalingNotSupportedException("targetW=" + targetW + " targetH=" + targetH + " image.H=" + h + " image.W=" + w);
        }

        System.out.println("tW=" + targetW + " tH="+ targetH + " iW=" + iconW + " iH=" + iconH);
        BufferedImage targetImage = new BufferedImage(targetW, targetH, image.getType());
        // copy the original image to the center of the new one
        int xShift = (int) ((targetW - iconW) / 2f);
        int yShift = (int) ((targetH - iconH) / 2f);

        // resize image as icon
        BufferedImage icon = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, iconW, iconH);

        Graphics2D g2 = targetImage.createGraphics();
        // set background color
        g2.setColor(color);
        g2.fillRect(0, 0, targetW, targetH);
        // copy image
        g2.drawImage(icon, null, xShift, yShift);
        g2.dispose();

        return targetImage;
    }

    @Override
    public Transformation copy() {
        return new CenterFillTo(iconW, iconH, targetW, targetH, color, pixelRounding);
    }
}
