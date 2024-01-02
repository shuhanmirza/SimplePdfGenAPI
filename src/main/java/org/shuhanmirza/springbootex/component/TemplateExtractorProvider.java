package org.shuhanmirza.springbootex.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.component.templateextractor.Base64TemplateExtractor;
import org.shuhanmirza.springbootex.enums.TemplateSourceType;
import org.springframework.stereotype.Component;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class TemplateExtractorProvider {
    private final Base64TemplateExtractor base64TemplateExtractor;

    public TemplateExtractor getTemplateExtractor(TemplateSourceType templateSourceType) {
        return switch (templateSourceType) {
            case BASE64 -> base64TemplateExtractor;
            default ->
                    throw new RuntimeException(String.format("NO PDF GENERATOR FOR TEMPLATE SOURCE TYPE %s", templateSourceType));
        };
    }
}
