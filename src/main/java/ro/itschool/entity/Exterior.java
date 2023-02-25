package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value = "/exterior_table")
@Table(name = "exterior")
public class Exterior extends Product{

    private String exteriorpartdescription;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

}
