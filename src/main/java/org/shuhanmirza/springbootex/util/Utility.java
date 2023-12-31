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

    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static int getXxHashSeed() {
        return 0x6969abcd;
    }

    public static String getApplicationName() {
        return "SpringBooTex";
    }

}
