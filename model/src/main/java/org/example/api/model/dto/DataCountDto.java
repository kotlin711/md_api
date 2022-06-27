package org.example.api.model.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DataCountDto {
    private String name;
    private Integer value;
}
