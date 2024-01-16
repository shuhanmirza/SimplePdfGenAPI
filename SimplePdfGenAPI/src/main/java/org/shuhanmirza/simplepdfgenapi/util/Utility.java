package org.shuhanmirza.simplepdfgenapi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Shuhan Mirza
 * @since 31/12/23
 */

@Slf4j
public class Utility {
    private Utility() {
    }

    public static int XX_HASH_SEED = 0x6969abcd;
    @Value("${spring.application.name}")
    public static String APPLICATION_NAME;
    public static String LATEX_FILE_INPUT = "main.tex";
    public static String LATEX_FILE_OUTPUT = "main.pdf";
    public static String HTML_FILE_OUTPUT = "main.pdf";

    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

}
