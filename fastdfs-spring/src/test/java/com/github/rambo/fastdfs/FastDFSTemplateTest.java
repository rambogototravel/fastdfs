package com.github.rambo.fastdfs;

import com.github.rambo.fastdfs.core.FastDFSTemplate;
import com.github.rambo.fastdfs.core.FileID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

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
