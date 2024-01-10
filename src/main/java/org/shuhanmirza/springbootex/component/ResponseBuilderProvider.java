package org.shuhanmirza.springbootex.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.springbootex.component.jsonresponsebuilder.JsonResponseBuilder;
import org.shuhanmirza.springbootex.enums.ResponseType;
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

    public ResponseBuilder getResponseBuilder(ResponseType responseType) {
        return switch (responseType) {
            case JSON -> jsonResponseBuilder;
            default -> throw new RuntimeException(String.format("NO RESPONSE BUILDER FOR TYPE %s", responseType));
        };
    }
}
