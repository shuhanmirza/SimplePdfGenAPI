package org.shuhanmirza.springbootex.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PdfGenerationResponse extends BaseResponse {
    private String pdfBase64;
}
