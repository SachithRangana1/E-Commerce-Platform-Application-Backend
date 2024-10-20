package lk.ecommerce.app.dto;

import jakarta.persistence.*;
import lk.ecommerce.app.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;

    private Long product_id;

}
