package org.example.api.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Data
@RequiredArgsConstructor
public class OrderDto {

    private Integer id;

    private String promoTitle;

    private String orderId;

    private Integer payType;

    private Float provideAmount;

    private String provideName;

    private Integer results;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private LocalDateTime createTime;

    private String username;

}
