package org.shuhanmirza.springbootex.component;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.io.InputStream;

/**
 * @author Shuhan Mirza
 * @since 10/1/24
 */
public interface ResponseBuilder {
    Mono<ResponseEntity<?>> buildResponse(InputStream inputStream);
}
