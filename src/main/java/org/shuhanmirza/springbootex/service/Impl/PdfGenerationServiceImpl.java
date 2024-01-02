package org.shuhanmirza.springbootex.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.component.PdfGeneratorProvider;
import org.shuhanmirza.springbootex.component.TemplateExtractorProvider;
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
    private final TemplateExtractorProvider templateExtractorProvider;

    @Override
    public Mono<PdfGenerationResponse> generatePdfFromTemplate(PdfGenerationRequest pdfGenerationRequest) {
        var templateExtractor = templateExtractorProvider.getTemplateExtractor(pdfGenerationRequest.getTemplateSourceType());
        var pdfGenerator = pdfGeneratorProvider.getPdfGenerator(pdfGenerationRequest.getTemplateType());

        return templateExtractor
                .getTemplateFromSource(pdfGenerationRequest.getTemplateSource())
                .flatMap(templateString -> {
                    return pdfGenerator
                            .generatePdfFromTemplate(
                                    PdfBuildingInstruction
                                            .builder()
                                            .templateString(templateString)
                                            .listMap(pdfGenerationRequest.getListMap())
                                            .stringMap(pdfGenerationRequest.getStringMap())
                                            .fileUrlMap(pdfGenerationRequest.getFileUrlMap())
                                            .build()
                            );
                })
                .flatMap(pdfBase64 -> {
                    return Mono.just(
                            PdfGenerationResponse
                                    .builder()
                                    .pdfBase64(pdfBase64)
                                    .build()
                    );
                });
    }
}
