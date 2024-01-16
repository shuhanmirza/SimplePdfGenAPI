package org.shuhanmirza.simplepdfgenapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.shuhanmirza.simplepdfgenapi.component.pdfgenerator.HtmlTemplatePdfGenerator;
import org.shuhanmirza.simplepdfgenapi.component.pdfgenerator.LatexTemplatePdfGenerator;
import org.shuhanmirza.simplepdfgenapi.dto.PdfBuildingInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class SimplePdfGenApplicationTests {

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
                                .stringMap(Map.of("title", "Invoice #100", "date", "01/01/2024", "logoUrl", "https://logohistory.net/wp-content/uploads/2023/02/Batman-Logo-2016-2048x1152.png"))
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
        return "<!-- thanks https://htmlpdfapi.com/blog/free_html5_invoice_templates-->\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <title>Example HTML</title>\n" +
                "    <style>\n" +
                "        @font-face {\n" +
                "            font-family: Junge;\n" +
                "            src: url(https://s3-eu-west-1.amazonaws.com/htmlpdfapi.production/free_html5_invoice_templates/example3/Junge-Regular.ttf);\n" +
                "        }\n" +
                "\n" +
                "        .clearfix:after {\n" +
                "            content: \"\";\n" +
                "            display: table;\n" +
                "            clear: both;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            color: #001028;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        @page {\n" +
                "            size: A4 portrait;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            font-family: Junge;\n" +
                "            position: relative;\n" +
                "            margin: 0 auto;\n" +
                "            color: #001028;\n" +
                "            background: #FFFFFF;\n" +
                "            font-size: 14px;\n" +
                "        }\n" +
                "\n" +
                "        .arrow {\n" +
                "            margin-bottom: 4px;\n" +
                "        }\n" +
                "\n" +
                "        .arrow.back {\n" +
                "            text-align: right;\n" +
                "        }\n" +
                "\n" +
                "        .inner-arrow {\n" +
                "            padding-right: 10px;\n" +
                "            height: 30px;\n" +
                "            display: inline-block;\n" +
                "            background-color: rgb(233, 125, 49);\n" +
                "            text-align: center;\n" +
                "\n" +
                "            line-height: 30px;\n" +
                "            vertical-align: middle;\n" +
                "        }\n" +
                "\n" +
                "        .arrow.back .inner-arrow {\n" +
                "            background-color: rgb(233, 217, 49);\n" +
                "            padding-right: 0;\n" +
                "            padding-left: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .arrow:before,\n" +
                "        .arrow:after {\n" +
                "            content: '';\n" +
                "            display: inline-block;\n" +
                "            width: 0;\n" +
                "            height: 0;\n" +
                "            border: 15px solid transparent;\n" +
                "            vertical-align: middle;\n" +
                "        }\n" +
                "\n" +
                "        .arrow:before {\n" +
                "            border-top-color: rgb(233, 125, 49);\n" +
                "            border-bottom-color: rgb(233, 125, 49);\n" +
                "            border-right-color: rgb(233, 125, 49);\n" +
                "        }\n" +
                "\n" +
                "        .arrow.back:before {\n" +
                "            border-top-color: transparent;\n" +
                "            border-bottom-color: transparent;\n" +
                "            border-right-color: rgb(233, 217, 49);\n" +
                "            border-left-color: transparent;\n" +
                "        }\n" +
                "\n" +
                "        .arrow:after {\n" +
                "            border-left-color: rgb(233, 125, 49);\n" +
                "        }\n" +
                "\n" +
                "        .arrow.back:after {\n" +
                "            border-left-color: rgb(233, 217, 49);\n" +
                "            border-top-color: rgb(233, 217, 49);\n" +
                "            border-bottom-color: rgb(233, 217, 49);\n" +
                "            border-right-color: transparent;\n" +
                "        }\n" +
                "\n" +
                "        .arrow span {\n" +
                "            display: inline-block;\n" +
                "            width: 80px;\n" +
                "            margin-right: 20px;\n" +
                "            text-align: right;\n" +
                "        }\n" +
                "\n" +
                "        .arrow.back span {\n" +
                "            margin-right: 0;\n" +
                "            margin-left: 20px;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            color: #5D6975;\n" +
                "            font-family: Junge;\n" +
                "            font-size: 2.4em;\n" +
                "            line-height: 1.4em;\n" +
                "            font-weight: normal;\n" +
                "            text-align: center;\n" +
                "            border-top: 1px solid #5D6975;\n" +
                "            border-bottom: 1px solid #5D6975;\n" +
                "            margin: 0 0 2em 0;\n" +
                "        }\n" +
                "\n" +
                "        h1 small {\n" +
                "            font-size: 0.45em;\n" +
                "            line-height: 1.5em;\n" +
                "            float: left;\n" +
                "        }\n" +
                "\n" +
                "        h1 small:last-child {\n" +
                "            float: right;\n" +
                "        }\n" +
                "\n" +
                "        #project {\n" +
                "            float: left;\n" +
                "        }\n" +
                "\n" +
                "        #company {\n" +
                "            float: right;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "            border-spacing: 0;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "\n" +
                "        table th,\n" +
                "        table td {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        table th {\n" +
                "            padding: 5px 20px;\n" +
                "            color: #5D6975;\n" +
                "            border-bottom: 1px solid #C1CED9;\n" +
                "            white-space: nowrap;\n" +
                "            font-weight: normal;\n" +
                "        }\n" +
                "\n" +
                "        table .service,\n" +
                "        table .desc {\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        table td {\n" +
                "            padding: 20px;\n" +
                "            text-align: right;\n" +
                "        }\n" +
                "\n" +
                "        table td.service,\n" +
                "        table td.desc {\n" +
                "            vertical-align: top;\n" +
                "        }\n" +
                "\n" +
                "        table td.unit,\n" +
                "        table td.qty,\n" +
                "        table td.total {\n" +
                "            font-size: 1.2em;\n" +
                "        }\n" +
                "\n" +
                "        table td.sub {\n" +
                "            border-top: 1px solid #C1CED9;\n" +
                "        }\n" +
                "\n" +
                "        table td.grand {\n" +
                "            border-top: 1px solid #5D6975;\n" +
                "        }\n" +
                "\n" +
                "        table tr:nth-child(2n-1) td {\n" +
                "            background: #EEEEEE;\n" +
                "        }\n" +
                "\n" +
                "        table tr:last-child td {\n" +
                "            background: #DDDDDD;\n" +
                "        }\n" +
                "\n" +
                "        #details {\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "\n" +
                "        footer {\n" +
                "            color: #5D6975;\n" +
                "            width: 100%;\n" +
                "            height: 30px;\n" +
                "            position: absolute;\n" +
                "            bottom: 0;\n" +
                "            border-top: 1px solid #C1CED9;\n" +
                "            padding: 8px 0;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <main>\n" +
                "        <h1 class=\"clearfix\"><small><span>DATE</span><br /><span th:text=\"${date}\"></span></small> <span\n" +
                "                th:text=\"${title}\"></span> <small><span>DUE\n" +
                "                    DATE</span><br /><span th:text=\"${dueDate}\"></span></small></h1>\n" +
                "        <table>\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th class=\"service\">SERVICE</th>\n" +
                "                    <th class=\"desc\">DESCRIPTION</th>\n" +
                "                    <th>PRICE</th>\n" +
                "                    <th>QTY</th>\n" +
                "                    <th>TOTAL</th>\n" +
                "                </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "                <tr th:each=\"service, serviceStat : ${serviceList}\">\n" +
                "                    <td class=\"service\" th:text=\"${serviceList.get(serviceStat.index)}\"></td>\n" +
                "                    <td class=\"desc\" th:text=\"${descriptionList.get(serviceStat.index)}\"></td>\n" +
                "                    <td class=\"unit\" th:text=\"${unitList.get(serviceStat.index)}\"></td>\n" +
                "                    <td class=\"qty\" th:text=\"${quantityList.get(serviceStat.index)}\"></td>\n" +
                "                    <td class=\"total\" th:text=\"${totalAmountList.get(serviceStat.index)}\">$1</td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <tr>\n" +
                "                    <td colspan=\"4\" class=\"sub\">SUBTOTAL</td>\n" +
                "                    <td class=\"sub total\" th:text=\"${subtotal}\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td colspan=\"4\" th:text=\"${taxTitle}\"></td>\n" +
                "                    <td class=\"total\" th:text=\"${taxAmount}\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td colspan=\"4\" class=\"grand total\">GRAND TOTAL</td>\n" +
                "                    <td class=\"grand total\" th:text=\"${grandTotal}\"></td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "        <div id=\"details\" class=\"clearfix\">\n" +
                "            <div id=\"project\">\n" +
                "                <div class=\"arrow\">\n" +
                "                    <div class=\"inner-arrow\"><span>PROJECT</span> Website development</div>\n" +
                "                </div>\n" +
                "                <div class=\"arrow\">\n" +
                "                    <div class=\"inner-arrow\"><span>CLIENT</span> John Doe</div>\n" +
                "                </div>\n" +
                "                <div class=\"arrow\">\n" +
                "                    <div class=\"inner-arrow\"><span>ADDRESS</span> 796 Silver Harbour, TX 79273, US</div>\n" +
                "                </div>\n" +
                "                <div class=\"arrow\">\n" +
                "                    <div class=\"inner-arrow\"><span>EMAIL</span> <a href=\"mailto:john@example.com\">john@example.com</a>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div id=\"company\">\n" +
                "                <div class=\"arrow back\">\n" +
                "                    <div class=\"inner-arrow\">Company Name <span>COMPANY</span></div>\n" +
                "                </div>\n" +
                "                <div class=\"arrow back\">\n" +
                "                    <div class=\"inner-arrow\">455 Foggy Heights, AZ 85004, US <span>ADDRESS</span></div>\n" +
                "                </div>\n" +
                "                <div class=\"arrow back\">\n" +
                "                    <div class=\"inner-arrow\">(602) 519-0450 <span>PHONE</span></div>\n" +
                "                </div>\n" +
                "                <div class=\"arrow back\">\n" +
                "                    <div class=\"inner-arrow\"><a href=\"mailto:company@example.com\">company@example.com</a>\n" +
                "                        <span>EMAIL</span></div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div id=\"notices\">\n" +
                "            <div>NOTICE:</div>\n" +
                "            <div class=\"notice\">A finance charge of 1.5% will be made on unpaid balances after 30 days.</div>\n" +
                "        </div>\n" +
                "    </main>\n" +
                "    <footer>\n" +
                "        Invoice was created on a computer and is valid without the signature and seal.\n" +
                "    </footer>\n" +
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
