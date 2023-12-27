package org.shuhamirza.springbootex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhamirza.springbootex.dto.request.PdfGenerationRequest;
import org.shuhamirza.springbootex.dto.response.PdfGenerationResponse;
import org.shuhamirza.springbootex.service.PdfGenerationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@RestController
@BaseController
@Slf4j
@RequiredArgsConstructor
public class PdfGeneratorController {
    private PdfGenerationService pdfGenerationService;

    @PostMapping("/generate-pdf")
    public Mono<PdfGenerationResponse> generatePdf(@RequestBody @Validated PdfGenerationRequest pdfGenerationRequest) {
        return pdfGenerationService.generatePdfFromTemplate(pdfGenerationRequest);
    }
}
