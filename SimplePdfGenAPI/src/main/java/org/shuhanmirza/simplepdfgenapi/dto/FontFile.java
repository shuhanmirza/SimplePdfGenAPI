package org.shuhanmirza.simplepdfgenapi.dto;

import lombok.Builder;
import lombok.Data;

import java.io.File;

/**
 * @author Shuhan Mirza
 * @since 12/1/24
 */
@Data
@Builder
public class FontFile {
    private File file;
    private String fontFamily;
}
