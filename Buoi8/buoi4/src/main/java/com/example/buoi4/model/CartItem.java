package com.example.buoi4.model;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long productId;
    private String productName;
    private Long price;
    private String image;
    private Integer quantity;

    public Long getTotalPrice() {
        return price * quantity;
    }
}
