package vn.vnpt.tracebility_custom.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Builder
@Data
public class PageRP<T> {

    @JsonProperty("rs")
    @Builder.Default
    private Object content = Collections.emptyList();

    @JsonProperty("total_item")
    private long total = 0;

    public List<T> toList(){

        if (content instanceof List){

            return  (List) content;
        }
        return Collections.emptyList();
    }

}
