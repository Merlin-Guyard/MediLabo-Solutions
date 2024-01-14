package com.oc.medilabosolutionsreport.TI;

import com.oc.medilabosolutionsreport.dao.KeywordFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FileTest {

    @Autowired
    private KeywordFile keywordFile;

    @Test
    public void keywordTest() throws Exception {
        List<String> keywords = keywordFile.getKeywords();

        assertEquals("hémoglobine a1c", keywords.get(0));
    }
}
