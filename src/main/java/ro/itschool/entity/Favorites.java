package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Detailing> detailings = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Interior> interiors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Exterior> exteriors = new ArrayList<>();

    @OneToOne(mappedBy = "favorites")
    private MyUser user;

    public void addProductToFavorites(Product p) {
        this.products.add(p);
    }

    public void addInteriorToFavorites(Interior i) {
        this.products.add(i);
    }

    public void addExteriorToFavorites(Exterior e) {
        this.products.add(e);
    }

    public void addDetailingToFavorites(Detailing d) {
        this.products.add(d);
    }

    public void removeProductFromFavorites(Product product) {
        this.products.remove(product);
    }

    public void removeDetailingFromFavorites(Detailing detailing) {
        this.detailings.remove(detailing);
    }

    public void removeInteriorFromFavorites(Interior interior) {
        this.interiors.remove(interior);
    }

    public void removeExteriorFromFavorites(Exterior exterior) {
        this.exteriors.remove(exterior);
    }

    public Favorites(Integer id, Integer productId) {
        this.id = id;
        this.productId = productId;
    }
}
