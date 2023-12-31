package org.shuhanmirza.springbootex.service;

import org.shuhanmirza.springbootex.dto.request.PdfGenerationRequest;
import org.shuhanmirza.springbootex.dto.response.PdfGenerationResponse;
import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
public interface PdfGenerationService {
    Mono<PdfGenerationResponse> generatePdfFromTemplate(PdfGenerationRequest pdfGenerationRequest);
}
