package org.shuhanmirza.simplepdfgenapi.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.simplepdfgenapi.component.jsonresponsebuilder.JsonResponseBuilder;
import org.shuhanmirza.simplepdfgenapi.component.jsonresponsebuilder.OctetStreamResponseBuilder;
import org.shuhanmirza.simplepdfgenapi.component.jsonresponsebuilder.PdfFileResponseBuilder;
import org.shuhanmirza.simplepdfgenapi.enums.ResponseType;
import org.springframework.stereotype.Component;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class ResponseBuilderProvider {
    private final JsonResponseBuilder jsonResponseBuilder;
    private final OctetStreamResponseBuilder octetStreamResponseBuilder;
    private final PdfFileResponseBuilder pdfFileResponseBuilder;

    public ResponseBuilder getResponseBuilder(ResponseType responseType) {
        return switch (responseType) {
            case JSON -> jsonResponseBuilder;
            case OCTET_STREAM -> octetStreamResponseBuilder;
            case PDF -> pdfFileResponseBuilder;
            default -> throw new RuntimeException(String.format("NO RESPONSE BUILDER FOR TYPE %s", responseType));
        };
    }
}
