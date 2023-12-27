package org.shuhamirza.springbootex.service;

import org.shuhamirza.springbootex.dto.request.PdfGenerationRequest;
import org.shuhamirza.springbootex.dto.response.PdfGenerationResponse;
import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
public interface PdfGenerationService {
    Mono<PdfGenerationResponse> generatePdfFromTemplate(PdfGenerationRequest pdfGenerationRequest);
}
