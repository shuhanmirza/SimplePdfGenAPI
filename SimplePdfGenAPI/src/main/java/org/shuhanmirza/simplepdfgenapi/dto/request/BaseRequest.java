package org.shuhanmirza.simplepdfgenapi.dto.request;

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
public abstract class BaseRequest implements Serializable {
}
