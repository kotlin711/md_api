package org.example.api.model.dto;


import lombok.Data;

@Data
public class GoodsDto {
    private Integer id;
    private String name;
    private String amount;
    private String create_time;
    private PromoDto promo;
}
