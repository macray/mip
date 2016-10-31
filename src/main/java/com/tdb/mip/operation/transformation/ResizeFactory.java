package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationDescription;
import com.tdb.mip.service.pixelrounding.PixelRounding;
import com.tdb.mip.service.pixelrounding.PixelRoundingHalfDown;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ResizeFactory implements TransformationFactory<Resize> {

    private static final Pattern PATTERN_RESIZE_FILTER_WH = Pattern.compile("w([0-9]+)h([0-9]+)");
    private static final Pattern PATTERN_RESIZE_FILTER_HW = Pattern.compile("h([0-9]+)w([0-9]+)");
    private static final Pattern PATTERN_RESIZE_FILTER_H = Pattern.compile("h([0-9]+)");
    private static final Pattern PATTERN_RESIZE_FILTER_W = Pattern.compile("w([0-9]+)");

    private Resize createResizeFilter(OperationDescription description, PixelRounding pixelRounding) {
        int w = -1;
        int h = -1;

        String rawConfig = description.getTextDescription();
        Matcher matcher = PATTERN_RESIZE_FILTER_HW.matcher(rawConfig);
        if (matcher.matches()) {
            h = Integer.parseInt(matcher.group(1));
            w = Integer.parseInt(matcher.group(2));
            return new Resize(w, h, pixelRounding);
        }

        matcher = PATTERN_RESIZE_FILTER_WH.matcher(rawConfig);
        if (matcher.matches()) {
            h = Integer.parseInt(matcher.group(2));
            w = Integer.parseInt(matcher.group(1));
            return new Resize(w, h, pixelRounding);
        }

        matcher = PATTERN_RESIZE_FILTER_H.matcher(rawConfig);
        if (matcher.matches()) {
            h = Integer.parseInt(matcher.group(1));
            return new Resize(w, h, pixelRounding);
        }

        matcher = PATTERN_RESIZE_FILTER_W.matcher(rawConfig);
        if (matcher.matches()) {
            w = Integer.parseInt(matcher.group(1));
            return new Resize(w, h, pixelRounding);
        }

        throw new RuntimeException("WTF? incorrect resize parameter " + rawConfig);
    }

    @Override
    public boolean match(OperationDescription description) {
        String config = description.getTextDescription();
        boolean match = PATTERN_RESIZE_FILTER_HW.matcher(config).matches() //
                || PATTERN_RESIZE_FILTER_WH.matcher(config).matches() //
                || PATTERN_RESIZE_FILTER_H.matcher(config).matches() //
                || PATTERN_RESIZE_FILTER_W.matcher(config).matches(); //
        return match;
    }

    @Override
    public Resize build(OperationDescription description) {
        return createResizeFilter(description, new PixelRoundingHalfDown());
    }
}
