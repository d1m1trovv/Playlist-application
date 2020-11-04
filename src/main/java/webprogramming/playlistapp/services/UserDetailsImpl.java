package webprogramming.playlistapp.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import webprogramming.playlistapp.entities.Invoice;
import webprogramming.playlistapp.entities.Subscription;
import webprogramming.playlistapp.entities.User;

import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private int isActive;
    private Collection<? extends GrantedAuthority> authorities;
    private Set<Subscription> subscriptions;
    private Set<Invoice> invoices;

    public UserDetailsImpl(Long id, String email, String username, String password, int isActive,
                           Collection<? extends GrantedAuthority> authorities, Set<Subscription> subs,
                           Set<Invoice> invoices){

        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.email = email;
        this.id = id;
        this.subscriptions = subs;
        this.invoices = invoices;
        this.isActive = isActive;
    }

    public static UserDetailsImpl create(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getIsActive(),
                authorities,
                user.getSubscriptions(),
                user.getInvoices()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
