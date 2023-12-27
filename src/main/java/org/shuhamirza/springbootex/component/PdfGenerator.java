package org.shuhamirza.springbootex.component;

import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
public interface PdfGenerator {
    Mono<String> generatePdfFromTemplate(String template);
}
