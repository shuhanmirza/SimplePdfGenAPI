package org.shuhanmirza.springbootex.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.component.pdfgenerator.HtmlTemplatePdfGenerator;
import org.shuhanmirza.springbootex.component.pdfgenerator.LatexTemplatePdfGenerator;
import org.shuhanmirza.springbootex.enums.TemplateType;
import org.springframework.stereotype.Component;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfGeneratorProvider {
    private final HtmlTemplatePdfGenerator htmlTemplatePdfGenerator;
    private final LatexTemplatePdfGenerator latexTemplatePdfGenerator;

    public PdfGenerator getPdfGenerator(TemplateType templateType) {
        return switch (templateType) {
            case HTML -> htmlTemplatePdfGenerator;
            case LATEX -> latexTemplatePdfGenerator;
            default -> throw new RuntimeException(String.format("NO PDF GENERATOR FOR TEMPLATE TYPE %s", templateType));
        };
    }
}
