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
@Table(name = "my_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime orderDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Detailing> detailings = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Interior> interiors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Exterior> exteriors = new ArrayList<>();

    @ManyToOne
    private MyUser user;

    public void addProductToOrder(Product p) {
        this.products.add(p);
    }

    public void addDetailingToOrder(Detailing d) {
        this.detailings.add(d);
    }

    public void addInteriorToOrder(Interior i) {
        this.interiors.add(i);
    }

    public void addExteriorToOrder(Exterior e) {
        this.exteriors.add(e);
    }

    public void addProductToFavorites(Product p) {
        this.products.add(p);
    }

    public void addDetailingToFavorites(Detailing d) {
        this.detailings.add(d);
    }

    public void addInteriorToFavorites(Interior i) {
        this.interiors.add(i);
    }

    public void addExteriorToFavorites(Exterior e) {
        this.exteriors.add(e);
    }

}
