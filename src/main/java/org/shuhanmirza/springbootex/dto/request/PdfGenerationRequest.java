package org.shuhanmirza.springbootex.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shuhanmirza.springbootex.enums.TemplateType;

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
    private String templateBase64;
    @NotNull
    private TemplateType templateType;
    private Map<String, String> stringMap;
    private Map<String, List<String>> listMap;
}
