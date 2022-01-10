package com.wle.blog.vo.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PageParams {
    @JsonProperty(value = "page")
    private int page = 1;
    @JsonProperty(value = "pageParms")
    private int pageParms = 10;
}
