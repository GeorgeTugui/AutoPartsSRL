package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serial;
import java.util.*;

@Getter
@Setter
@Entity
public class MyUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(nullable = false, length = 30)
    private String fullName;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column
    private String randomToken;

    @Transient
    private String randomTokenEmail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;

    @Transient
    private String passwordConfirm;

    @Transient
    private List<GrantedAuthority> authorities;

    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    @OneToOne(cascade = CascadeType.ALL)
    private Favorites favorites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    public MyUser() {
        this.shoppingCart = new ShoppingCart();
        this.favorites=new Favorites();
        this.orders = new ArrayList<>();
        this.roles = new HashSet<>();
    }

    public void addOrderToUser(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public MyUser(MyUser myUser) {
        this.enabled = myUser.isEnabled();
        this.roles = myUser.getRoles();
        this.username = myUser.getUsername();
        this.fullName = myUser.getFullName();
        this.id = myUser.getId();
        this.accountNonExpired = myUser.isAccountNonExpired();
        this.accountNonLocked = myUser.isAccountNonLocked();
        this.credentialsNonExpired = myUser.isCredentialsNonExpired();
        this.email = myUser.getEmail();
        this.shoppingCart = myUser.getShoppingCart();
        this.favorites=myUser.getFavorites();
        this.orders = myUser.getOrders();
    }

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

}