package org.shuhanmirza.simplepdfgenapi.component.templateextractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shuhanmirza.simplepdfgenapi.component.TemplateExtractor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Base64;

/**
 * @author Shuhan Mirza
 * @since 1/1/24
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class Base64TemplateExtractor implements TemplateExtractor {

    @Override
    public Mono<String> getTemplateFromSource(String templateSource) {
        return Mono.just(new String(Base64.getDecoder().decode(templateSource)));
    }
}
