package org.shuhanmirza.springbootex.component.pdfgenerator;

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
import java.io.InputStream;
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

    @Override
    public Mono<InputStream> generatePdfFromTemplate(PdfBuildingInstruction pdfBuildingInstruction) {

        return Mono.fromFuture(utilityService.createTemporaryDirectory(Utility.APPLICATION_NAME))
                .flatMap(tempFolderPath -> {
                    log.info("LatexGenerator: Temp Folder Generated {}", tempFolderPath);
                    return downloadAllFiles(pdfBuildingInstruction, tempFolderPath)
                            .flatMap(fileDownloaded -> prepareLatexFile(pdfBuildingInstruction, tempFolderPath))
                            .flatMap(latexFilePath -> Mono.fromFuture(compilePdf(tempFolderPath)))
                            .flatMap(compiled -> utilityService.readFileToInputStream(tempFolderPath.concat("/").concat(Utility.LATEX_FILE_OUTPUT)));
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

    private Mono<Boolean> downloadAllFiles(PdfBuildingInstruction pdfBuildingInstruction, String tempFolderPath) {
        var fileUrlMap = pdfBuildingInstruction.getFileUrlMap();
        return Flux.fromIterable(fileUrlMap.keySet())
                .flatMap(fileName -> downloadFile(fileUrlMap.get(fileName), fileName, tempFolderPath))
                .filter(filePath -> !filePath.isEmpty())
                .collect(Collectors.toList())
                .flatMap(resultList -> {
                    log.info("LatexGenerator: {} files downloaded out of {} ", resultList.size(), fileUrlMap.size());
                    return Mono.just(Boolean.TRUE);
                });
    }

    private Mono<String> downloadFile(String urlString, String fileName, String tempFolderPath) {
        return Mono.fromFuture(utilityService.downloadFile(urlString, fileName, tempFolderPath))
                .map(File::getAbsolutePath)
                .onErrorResume(throwable -> Mono.just(""));//Failing to download one file should not hamper generating the pdf
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
