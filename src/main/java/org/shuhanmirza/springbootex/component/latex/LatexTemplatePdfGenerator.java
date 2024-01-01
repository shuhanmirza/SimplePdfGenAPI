package org.shuhanmirza.springbootex.component.latex;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.shuhanmirza.springbootex.component.PdfGenerator;
import org.shuhanmirza.springbootex.dto.PdfBuildingInstruction;
import org.shuhanmirza.springbootex.service.UtilityService;
import org.shuhanmirza.springbootex.util.Utility;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
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

    //TODO: clean up temp folder after completion
    @Override
    public Mono<String> generatePdfFromTemplate(PdfBuildingInstruction pdfBuildingInstruction) {

        return Mono.fromFuture(utilityService.createTemporaryDirectory(Utility.APPLICATION_NAME))
                .flatMap(tempFolderPath -> {
                    log.info("LatexGenerator: Temp Folder Generated {}", tempFolderPath);
                    return downloadAllImageFiles(pdfBuildingInstruction, tempFolderPath)
                            .flatMap(imageDownloaded -> prepareLatexFile(pdfBuildingInstruction, tempFolderPath))
                            .flatMap(latexFilePath -> Mono.fromFuture(compilePdf(tempFolderPath)))
                            .flatMap(compiled -> Mono.fromFuture(utilityService.readFileToBase64(tempFolderPath.concat("/").concat(Utility.LATEX_FILE_OUTPUT))));
                });
    }

    private Mono<String> prepareLatexFile(PdfBuildingInstruction pdfBuildingInstruction, String tempFolderPath) {

        var templateKeyValues = new HashMap<String, String>();
        for (String key : pdfBuildingInstruction.getStringMap().keySet()) {
            templateKeyValues.put("%".concat(key).concat("%"), pdfBuildingInstruction.getStringMap().get(key));
        }

        for (String key : pdfBuildingInstruction.getListMap().keySet()) {
            templateKeyValues.put("%".concat(key).concat("%"), pdfBuildingInstruction.getListMap().get(key).stream().map(value -> value.concat("&")).collect(Collectors.joining()));
        }

        var latexFileContentText = StringUtils.replaceEach(pdfBuildingInstruction.getTemplateString(), templateKeyValues.keySet().toArray(new String[0]), templateKeyValues.values().toArray(new String[0]));

        return Mono.fromFuture(utilityService.createTextFile(latexFileContentText, tempFolderPath.concat("/").concat(Utility.LATEX_FILE_INPUT)))
                .map(latexFilePath -> {
                    log.info("LatexGenerator: latex file generated | path {}", latexFilePath);
                    return latexFilePath;
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
        return Mono.fromFuture(utilityService.downloadFile(urlString, fileName, tempFolderPath)) //TODO: need to consider caching to improve throughput
                .onErrorResume(throwable -> Mono.just("")) //Failing to download one image should not hamper generating the pdf
                .map(downloadedFileName -> !downloadedFileName.isEmpty());
    }

    private CompletableFuture<Boolean> compilePdf(String tempFolderPath) {
        try {
            var processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(tempFolderPath));
            processBuilder.command("pdflatex", "-no-shell-escape", "-interaction", "nonstopmode", "main");
            var process = processBuilder.start();
            int exitCode = process.waitFor();

            log.info("LatexGenerator: latex compilation successful | path {} | exit code {}", tempFolderPath, exitCode);

            return CompletableFuture.completedFuture(exitCode == 0);
        } catch (InterruptedException | IOException exception) {
            log.info("LatexGenerator: latex compilation failed | path {}", tempFolderPath, exception);
            return CompletableFuture.failedFuture(exception);
        }
    }
}
