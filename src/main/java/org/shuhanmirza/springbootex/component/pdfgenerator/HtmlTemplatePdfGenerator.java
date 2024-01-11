package org.shuhanmirza.springbootex.component.pdfgenerator;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.component.PdfGenerator;
import org.shuhanmirza.springbootex.dto.PdfBuildingInstruction;
import org.shuhanmirza.springbootex.service.UtilityService;
import org.shuhanmirza.springbootex.util.Utility;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@Component
@Slf4j
public class HtmlTemplatePdfGenerator implements PdfGenerator {
    private final SpringTemplateEngine springTemplateEngine;
    private final UtilityService utilityService;

    public HtmlTemplatePdfGenerator(UtilityService utilityService) {
        this.utilityService = utilityService;
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

                    return generateHtml(pdfBuildingInstruction, context)
                            .flatMap(generatedHtml -> generatedPdfFromHtml(generatedHtml, tempFolderPath.concat("/").concat(Utility.HTML_FILE_OUTPUT)));
                });
    }

    private Mono<InputStream> generatedPdfFromHtml(String html, String pdfPath) {
        var pdfRendererBuilder = new PdfRendererBuilder();
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
