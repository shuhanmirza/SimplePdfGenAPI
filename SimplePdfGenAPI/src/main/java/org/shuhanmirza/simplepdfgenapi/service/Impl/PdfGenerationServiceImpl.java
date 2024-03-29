package org.shuhanmirza.simplepdfgenapi.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.simplepdfgenapi.component.PdfGeneratorProvider;
import org.shuhanmirza.simplepdfgenapi.component.ResponseBuilderProvider;
import org.shuhanmirza.simplepdfgenapi.component.TemplateExtractorProvider;
import org.shuhanmirza.simplepdfgenapi.dto.PdfBuildingInstruction;
import org.shuhanmirza.simplepdfgenapi.dto.request.PdfGenerationRequest;
import org.shuhanmirza.simplepdfgenapi.service.PdfGenerationService;
import org.springframework.http.ResponseEntity;
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
    private final ResponseBuilderProvider responseBuilderProvider;

    @Override
    public Mono<ResponseEntity<?>> generatePdfFromTemplate(PdfGenerationRequest pdfGenerationRequest) {
        var templateExtractor = templateExtractorProvider.getTemplateExtractor(pdfGenerationRequest.getTemplateSourceType());
        var pdfGenerator = pdfGeneratorProvider.getPdfGenerator(pdfGenerationRequest.getTemplateType());
        var responseBuilder = responseBuilderProvider.getResponseBuilder(pdfGenerationRequest.getResponseType());

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
                .flatMap(responseBuilder::buildResponse);
    }
}
