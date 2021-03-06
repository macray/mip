package com.tdb.mip;

import com.tdb.mip.service.filenameparser.FileNameParser;
import com.tdb.mip.service.filenameparser.FileNameParserImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mcy on 26/10/2016.
 */
public class FileNameParserImplTest {
    private FileNameParser fileNameParser;

    @Before
    public void setUp() throws Exception {
        fileNameParser = new FileNameParserImpl();
    }

    @Test
    public void extract_file_extension() {
        FileNameParser.FileNameInfo info = fileNameParser.parse("base_name-icon-bla1-blabla2.extension");
        Assertions.assertThat(info.getExtension()).isEqualTo("extension");

        info = fileNameParser.parse("base_name.jpg");
        Assertions.assertThat(info.getExtension()).isEqualTo("jpg");
    }

    @Test
    public void extract_basename() {
        FileNameParser.FileNameInfo info = fileNameParser.parse("base_name-icon-bla1-blabla2.extension");
        Assertions.assertThat(info.getBaseName()).isEqualTo("base_name");
    }

    @Test
    public void extract_operation_descriptions() {
        FileNameParser.FileNameInfo info = fileNameParser.parse("base_name-icon-bla1-blabla2.extension");
        Assertions.assertThat(info.getOperationDescriptions()).hasSize(3);
        Assertions.assertThat(info.getOperationDescriptions().get(0).getTextDescription()).isEqualTo("icon");
        Assertions.assertThat(info.getOperationDescriptions().get(1).getTextDescription()).isEqualTo("bla1");
        Assertions.assertThat(info.getOperationDescriptions().get(2).getTextDescription()).isEqualTo("blabla2");
    }

}