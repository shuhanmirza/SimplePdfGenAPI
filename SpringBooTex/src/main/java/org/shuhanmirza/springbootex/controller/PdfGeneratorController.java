package org.shuhanmirza.springbootex.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.dto.request.PdfGenerationRequest;
import org.shuhanmirza.springbootex.service.PdfGenerationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class PdfGeneratorController {
    private final PdfGenerationService pdfGenerationService;

    @PostMapping(value = "/generate-pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> generatePdf(@RequestBody @Valid PdfGenerationRequest pdfGenerationRequest) {
        return pdfGenerationService.generatePdfFromTemplate(pdfGenerationRequest);
    }

    //TODO: return file in a callback url
    //TODO: span and trace id
    //TODO: add password protection for pdf
    //TODO: clean up temp folder after completion
}