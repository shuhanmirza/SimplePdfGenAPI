package org.shuhamirza.springbootex.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shuhamirza.springbootex.enums.TemplateType;
import reactor.util.annotation.NonNull;

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
    @NonNull
    private String templateBase64;
    @NonNull
    private TemplateType templateType;
    private Map<String, String> stringMap;
    private Map<String, List<String>> listMap;
}
