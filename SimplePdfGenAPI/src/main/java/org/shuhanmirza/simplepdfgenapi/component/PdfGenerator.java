package org.shuhanmirza.simplepdfgenapi.component;

import org.shuhanmirza.simplepdfgenapi.dto.PdfBuildingInstruction;
import reactor.core.publisher.Mono;

import java.io.InputStream;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
public interface PdfGenerator {
    Mono<InputStream> generatePdfFromTemplate(PdfBuildingInstruction pdfBuildingInstruction);
}
