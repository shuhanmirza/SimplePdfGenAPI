package org.shuhamirza.springbootex.component.html;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhamirza.springbootex.component.PdfGenerator;
import org.shuhamirza.springbootex.dto.PdfBuildingInstruction;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;
import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class HtmlTemplatePdfGenerator implements PdfGenerator {
    private final SpringTemplateEngine templateEngine;

    @Override
    public Mono<String> generatePdfFromTemplate(PdfBuildingInstruction pdfBuildingInstruction) {
        throw new RuntimeException("NOT YET IMPLEMENTED");
    }
}
