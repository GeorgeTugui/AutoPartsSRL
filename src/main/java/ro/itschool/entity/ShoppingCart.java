package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;

    private LocalDateTime orderDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Detailing> detailings = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Interior> interiors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Exterior> exteriors = new ArrayList<>();

    @OneToOne(mappedBy = "shoppingCart")
    private MyUser user;

    public void addProductToShoppingCart(Product p) {
        this.products.add(p);
    }

    public void addInteriorToShoppingCart(Interior i) {
        this.products.add(i);
    }

    public void addExteriorToShoppingCart(Exterior e) {
        this.products.add(e);
    }

    public void addDetailingToShoppingCart(Detailing d) {
        this.products.add(d);
    }

    public void removeProductFromShoppingCart(Product product) {
        this.products.remove(product);
    }

    public void removeDetailingFromShoppingCart(Detailing detailing) {
        this.detailings.remove(detailing);
    }

    public void removeInteriorFromShoppingCart(Interior interior) {
        this.interiors.remove(interior);
    }

    public void removeExteriorFromShoppingCart(Exterior exterior) {
        this.exteriors.remove(exterior);
    }

    public ShoppingCart(Integer id, Integer productId) {
        this.id = id;
        this.productId = productId;
    }
}
