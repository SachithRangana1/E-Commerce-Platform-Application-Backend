package lk.ecommerce.app.dto;

import jakarta.persistence.*;
import lk.ecommerce.app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;

    private Long user_id;
}
