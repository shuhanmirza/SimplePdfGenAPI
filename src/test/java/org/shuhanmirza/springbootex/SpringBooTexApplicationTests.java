package org.shuhanmirza.springbootex;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.shuhanmirza.springbootex.component.pdfgenerator.HtmlTemplatePdfGenerator;
import org.shuhanmirza.springbootex.component.pdfgenerator.LatexTemplatePdfGenerator;
import org.shuhanmirza.springbootex.dto.PdfBuildingInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class SpringBooTexApplicationTests {

    @Autowired
    private LatexTemplatePdfGenerator latexTemplatePdfGenerator;

    @Autowired
    private HtmlTemplatePdfGenerator htmlTemplatePdfGenerator;

    @Test
    void testHtmlCompilation(){
        htmlTemplatePdfGenerator.generatePdfFromTemplate(
                        PdfBuildingInstruction
                                .builder()
                                .templateString(getHtmlTemplate())
                                .stringMap(Map.of("FOOD", "Birun Vaat", "CITY", "Sylhet", "LIST_SIZE", "3"))
                                .listMap(Map.of("COUNTRY_LIST", List.of("Bangladesh", "United States", "United Kingdom"), "CITY_LIST", List.of("Dhaka", "Washington DC", "London")))
                                .fileUrlMap(Map.of("universe.jpg", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Hubble_ultra_deep_field.jpg/1024px-Hubble_ultra_deep_field.jpg"))
                                .build())
                .flatMap(base64 -> {
                    log.info("file -> {}", base64);
                    return Mono.just(Boolean.TRUE);
                })
                .subscribe();
    }

    @Test
    void testLatexCompilation() {
        latexTemplatePdfGenerator.generatePdfFromTemplate(
                        PdfBuildingInstruction
                                .builder()
                                .templateString(getLatexTemplate())
                                .stringMap(Map.of("FOOD", "Birun Vaat", "CITY", "Sylhet", "LIST_SIZE", "3"))
                                .listMap(Map.of("COUNTRY_LIST", List.of("Bangladesh", "United States", "United Kingdom"), "CITY_LIST", List.of("Dhaka", "Washington DC", "London")))
                                .fileUrlMap(Map.of("universe.jpg", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Hubble_ultra_deep_field.jpg/1024px-Hubble_ultra_deep_field.jpg"))
                                .build())
                .flatMap(base64 -> {
                    log.info("file -> {}", base64);
                    return Mono.just(Boolean.TRUE);
                })
                .subscribe();

    }

    String getHtmlTemplate() {
        return "<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "\n" +
                "  <meta charset=\"UTF-8\"></meta>\n" +
                "\n" +
                "  <title>Bill Receipt</title>\n" +
                "\n" +
                "  <meta name=\"viewport\" content=\"width=device-width\"></meta>\n" +
                "\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Roboto:400,500&amp;display=swap\" rel=\"stylesheet\"></link>\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "\n" +
                "    .status {\n" +
                "\n" +
                "      color: #01846C;\n" +
                "\n" +
                "      font-size: 16px;\n" +
                "\n" +
                "      font-weight: bold;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    table {\n" +
                "\n" +
                "      page-break-after: auto;\n" +
                "\n" +
                "      width: 100%;\n" +
                "\n" +
                "      border-collapse: collapse;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    tr {\n" +
                "\n" +
                "      page-break-inside: avoid;\n" +
                "\n" +
                "      page-break-after: auto;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    td {\n" +
                "\n" +
                "      page-break-inside: avoid;\n" +
                "\n" +
                "      page-break-after: auto;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    hr {\n" +
                "\n" +
                "      height: 1px;\n" +
                "\n" +
                "      background-color: #ededed;\n" +
                "\n" +
                "      border: none;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    .roboto-medium-text {\n" +
                "\n" +
                "      font-family: 'Roboto Medium', sans-serif;\n" +
                "\n" +
                "      font-weight: bold;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    .labels {\n" +
                "\n" +
                "      border-right: 1px solid #ededed;\n" +
                "\n" +
                "      font-size: 14px;\n" +
                "\n" +
                "      padding: 6px 6px 6px 0;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    .values {\n" +
                "\n" +
                "      font-size: 14px;\n" +
                "\n" +
                "      color: #777777;\n" +
                "\n" +
                "      padding: 6px 6px 6px 10px;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    .segments {\n" +
                "\n" +
                "      font-weight: bold;\n" +
                "\n" +
                "      margin: 0;\n" +
                "\n" +
                "      padding-bottom: 2px;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    .footer {\n" +
                "\n" +
                "     padding-top: 50px;\n" +
                "\n" +
                "     text-align: center;\n" +
                "\n" +
                "     color: #777777;\n" +
                "\n" +
                "     font-size: 12px;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    .with-bottom-border{\n" +
                "\n" +
                "      border-bottom: 1px solid #ededed;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    .with-top-border{\n" +
                "\n" +
                "      border-top: 1px solid #ededed;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "  </style>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"background-color: #fff; padding: 1px; font-family: 'Roboto', sans-serif; font-size: 16px;\">\n" +
                "\n" +
                "<div style=\"background-color: #fff; padding: 10px;\">\n" +
                "\n" +
                "  <div style=\"width: 100%\" class=\"row\">\n" +
                "\n" +
                "    <br/>\n" +
                "\n" +
                "    <table>\n" +
                "\n" +
                "      <tr>\n" +
                "\n" +
                "        <td style=\"width: 70%;\">\n" +
                "\n" +
                "        </td>\n" +
                "\n" +
                "        <td style=\"width: 30%;\">\n" +
                "\n" +
                "          <h2 style=\"height: 100%; text-align: center; margin: 0 0 0 20px; padding-left: 20px; float: right; border-left: 1px solid #a6a8ab; text-transform: uppercase; color: #414143;\">\n" +
                "\n" +
                "            Receipt</h2>\n" +
                "\n" +
                "        </td>\n" +
                "\n" +
                "      </tr>\n" +
                "\n" +
                "    </table>\n" +
                "\n" +
                "    <hr style=\"background-color: #cfcfcf\"/>\n" +
                "\n" +
                "    <br/>\n" +
                "\n" +
                "    <p class=\"segments\" text=\"asd\"></p>\n" +
                "\n" +
                "    <br/>\n" +
                "\n" +
                "    <br/>\n" +
                "\n" +
                "    <p class=\"segments\" text=\"receiptGenerationRequest.paymentDetailsLabel\"></p>\n" +
                "\n" +
                "    <table>\n" +
                "\n" +
                "    </table>\n" +
                "\n" +
                "    <br/>\n" +
                "\n" +
                "    <p class=\"segments\" text=\"receiptGenerationRequest.paymentInformationLabel\"></p>\n" +
                "\n" +
                "    <table>\n" +
                "\n" +
                "    </table>\n" +
                "\n" +
                "    <table>\n" +
                "\n" +
                "    </table>\n" +
                "\n" +
                "  </div>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "<footer class=\"footer\">\n" +
                "\n" +
                "  This receipt has been generated electronically\n" +
                "\n" +
                "</footer>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    String getLatexTemplate() {
        return "\\documentclass[a4paper,11pt]{article}\n" +
                "\\usepackage{graphicx}\n" +
                "\\usepackage[empty]{fullpage}\n" +
                "\\usepackage{hyperref}\n" +
                "\\hypersetup{\n" +
                "    colorlinks=true,\n" +
                "    urlcolor=blue,\n" +
                "    breaklinks=true\n" +
                "}\n" +
                "\\usepackage{arrayjob}\n" +
                "\\usepackage{multido}\n" +
                "\n" +
                "\\newcommand{\\food}{%FOOD%}\n" +
                "\\newcommand{\\city}{%CITY%}\n" +
                "\n" +
                "\\newarray\\CountryList\n" +
                "\\newarray\\CityList\n" +
                "\n" +
                "\\readarray{CountryList}{%COUNTRY_LIST%}\n" +
                "\\readarray{CityList}{%CITY_LIST%}\n" +
                "\\newcommand{\\ListSize}{%LIST_SIZE%}\n" +
                "\n" +
                "\n" +
                "\\title{SpringBooTex Example}\n" +
                "\\date{}\n" +
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
                "\\section{Array}\n" +
                "\n" +
                "List of Countries and their Capitals\n" +
                "\n" +
                "\\begin{itemize}\n" +
                "    \\multido{\\i=1+1}{\\ListSize}{\n" +
                "        \\item \\CountryList(\\i) : \\CityList(\\i)\n" +
                "    }\n" +
                "\\end{itemize}\n" +
                "\n" +
                "\\end{document}\n";
    }

}
