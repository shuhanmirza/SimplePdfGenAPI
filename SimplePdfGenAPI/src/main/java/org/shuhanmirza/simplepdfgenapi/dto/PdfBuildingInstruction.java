package org.shuhanmirza.simplepdfgenapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
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
    @Builder.Default
    private String templateString = "";
    @Builder.Default
    private Map<String, String> stringMap = new HashMap<>();
    @Builder.Default
    private Map<String, List<String>> listMap = new HashMap<>();
    @Builder.Default
    private Map<String, String> fileUrlMap = new HashMap<>();
}
