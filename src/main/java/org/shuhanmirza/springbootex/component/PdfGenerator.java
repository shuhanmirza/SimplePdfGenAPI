package org.shuhanmirza.springbootex.component;

import org.shuhanmirza.springbootex.dto.PdfBuildingInstruction;
import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
public interface PdfGenerator {
    Mono<String> generatePdfFromTemplate(PdfBuildingInstruction pdfBuildingInstruction);
}
