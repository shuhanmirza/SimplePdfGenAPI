package org.shuhanmirza.springbootex.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Shuhan Mirza
 * @since 31/12/23
 */

@Slf4j
public class Utility {
    private Utility() {
    }

    public static int XX_HASH_SEED = 0x6969abcd;
    public static String APPLICATION_NAME = "SpringBooTex";
    public static String LATEX_FILE_INPUT = "main.tex";
    public static String LATEX_FILE_OUTPUT = "main.pdf";

    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

}