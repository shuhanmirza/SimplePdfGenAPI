package org.shuhanmirza.springbootex.component.latex;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.component.PdfGenerator;
import org.shuhanmirza.springbootex.dto.PdfBuildingInstruction;
import org.shuhanmirza.springbootex.service.UtilityService;
import org.shuhanmirza.springbootex.util.Utility;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

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

        return Mono.fromFuture(utilityService.createTemporaryDirectory(Utility.getApplicationName()))
                .flatMap(tempFolderPath -> {
                    log.info("LatexGenerator: Temp Folder Generated {}", tempFolderPath);
                    return downloadAllImageFiles(pdfBuildingInstruction, tempFolderPath)
                            .flatMap(result -> Mono.just(tempFolderPath));
                }).map(string -> {
                    log.info(string);
                    return "asdasd";
                });
    }

    private Mono<Boolean> downloadAllImageFiles(PdfBuildingInstruction pdfBuildingInstruction, String tempFolderPath) {
        var imageUrlMap = pdfBuildingInstruction.getImageUrlMap();
        return Flux.fromIterable(imageUrlMap.keySet())
                .flatMap(imageFilename -> downloadImageFile(imageUrlMap.get(imageFilename), imageFilename, tempFolderPath))
                .collect(Collectors.toList())
                .flatMap(resultList -> {
                    log.info("LatexGenerator: image downloaded | count {}", resultList.size()); //TODO: add success and failed count
                    return Mono.just(Boolean.TRUE);
                });
    }

    private Mono<Boolean> downloadImageFile(String urlString, String fileName, String tempFolderPath) {
        return Mono.fromFuture(utilityService.downloadFile(urlString, fileName, tempFolderPath))
                .onErrorResume(throwable -> Mono.just("")) //Failing to download one image should not hamper generating the pdf
                .map(downloadedFileName -> !downloadedFileName.isEmpty());
    }

    @PostConstruct
    public void test() {
        generatePdfFromTemplate(
                PdfBuildingInstruction
                        .builder()
                        .templateString("aspodkpoaskd")
                        .imageUrlMap(Map.of("mkb.pdf", "https://shuhanmirza.com/assets/files/mkb_shuhan.pdf", "mkb1.pdf", "https://shuhanmirza.com/assets/files/mkb_shuhan.pdf"))
                        .build())
                .subscribe();
    }
}
