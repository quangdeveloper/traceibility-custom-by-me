package vn.vnpt.tracebility_custom.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class SttRP extends IsActiveField {

    private long stt;
}
