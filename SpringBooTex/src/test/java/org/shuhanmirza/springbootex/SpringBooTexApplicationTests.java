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
        return "<!-- Source https://github.com/DocRaptor/html-to-pdf-templates -->\n" +
                "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<style>\n" +
                "    /* \n" +
                "    Import the desired font from Google fonts. \n" +
                "    */\n" +
                "    @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&amp;amp;display=swap');\n" +
                "    \n" +
                "    /* \n" +
                "    Define all colors used in this template \n" +
                "    */\n" +
                "    :root{\n" +
                "      --font-color: black;\n" +
                "      --highlight-color: #60D0E4;\n" +
                "      --header-bg-color: #B8E6F1;\n" +
                "      --footer-bg-color: #BFC0C3;\n" +
                "      --table-row-separator-color: #BFC0C3;\n" +
                "    }\n" +
                "    \n" +
                "    @page{\n" +
                "      /*\n" +
                "      This CSS highlights how page sizes, margins, and margin boxes are set.\n" +
                "      https://docraptor.com/documentation/article/1067959-size-dimensions-orientation\n" +
                "    \n" +
                "      Within the page margin boxes content from running elements is used instead of a \n" +
                "      standard content string. The name which is passed in the element() function can\n" +
                "      be found in the CSS code below in a position property and is defined there by \n" +
                "      the running() function.\n" +
                "      */\n" +
                "      size:A4;\n" +
                "      margin:8cm 0 3cm 0;\n" +
                "    \n" +
                "      @top-left{\n" +
                "          content:element(header);\n" +
                "      }\n" +
                "    \n" +
                "      @bottom-left{\n" +
                "          content:element(footer);\n" +
                "      }\n" +
                "    }\n" +
                "    \n" +
                "    /* \n" +
                "    The body itself has no margin but a padding top &amp; bottom 1cm and left &amp; right 2cm.\n" +
                "    Additionally the default font family, size and color for the document is defined\n" +
                "    here.\n" +
                "    */\n" +
                "    body{\n" +
                "      margin:0;\n" +
                "      padding:1cm 2cm;\n" +
                "      color:var(--font-color);\n" +
                "      font-family: 'Montserrat', sans-serif;\n" +
                "      font-size:10pt;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The links in the document should not be highlighted by an different color and underline\n" +
                "    instead we use the color value inherit to get the current texts color.\n" +
                "    */\n" +
                "    a{\n" +
                "      color:inherit;\n" +
                "      text-decoration:none;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    For the dividers in the document we use an HR element with a margin top and bottom \n" +
                "    of 1cm, no height and only a border top of one millimeter.\n" +
                "    */\n" +
                "    hr{\n" +
                "      margin:1cm 0;\n" +
                "      height:0;\n" +
                "      border:0;\n" +
                "      border-top:1mm solid var(--highlight-color);\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The page header in our document uses the HTML HEADER element, we define a height \n" +
                "    of 8cm matching the margin top of the page (see @page rule) and a padding left\n" +
                "    and right of 2cm. We did not give the page itself a margin of 2cm to ensure that\n" +
                "    the background color goes to the edges of the document.\n" +
                "    \n" +
                "    As mentioned above in the comment for the @page the position property with the \n" +
                "    value running(header) makes this HTML element float into the top left page margin\n" +
                "    box. This page margin box repeats on every page in case we would have a multi-page\n" +
                "    invoice.\n" +
                "    */\n" +
                "    header{\n" +
                "      height:8cm;\n" +
                "      padding:0 2cm;\n" +
                "      position:running(header);\n" +
                "      background-color:var(--header-bg-color);\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    For the different sections in the header we use some flexbox and keep space between\n" +
                "    with the justify-content property.\n" +
                "    */\n" +
                "    header .headerSection{\n" +
                "      display:flex;\n" +
                "      justify-content:space-between;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    To move the first sections a little down and have more space between the top of \n" +
                "    the document and the logo/company name we give the section a padding top of 5mm.\n" +
                "    */\n" +
                "    header .headerSection:first-child{\n" +
                "      padding-top:.5cm;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    Similar we keep some space at the bottom of the header with the padding-bottom\n" +
                "    property.\n" +
                "    */\n" +
                "    header .headerSection:last-child{\n" +
                "      padding-bottom:.5cm;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    Within the header sections we have defined two DIV elements, and the last one in\n" +
                "    each headerSection element should only take 35% of the headers width.\n" +
                "    */\n" +
                "    header .headerSection div:last-child{\n" +
                "      width:35%;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    For the logo, where we use an SVG image and the company text we also use flexbox\n" +
                "    to align them correctly.\n" +
                "    */\n" +
                "    header .logoAndName{\n" +
                "      display:flex;\n" +
                "      align-items:center;\n" +
                "      justify-content:space-between;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The SVG gets set to a fixed size and get 5mm margin right to keep some distance\n" +
                "    to the company name.\n" +
                "    */\n" +
                "    header .logoAndName svg{\n" +
                "      width:1.5cm;\n" +
                "      height:1.5cm;\n" +
                "      margin-right:.5cm;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    To ensure the top right section \"Invoice #100\" starts on the same level as the Logo &amp;\n" +
                "    Name we set a padding top of 1cm for this element.\n" +
                "    */\n" +
                "    header .headerSection .invoiceDetails{\n" +
                "      padding-top:.5cm;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The H3 element \"ISSUED TO\" gets another 25mm margin to the right to keep some \n" +
                "    space between this header and the client's address.\n" +
                "    Additionally this header text gets the hightlight color as font color.\n" +
                "    */\n" +
                "    header .headerSection h3{\n" +
                "      margin:0 .75cm 0 0;\n" +
                "      color:var(--highlight-color);\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    Put some margin between the \"DUE DATE\" and \"AMOUNT\" headings.\n" +
                "    */\n" +
                "    header .headerSection div:last-of-type h3:last-of-type{\n" +
                "      margin-top:.5cm;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The paragraphs within the header sections DIV elements get a small 2px margin top\n" +
                "    to ensure its in line with the \"ISSUED TO\" header text.\n" +
                "    */\n" +
                "    header .headerSection div p{\n" +
                "      margin-top:2px;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    All header elements and paragraphs within the HTML HEADER tag get a margin of 0.\n" +
                "    */\n" +
                "    header h1,\n" +
                "    header h2,\n" +
                "    header h3,\n" +
                "    header p{\n" +
                "      margin:0;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The invoice details should not be uppercase and also be aligned to the right.\n" +
                "    */\n" +
                "    header .invoiceDetails,\n" +
                "    header .invoiceDetails h2{\n" +
                "      text-align:right;\n" +
                "      font-size:1em;\n" +
                "      text-transform:none;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    Heading of level 2 and 3 (\"DUE DATE\", \"AMOUNT\" and \"INVOICE TO\") need to be written in \n" +
                "    uppercase, so we use the text-transform property for that.\n" +
                "    */\n" +
                "    header h2,\n" +
                "    header h3{\n" +
                "      text-transform:uppercase;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The divider in the HEADER element gets a slightly different margin than the \n" +
                "    standard dividers.\n" +
                "    */\n" +
                "    header hr{\n" +
                "      margin:1cm 0 .5cm 0;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    Our main content is all within the HTML MAIN element. In this template this are\n" +
                "    two tables. The one which lists all items and the table which shows us the \n" +
                "    subtotal, tax and total amount.\n" +
                "    \n" +
                "    Both tables get the full width and collapse the border.\n" +
                "    */\n" +
                "    main table{\n" +
                "      width:100%;\n" +
                "      border-collapse:collapse;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    We put the first tables headers in a THEAD element, this way they repeat on the\n" +
                "    next page if our table overflows to multiple pages.\n" +
                "    \n" +
                "    The text color gets set to the highlight color.\n" +
                "    */\n" +
                "    main table thead th{\n" +
                "      height:1cm;\n" +
                "      color:var(--highlight-color);\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    For the last three columns we set a fixed width of 2.5cm, so if we would change\n" +
                "    the documents size only the first column with the item name and description grows.\n" +
                "    */\n" +
                "    main table thead th:nth-of-type(2),\n" +
                "    main table thead th:nth-of-type(3),\n" +
                "    main table thead th:last-of-type{\n" +
                "      width:2.5cm;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The items itself are all with the TBODY element, each cell gets a padding top\n" +
                "    and bottom of 2mm.\n" +
                "    */\n" +
                "    main table tbody td{\n" +
                "      padding:2mm 0;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The cells in the last column (in this template the column containing the total)\n" +
                "    get a text align right so the text is at the end of the table.\n" +
                "    */\n" +
                "    main table thead th:last-of-type,\n" +
                "    main table tbody td:last-of-type{\n" +
                "      text-align:right;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    By default text within TH elements is aligned in the center, we do not want that\n" +
                "    so we overwrite it with an left alignment.\n" +
                "    */\n" +
                "    main table th{\n" +
                "      text-align:left;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The summary table, so the table containing the subtotal, tax and total amount \n" +
                "    gets a width of 40% + 2cm. The plus 2cm is added because our body has a 2cm padding\n" +
                "    but we want our highlight color for the total row to go to the edge of the document.\n" +
                "    \n" +
                "    To move the table to the right side we simply set a margin-left of 60%.\n" +
                "    */\n" +
                "    main table.summary{\n" +
                "      width:calc(40% + 2cm);\n" +
                "      margin-left:60%;\n" +
                "      margin-top:.5cm;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The row containing the total amount gets its background color set to the highlight \n" +
                "    color and the font weight to bold.\n" +
                "    */\n" +
                "    main table.summary tr.total{\n" +
                "      font-weight:bold;\n" +
                "      background-color:var(--highlight-color);\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The TH elements of the summary table are not on top but the cells on the left side\n" +
                "    these get a padding left of 1cm to give the highlight color some space.\n" +
                "    */\n" +
                "    main table.summary th{\n" +
                "      padding:4mm 0 4mm 1cm;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    As only the highlight background color should go to the edge of the document\n" +
                "    but the text should still have the 2cm distance, we set the padding right to \n" +
                "    2cm.\n" +
                "    */\n" +
                "    main table.summary td{\n" +
                "      padding:4mm 2cm 4mm 0;\n" +
                "      border-bottom:0;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The content below the tables is placed in a ASIDE element next to the MAIN element.\n" +
                "    To ensure this element is always at the bottom of the page, just above the page \n" +
                "    footer, we use the Prince custom property \"-prince-float\" with the value bottom.\n" +
                "    \n" +
                "    See Page Floats on https://www.princexml.com/howcome/2021/guides/float/.\n" +
                "    */\n" +
                "    aside{\n" +
                "      -prince-float: bottom;\n" +
                "      padding:0 2cm .5cm 2cm;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The content itself is shown in 2 columns we use flexbox for this.\n" +
                "    */\n" +
                "    aside > div{\n" +
                "      display:flex;\n" +
                "      justify-content:space-between;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    Each \"column\" has a width of 45% of the document.\n" +
                "    */\n" +
                "    aside > div > div{\n" +
                "      width:45%;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The list with the payment options has no bullet points and no margin.\n" +
                "    */\n" +
                "    aside > div > div ul{\n" +
                "      list-style-type:none;\n" +
                "      margin:0;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The page footer in our document uses the HTML FOOTER element, we define a height \n" +
                "    of 3cm matching the margin bottom of the page (see @page rule) and a padding left\n" +
                "    and right of 2cm. We did not give the page itself a margin of 2cm to ensure that\n" +
                "    the background color goes to the edges of the document.\n" +
                "    \n" +
                "    As mentioned above in the comment for the @page the position property with the \n" +
                "    value running(footer) makes this HTML element float into the bottom left page margin\n" +
                "    box. This page margin box repeats on every page in case we would have a multi-page\n" +
                "    invoice.\n" +
                "    \n" +
                "    The content inside the footer is aligned with the help of line-height 3cm and a \n" +
                "    flexbox for the child elements.\n" +
                "    */\n" +
                "    footer{\n" +
                "      height:3cm;\n" +
                "      line-height:3cm;\n" +
                "      padding:0 2cm;\n" +
                "      position:running(footer);\n" +
                "      background-color:var(--footer-bg-color);\n" +
                "      font-size:8pt;\n" +
                "      display:flex;\n" +
                "      align-items:baseline;\n" +
                "      justify-content:space-between;\n" +
                "    }\n" +
                "    \n" +
                "    /*\n" +
                "    The first link in the footer, which points to the company website is highlighted \n" +
                "    in bold.\n" +
                "    */\n" +
                "    footer a:first-child{\n" +
                "      font-weight:bold;\n" +
                "    }\n" +
                "    \n" +
                "    </style>\n" +
                "    <!-- The header element will appear on the top of each page of this invoice document. -->\n" +
                "    <header>\n" +
                "      <div class=\"headerSection\">\n" +
                "        <!-- As a logo we take an SVG element and add the name in an standard H1 element behind it. -->\n" +
                "        <div class=\"logoAndName\">\n" +
                "            <img th:src=\"${logoUrl}\" style=\"width: 60px;\"/>\n" +
                "          <h1>Logo &amp; Name</h1>\n" +
                "        </div>\n" +
                "        <!-- Details about the invoice are on the right top side of each page. -->\n" +
                "        <div class=\"invoiceDetails\">\n" +
                "          <h2 th:text=\"${title}\"></h2>\n" +
                "          <p th:text=\"${date}\"></p>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "      <!-- The two header rows are divided by an blue line, we use the HR element for this. -->\n" +
                "      <hr />\n" +
                "      <div class=\"headerSection\">\n" +
                "        <!-- The clients details come on the left side below the logo and company name. -->\n" +
                "        <div>\n" +
                "          <h3>Invoice to</h3>\n" +
                "          <p>\n" +
                "            <b>Client Name</b>\n" +
                "            <br />\n" +
                "            123 Alphabet Road, Suite 01\n" +
                "            <br />\n" +
                "            Indianapolis, IN 46260\n" +
                "            <br />\n" +
                "            <a href=\"mailto:clientname@clientwebsite.com\">\n" +
                "              clientname@clientwebsite.com\n" +
                "            </a>\n" +
                "            <br />\n" +
                "            317.123.8765\n" +
                "          </p>\n" +
                "        </div>\n" +
                "        <!-- Additional details can be placed below the invoice details. -->\n" +
                "        <div>\n" +
                "          <h3>Due Date</h3>\n" +
                "          <p>\n" +
                "            <b>07 April 2021</b>\n" +
                "          </p>\n" +
                "          <h3>Amount</h3>\n" +
                "          <p>\n" +
                "            <b>$3,500</b>\n" +
                "          </p>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </header>\n" +
                "    \n" +
                "    <!-- The footer contains the company's website and address. To align the address details we will use flexbox in the CSS style. -->\n" +
                "    <footer>\n" +
                "        <a href=\"https://companywebsite.com\">\n" +
                "          companywebsite.com\n" +
                "        </a>\n" +
                "        <a href=\"mailto:company@website.com\">\n" +
                "          company@website.com\n" +
                "        </a>\n" +
                "        <span>\n" +
                "          317.123.8765\n" +
                "        </span>\n" +
                "        <span>\n" +
                "          123 Alphabet Road, Suite 01, Indianapolis, IN 46260\n" +
                "        </span>\n" +
                "    </footer>\n" +
                "    \n" +
                "    <!-- In the main section the table for the separate items is added. Also we add another table for the summary, so subtotal, tax and total amount. -->\n" +
                "    <main>\n" +
                "      <table>\n" +
                "        <!-- A THEAD element is used to ensure the header of the table is repeated if it consumes more than one page. -->\n" +
                "        <thead>\n" +
                "          <tr>\n" +
                "            <th>Item Description</th>\n" +
                "            <th>Rate</th>\n" +
                "            <th>Amount</th>\n" +
                "            <th>Total</th>\n" +
                "          </tr>\n" +
                "        </thead>\n" +
                "        <!-- The single invoice items are all within the TBODY of the table. -->\n" +
                "        <tbody>\n" +
                "          <tr>\n" +
                "            <td>\n" +
                "              <b>Item Names Goes Here</b>\n" +
                "              <br />\n" +
                "              Description goes here\n" +
                "            </td>\n" +
                "            <td>\n" +
                "              $100\n" +
                "            </td>\n" +
                "            <td>\n" +
                "              4\n" +
                "            </td>\n" +
                "            <td>\n" +
                "              $400.00\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td>\n" +
                "              <b>Lorem Ipsum</b>\n" +
                "              <br />\n" +
                "              Description goes here\n" +
                "            </td>\n" +
                "            <td>\n" +
                "              $250\n" +
                "            </td>\n" +
                "            <td>\n" +
                "              2\n" +
                "            </td>\n" +
                "            <td>\n" +
                "              $500.00\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td>\n" +
                "              <b>Dolor Set Amit Caslum</b>\n" +
                "              <br />\n" +
                "              Description goes here\n" +
                "            </td>\n" +
                "            <td>\n" +
                "              $300\n" +
                "            </td>\n" +
                "            <td>\n" +
                "              1\n" +
                "            </td>\n" +
                "            <td>\n" +
                "              $300.00\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <!-- The summary table contains the subtotal, tax and total amount. -->\n" +
                "      <table class=\"summary\">\n" +
                "        <tr>\n" +
                "          <th>\n" +
                "            Subtotal\n" +
                "          </th>\n" +
                "          <td>\n" +
                "            $1200.00\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <th>\n" +
                "            Tax 4.7%\n" +
                "          </th>\n" +
                "          <td>\n" +
                "            $000.00\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <tr class=\"total\">\n" +
                "          <th>\n" +
                "            Total\n" +
                "          </th>\n" +
                "          <td>\n" +
                "            $12,000.00\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </main>\n" +
                "    <!-- Within the aside tag we will put the terms and conditions which shall be shown below the invoice table. -->\n" +
                "    <aside>\n" +
                "      <!-- Before the terms and conditions we will add another blue divider line with the help of the HR tag. -->\n" +
                "      <hr />\n" +
                "      <div>\n" +
                "        <div>\n" +
                "          <b>Terms &amp; Conditions</b>\n" +
                "          <p>\n" +
                "            Please make payment within 30 days of issue of the invoice.\n" +
                "          </p>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "          <b>Payment Options</b>\n" +
                "          <ul>\n" +
                "            <li>Paypal</li>\n" +
                "            <li>Credit Card</li>\n" +
                "          </ul>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </aside>\n" +
                "\n" +
                "</html>\n";
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
