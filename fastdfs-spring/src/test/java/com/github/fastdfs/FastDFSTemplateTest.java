package com.github.fastdfs;

import com.github.fastdfs.core.FastDFSTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author Rambo Yang
 */
public class FastDFSTemplateTest extends BaseJUnitTest {

    @Autowired
    private FastDFSTemplate template;

    @Test
    public void testUpload() {
        File file = new File("F:" + File.separator + "timg.jpg");
        print(template.upload(file));
    }
}
