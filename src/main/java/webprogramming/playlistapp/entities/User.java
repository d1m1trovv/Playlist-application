package webprogramming.playlistapp.entities;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @NotEmpty(message = "Email is required")
    @Email(message = "Provide valid email")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Username is required")
    @Length(min = 7, message = "Username should have at least 7 characters")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Password is required")
    @Length(min = 10, message = "Password should be at least 10 characters")
    @Column(name = "pass")
    private String pass;

    @Column(name = "isactive")
    private int isActive;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Subscription> subscriptions;

    @OneToMany(mappedBy = "user")
    private Set<Invoice> invoices;

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles){
        List<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

}
