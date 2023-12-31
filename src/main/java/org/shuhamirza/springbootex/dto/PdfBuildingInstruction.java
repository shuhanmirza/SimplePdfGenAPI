package org.shuhamirza.springbootex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Shuhan Mirza
 * @since 28/12/23
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PdfBuildingInstruction implements Serializable {
    private String templateString;
    private Map<String, String> stringMap;
    private Map<String, List<String>> listMap;
    private Map<String, List<String>> listImageUrl;
}
