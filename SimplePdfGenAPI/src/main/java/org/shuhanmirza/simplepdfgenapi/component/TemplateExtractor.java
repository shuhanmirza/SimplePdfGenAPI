package org.shuhanmirza.simplepdfgenapi.component;

import reactor.core.publisher.Mono;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
public interface TemplateExtractor {
    Mono<String> getTemplateFromSource(String templateSource);
}
