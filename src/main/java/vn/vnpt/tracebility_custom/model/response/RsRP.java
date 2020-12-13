package vn.vnpt.tracebility_custom.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import vn.vnpt.tracebility_custom.util.Constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class RsRP<T> {

    @JsonProperty(Constant.RESPONSE.RS)
    List<T> content = Collections.emptyList();

    public RsRP(T content) {
        if (content != null) {
            this.content = Arrays.asList(content);
        }

    }

    public RsRP(List<T> content) {
        if (content != null)
            this.content = content;
    }
}
