package org.shuhanmirza.springbootex;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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

    @Test
    void contextLoads() {
        latexTemplatePdfGenerator.generatePdfFromTemplate(
                        PdfBuildingInstruction
                                .builder()
                                .templateString(getTemplate())
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

    String getTemplate() {
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
