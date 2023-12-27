package org.shuhamirza.springbootex.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LatexTemplatePdfGenerator implements PdfGenerator {
    @Override
    public Mono<String> generatePdfFromTemplate(String template) {
        return null;
    }
}
