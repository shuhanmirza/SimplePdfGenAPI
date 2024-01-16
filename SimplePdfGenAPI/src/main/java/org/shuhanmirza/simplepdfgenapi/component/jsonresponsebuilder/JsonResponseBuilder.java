package org.shuhanmirza.simplepdfgenapi.component.jsonresponsebuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.simplepdfgenapi.component.ResponseBuilder;
import org.shuhanmirza.simplepdfgenapi.dto.response.PdfGenerationResponse;
import org.shuhanmirza.simplepdfgenapi.service.UtilityService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.InputStream;

/**
 * @author Shuhan Mirza
 * @since 10/1/24
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JsonResponseBuilder implements ResponseBuilder {
    private final UtilityService utilityService;

    @Override
    public Mono<ResponseEntity<?>> buildResponse(InputStream inputStream) {
        return Mono.fromFuture(utilityService.convertInputStreamToBase64(inputStream))
                .map(base64String ->
                        ResponseEntity
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(PdfGenerationResponse
                                        .builder()
                                        .pdfBase64(base64String)
                                        .build()
                                )
                );
    }

}
