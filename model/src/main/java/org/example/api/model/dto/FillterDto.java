package org.example.api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FillterDto {

    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer nid;
    private String text;
    private Integer action;
    private String data;
}
