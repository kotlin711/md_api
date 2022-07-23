package org.example.api.model.dto;

import lombok.Data;

@Data
public class PromoDto {
        private Integer pid;
        private String title;
        private String content;
        private String provide_name;
        private String promo_amount;
        private String begin_time;
        private String end_time;

}
