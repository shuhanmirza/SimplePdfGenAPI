package org.shuhanmirza.springbootex.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author Shuhan Mirza
 * @since 27/12/23
 */
@Data
@SuperBuilder
@AllArgsConstructor
public abstract class BaseResponse implements Serializable {
}
