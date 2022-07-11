package org.example.api.model.req;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearch {
    private String  promoTitle;
    private String username;
    private Integer pay_type;
    private Integer page;
    private Integer limit;
    private String start;
    private String end;

}
