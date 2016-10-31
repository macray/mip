package com.tdb.mip.service.filenameparser;

import com.tdb.mip.operation.OperationDescription;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;

/**
 * Created by mcy on 26/10/2016.
 */
@Getter
@Setter
@Slf4j
public class FileNameParserImpl implements FileNameParser {

    public static final String DESCRIPTION_SEPARATOR = "-";

    @Override
    public FileNameInfo parse(String fileName) {
        FileNameInfo info = new FileNameInfo();

        info.setExtension(FilenameUtils.getExtension(fileName));
        String baseNameAndDescriptions = FilenameUtils.getBaseName(fileName);
        info.setBaseName(StringUtils.substringBefore(baseNameAndDescriptions, DESCRIPTION_SEPARATOR));

        String rawDescriptions = StringUtils.substringAfter(baseNameAndDescriptions, DESCRIPTION_SEPARATOR);
        info.setOperationDescriptions(new LinkedList<>());
        if (StringUtils.isNotBlank(rawDescriptions)) {
            String[] split = StringUtils.split(rawDescriptions, DESCRIPTION_SEPARATOR);
            for (String s : split) {
                info.getOperationDescriptions().add(new OperationDescription(s));
            }
        }

        return info;
    }
}
