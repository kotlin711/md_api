package org.example.api.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class NoticeDto {

    @JsonIgnore
    private Integer id;

    private String title;

    private String content;

    private List<FillterDto> fillter;
}
