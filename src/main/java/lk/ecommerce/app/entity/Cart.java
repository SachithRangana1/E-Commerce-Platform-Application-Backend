package lk.ecommerce.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@ToString(exclude = "productList")
@Table(name = "cart")
public class Cart {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter(AccessLevel.NONE)
    @JsonBackReference
    private List<Product> productList = new ArrayList<>();

    public Cart(Long id, User user){
        this.id = id;
        this.user = user;
    }

    public Cart(Long id, User user, List<Product> productList){
        this.id = id;
        this.user = user;
        setProductList(productList); // Delegate setting product list to a method
    }

    public void setProductList(List<Product> productList) {
        if (productList != null && !productList.isEmpty()) {
            productList.forEach(product -> {
                if (product.getCart() == null) {
                    product.setCart(this);  // Associate product with this cart
                } else if (product.getCart() != this) {
                    throw new IllegalStateException("Product %s is already associated with another cart".formatted(product.getId()));
                }
            });
        }
        this.productList = productList;
    }
}
