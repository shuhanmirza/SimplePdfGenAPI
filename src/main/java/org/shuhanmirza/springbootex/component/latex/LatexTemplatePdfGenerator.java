package org.shuhanmirza.springbootex.component.latex;

import jakarta.annotation.PostConstruct;
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

import java.util.HashMap;
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
                }).flatMap(string -> {
                    log.info(string);
                    return prepareLatexFile(pdfBuildingInstruction);
                });
    }

    private Mono<String> prepareLatexFile(PdfBuildingInstruction pdfBuildingInstruction) {

        var templateKeyValues = new HashMap<String, String>();
        for (String key : pdfBuildingInstruction.getStringMap().keySet()) {
            templateKeyValues.put("%".concat(key).concat("%"), pdfBuildingInstruction.getStringMap().get(key));
        }

        var latexFileContentText = StringUtils.replaceEach(pdfBuildingInstruction.getTemplateString(), templateKeyValues.keySet().toArray(new String[0]), templateKeyValues.values().toArray(new String[0]));

        log.info(latexFileContentText);

        return Mono.just(latexFileContentText);
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
                        .templateString(getTemplate())
                        .stringMap(Map.of("FOOD", "Birun Vaat", "CITY", "Sylhet"))
                        .imageUrlMap(Map.of("universe.jpg", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Hubble_ultra_deep_field.jpg/1024px-Hubble_ultra_deep_field.jpg"))
                        .build())
                .subscribe();
    }

    public String getTemplate() {
        return "\\documentclass{article}\n" +
                "\\usepackage{graphicx} % Required for inserting images\n" +
                "\n" +
                "\\usepackage{hyperref}\n" +
                "\\hypersetup{\n" +
                "    colorlinks=true,\n" +
                "    urlcolor=blue,\n" +
                "    breaklinks=true\n" +
                "}\n" +
                "\n" +
                "\\title{SpringBooTex}\n" +
                "\\author{shuhan mirza}\n" +
                "\\date{\\today}\n" +
                "\n" +
                "\\newcommand{\\food}{%FOOD%}\n" +
                "\\newcommand{\\city}{%CITY%}\n" +
                "\n" +
                "\\begin{document}\n" +
                "\n" +
                "\\maketitle\n" +
                "\n" +
                "\\section{Single Variable}\n" +
                "\n" +
                "I am from \\city. I love eating \\food.\n" +
                "\n" +
                "\\section{Image}\n" +
                "This image was downloaded from \\href{https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Hubble_ultra_deep_field.jpg/1024px-Hubble_ultra_deep_field.jpg}{Wikipedia}\n" +
                "\n" +
                "\\vspace{1cm}\n" +
                "\\includegraphics[scale=0.2]{universe.jpg}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\\end{document}\n";
    }
}
