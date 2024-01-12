package org.shuhanmirza.springbootex.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shuhanmirza.springbootex.enums.ResponseType;
import org.shuhanmirza.springbootex.enums.TemplateSourceType;
import org.shuhanmirza.springbootex.enums.TemplateType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PdfGenerationRequest extends BaseRequest {
    @NotNull
    private TemplateType templateType;
    @NotNull
    private TemplateSourceType templateSourceType;
    @NotNull
    private String templateSource;
    @Builder.Default
    private Map<String, String> stringMap = new HashMap<>();
    @Builder.Default
    private Map<String, List<String>> listMap = new HashMap<>();
    @Builder.Default
    private Map<String, String> fileUrlMap = new HashMap<>();
    @Builder.Default
    private ResponseType responseType = ResponseType.JSON;
}
