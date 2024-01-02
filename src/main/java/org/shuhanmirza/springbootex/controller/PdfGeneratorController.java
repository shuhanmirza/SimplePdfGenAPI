package org.shuhanmirza.springbootex.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.dto.request.PdfGenerationRequest;
import org.shuhanmirza.springbootex.dto.response.PdfGenerationResponse;
import org.shuhanmirza.springbootex.service.PdfGenerationService;
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
    private final PdfGenerationService pdfGenerationService;

    @PostMapping("/generate-pdf")
    public Mono<PdfGenerationResponse> generatePdf(@RequestBody @Valid PdfGenerationRequest pdfGenerationRequest) {
        return pdfGenerationService.generatePdfFromTemplate(pdfGenerationRequest);
    }

    //TODO: return file as octet stream
    //TODO: return file in a callback url
}
