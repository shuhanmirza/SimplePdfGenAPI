package org.shuhanmirza.springbootex.component;

import org.shuhanmirza.springbootex.dto.PdfBuildingInstruction;
import reactor.core.publisher.Mono;

import java.io.InputStream;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
public interface PdfGenerator {
    Mono<InputStream> generatePdfFromTemplate(PdfBuildingInstruction pdfBuildingInstruction);
}
