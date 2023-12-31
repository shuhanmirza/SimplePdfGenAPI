package org.shuhamirza.springbootex.component.latex;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhamirza.springbootex.component.PdfGenerator;
import org.shuhamirza.springbootex.dto.PdfBuildingInstruction;
import org.shuhamirza.springbootex.service.UtilityService;
import org.shuhamirza.springbootex.util.Utility;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LatexTemplatePdfGenerator implements PdfGenerator {
    private final UtilityService utilityService;

    @Override
    public Mono<String> generatePdfFromTemplate(PdfBuildingInstruction pdfBuildingInstruction) {
        return Mono.fromFuture(utilityService.getDigestOfString(pdfBuildingInstruction.getTemplateString()))
                .flatMap(digestHex -> Mono.fromFuture(utilityService.createTemporaryDirectory(digestHex)))
                .map(tempFolderPath -> {

                    return "asdasd";
                });
    }

    @PostConstruct
    public void test() {
        generatePdfFromTemplate(
                PdfBuildingInstruction
                        .builder()
                        .templateString("aspodkpoaskd")
                        .build())
                .subscribe();
    }
}
