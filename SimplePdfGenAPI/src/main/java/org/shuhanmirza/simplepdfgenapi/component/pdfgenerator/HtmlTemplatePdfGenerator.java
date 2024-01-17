package org.shuhanmirza.simplepdfgenapi.component.pdfgenerator;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.simplepdfgenapi.component.PdfGenerator;
import org.shuhanmirza.simplepdfgenapi.dto.FontFile;
import org.shuhanmirza.simplepdfgenapi.dto.PdfBuildingInstruction;
import org.shuhanmirza.simplepdfgenapi.service.PdfCleanUpService;
import org.shuhanmirza.simplepdfgenapi.service.UtilityService;
import org.shuhanmirza.simplepdfgenapi.util.Utility;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@Component
@Slf4j
public class HtmlTemplatePdfGenerator implements PdfGenerator {
    private final SpringTemplateEngine springTemplateEngine;
    private final UtilityService utilityService;
    private final PdfCleanUpService pdfCleanUpService;

    public HtmlTemplatePdfGenerator(UtilityService utilityService, PdfCleanUpService pdfCleanUpService) {
        this.utilityService = utilityService;
        this.pdfCleanUpService = pdfCleanUpService;
        springTemplateEngine = new SpringTemplateEngine();

        var stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setTemplateMode(TemplateMode.HTML);
        springTemplateEngine.addTemplateResolver(stringTemplateResolver);
    }

    @Override
    public Mono<InputStream> generatePdfFromTemplate(PdfBuildingInstruction pdfBuildingInstruction) {

        return Mono.fromFuture(utilityService.createTemporaryDirectory(Utility.APPLICATION_NAME))
                .flatMap(tempFolderPath -> {
                    log.info("HtmlGenerator: Temp Folder Generated {}", tempFolderPath);
                    var context = generateContext(pdfBuildingInstruction);

                    pdfCleanUpService.scheduleFolderForDeletion(tempFolderPath);

                    return generateHtml(pdfBuildingInstruction, context)
                            .flatMap(generatedHtml -> generatedPdfFromHtml(pdfBuildingInstruction, tempFolderPath, generatedHtml, tempFolderPath.concat("/").concat(Utility.HTML_FILE_OUTPUT)));
                });
    }

    private Mono<List<FontFile>> downloadAllFonts(PdfBuildingInstruction pdfBuildingInstruction, String tempFolderPath) {
        var fileUrlMap = pdfBuildingInstruction.getFileUrlMap();
        return Flux.fromIterable(fileUrlMap.keySet())
                .flatMap(fontFamilyName -> downloadFont(fileUrlMap.get(fontFamilyName), fontFamilyName, fontFamilyName, tempFolderPath))//Using fontfamily name as the filename
                .filter(fontFile -> null != fontFile.getFile())
                .collect(Collectors.toList())
                .flatMap(resultList -> {
                    log.info("HtmlGenerator: {} fonts downloaded out of {}", resultList.size(), fileUrlMap.size());
                    return Mono.just(resultList);
                });
    }

    private Mono<FontFile> downloadFont(String urlString, String fileName, String fontFamily, String tempFolderPath) {
        return Mono.fromFuture(utilityService.downloadFile(urlString, fileName, tempFolderPath))
                .map(file -> FontFile.builder().fontFamily(fontFamily).file(file).build())
                .onErrorResume(throwable -> Mono.just(FontFile.builder().build())); //Failing to download one font should not hamper generating the pdf
    }


    private Mono<InputStream> generatedPdfFromHtml(PdfBuildingInstruction pdfBuildingInstruction, String tempFolder, String html, String pdfPath) {
        return downloadAllFonts(pdfBuildingInstruction, tempFolder)
                .flatMap(fontFileList -> {
                    var pdfRendererBuilder = new PdfRendererBuilder();
                    for (var fontFile : fontFileList) {
                        pdfRendererBuilder.useFont(fontFile.getFile(), fontFile.getFontFamily());
                    }
                    try (OutputStream outputStream = new FileOutputStream(pdfPath)) {
                        pdfRendererBuilder.useFastMode();
                        pdfRendererBuilder.withHtmlContent(html, "/");
                        pdfRendererBuilder.toStream(outputStream);
                        pdfRendererBuilder.run();

                        log.info("HtmlGenerator: PDF generated | path {}", pdfPath);

                        return utilityService.readFileToInputStream(pdfPath);
                    } catch (Exception ex) {
                        log.error("HtmlGenerator: Error Generating Receipt ", ex);
                        return Mono.error(ex);
                    }
                });

    }

    private Mono<String> generateHtml(PdfBuildingInstruction pdfBuildingInstruction, Context context) {
        var generatedHtml = springTemplateEngine.process(pdfBuildingInstruction.getTemplateString(), context);
        log.info("HtmlGenerator: Html Generated");
        return Mono.just(generatedHtml);
    }

    private Context generateContext(PdfBuildingInstruction pdfBuildingInstruction) {
        var context = new Context();
        context.setVariables(new HashMap<String, Object>(pdfBuildingInstruction.getStringMap()));
        context.setVariables(new HashMap<String, Object>(pdfBuildingInstruction.getListMap()));
        return context;
    }
}
