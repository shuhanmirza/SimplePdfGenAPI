package org.shuhanmirza.springbootex.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.component.PdfGeneratorProvider;
import org.shuhanmirza.springbootex.dto.PdfBuildingInstruction;
import org.shuhanmirza.springbootex.dto.request.PdfGenerationRequest;
import org.shuhanmirza.springbootex.dto.response.PdfGenerationResponse;
import org.shuhanmirza.springbootex.service.PdfGenerationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PdfGenerationServiceImpl implements PdfGenerationService {
    private final PdfGeneratorProvider pdfGeneratorProvider;

    @Override
    public Mono<PdfGenerationResponse> generatePdfFromTemplate(PdfGenerationRequest pdfGenerationRequest) {

        return pdfGeneratorProvider
                .getPdfGenerator(pdfGenerationRequest.getTemplateType())
                .generatePdfFromTemplate(PdfBuildingInstruction.builder().build())
                .flatMap(pdf -> {
                    log.info(pdf);

                    return Mono.just(PdfGenerationResponse.builder().build());
                });
    }
}
