package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationDescription;
import com.tdb.mip.pipeline.Pipeline;
import com.tdb.mip.service.pixelrounding.PixelRounding;
import com.tdb.mip.service.pixelrounding.PixelRoundingHalfDown;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CenterFillToFactory implements TransformationFactory<CenterFillTo> {

    private static final Pattern PATTERN_RESIZE_WW = Pattern.compile("centerfill_w([0-9]+)into_w([0-9]+)#([0-9a-fA-F]{8})");
    private static final Pattern PATTERN_RESIZE_HH = Pattern.compile("centerfill_h([0-9]+)into_h([0-9]+)#([0-9a-fA-F]{8})");

    private Color extractColor(String colorTxt) {
        //String colorHexa = "#" + colorTxt;
        //return Color.decode(colorInt);
        boolean hasAlpha = colorTxt.length() == 8;
        int intValue = (int) Long.parseLong(colorTxt, 16);
        return new Color(intValue, hasAlpha);
    }

    @Override
    public boolean match(OperationDescription description) {

        String config = description.getTextDescription();
        boolean match = PATTERN_RESIZE_WW.matcher(config).matches() //
                || PATTERN_RESIZE_HH.matcher(config).matches();
        return match;
    }

    @Override
    public CenterFillTo build(OperationDescription description) {
        PixelRounding pixelRounding = new PixelRoundingHalfDown();

        String rawConfig = description.getTextDescription();
        int iconAreaW;
        int iconAreaH;
        int imageW;
        int imageH;
        Color color;

        Matcher matcher = PATTERN_RESIZE_WW.matcher(rawConfig);
        if (matcher.matches()) {
            iconAreaW = Integer.parseInt(matcher.group(1));
            iconAreaH = -1;
            imageW = Integer.parseInt(matcher.group(2));
            imageH = -1;
            color = extractColor(matcher.group(3));
            return new CenterFillTo(iconAreaW, iconAreaH, imageW, imageH, color, pixelRounding);
        }

        matcher = PATTERN_RESIZE_HH.matcher(rawConfig);
        if (matcher.matches()) {
            iconAreaW = -1;
            iconAreaH = Integer.parseInt(matcher.group(1));
            imageW = -1;
            imageH = Integer.parseInt(matcher.group(2));
            color = extractColor(matcher.group(3));
            return new CenterFillTo(iconAreaW, iconAreaH, imageW, imageH, color, pixelRounding);
        }

        throw new IllegalStateException("impossible to build FillTo");

    }

}
