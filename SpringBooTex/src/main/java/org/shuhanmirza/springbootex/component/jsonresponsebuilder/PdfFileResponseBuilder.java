package org.shuhanmirza.springbootex.component.jsonresponsebuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.component.ResponseBuilder;
import org.springframework.core.io.InputStreamResource;
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
public class PdfFileResponseBuilder implements ResponseBuilder {

    @Override
    public Mono<ResponseEntity<?>> buildResponse(InputStream inputStream) {
        return Mono.just(
                ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(inputStream))
        );
    }

}
