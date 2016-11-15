package com.tdb.mip.service.reader;

import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.service.reader.ImageReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mcy on 29/10/2016.
 */
@Slf4j
public class SVGImageReader implements ImageReader {
    private int width = -1;
    private int height = -1;

    private PipelineConfig config;

    @Inject
    public SVGImageReader(PipelineConfig config) {
        this.config = config;
    }

    @Override
    @SneakyThrows
    public BufferedImage read(Path path) {
        Path tempFile = Files.createTempFile("inkscape", ".png");
        List<String> commands = new LinkedList<>();

        commands.add(config.inkscapePath().toAbsolutePath().normalize().toString());
        commands.add("--without-gui");
        commands.add("--export-area-page");
        commands.add("--export-png=" + tempFile.toAbsolutePath().normalize());
        if (width != -1 && height != -1) {
            commands.add("--export-width=" + Integer.toString(width));
            commands.add("--export-height=" + Integer.toString(height));
        }
        commands.add(path.toAbsolutePath().normalize().toString());

        ProcessBuilder pb = new ProcessBuilder(commands);

        Process processInkscape = pb.start();
        processInkscape.waitFor(20, TimeUnit.SECONDS);

        String output = IOUtils.toString(processInkscape.getInputStream(), StandardCharsets.UTF_8);
        if (StringUtils.isNoneBlank(output)) {
            log.debug("Inkscape output: " + output);
        }

        String error = IOUtils.toString(processInkscape.getErrorStream(), StandardCharsets.UTF_8);
        if (StringUtils.isNoneBlank(error)) {
            log.error("Inkscape error: " + error);
        }

        BufferedImage bufferedImage = ImageIO.read(tempFile.toFile());

        Files.deleteIfExists(tempFile);
        return bufferedImage;
    }

    @Override
    public boolean supportResizing() {
        return true;
    }

    @Override
    public void setOutputSize(int w, int h) {
        width = w;
        height = h;
    }

    @Override
    @SneakyThrows
    public Size readSize(Path path) {
        Size size = new Size();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(path.toFile());

        NodeList childNodes = document.getChildNodes();
        int nbChild = childNodes.getLength();
        NamedNodeMap svgAttributes = null;
        for (int i = 0; i < nbChild; i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE && ((Element) item).getTagName().equalsIgnoreCase("svg")) {
                svgAttributes = item.getAttributes();
                break;
            }
        }

        if (svgAttributes == null) {
            throw new RuntimeException("No 'svg' element found into " + path.toAbsolutePath().normalize().toString());
        }

        Node height = svgAttributes.getNamedItem("height");
        Node width = svgAttributes.getNamedItem("width");
        if(height != null && width != null){
            size.setHeight(convertDimension(height.getTextContent()));
            size.setWidth(convertDimension(width.getTextContent()));
        }else{
            String viewBox = svgAttributes.getNamedItem("viewBox").getTextContent();
            String[] split = StringUtils.split(viewBox, " ");
            size.setHeight(convertDimension(split[3]));
            size.setWidth(convertDimension(split[2]));
        }

        return size;
    }

    public float convertDimension(String dim) {
        // "1pt" equals "1.25px" (and therefore 1.25 user units)
        // "1pc" equals "15px" (and therefore 15 user units)
        // "1mm" would be "3.543307px" (3.543307 user units)
        // "1cm" equals "35.43307px" (and therefore 35.43307 user units)
        // "1in" equals "90px" (and therefore 90 user units)

        if (dim.endsWith("px")) {
            return Float.parseFloat(dim.replace("px", ""));
        }

        if (dim.endsWith("pt")) {
            return Float.parseFloat(dim.replace("pt", "")) * 1.25f;
        }

        if (dim.endsWith("pc")) {
            return Float.parseFloat(dim.replace("pc", "")) * 15f;
        }

        if (dim.endsWith("mm")) {
            return Float.parseFloat(dim.replace("mm", "")) * 3.543307f;
        }

        if (dim.endsWith("cm")) {
            return Float.parseFloat(dim.replace("cm", "")) * 35.43307f;
        }

        if (dim.endsWith("in")) {
            return Float.parseFloat(dim.replace("in", "")) * 90f;
        }

        return Float.parseFloat(dim);
    }

}
