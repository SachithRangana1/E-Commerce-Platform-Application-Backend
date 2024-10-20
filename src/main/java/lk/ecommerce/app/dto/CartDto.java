package lk.ecommerce.app.dto;

import jakarta.persistence.*;
import lk.ecommerce.app.entity.Product;
import lk.ecommerce.app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;

    private Long user_id;

    private List<Long> product_ids;
}
