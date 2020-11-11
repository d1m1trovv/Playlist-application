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

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @NotEmpty(message = "Username is required")
    @Length(min = 7, message = "Username should have at least 7 characters")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Provide valid email")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password is required")
    @Length(min = 10, message = "Password should be at least 10 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "isactive")
    private int isActive;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_playlists",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    private Set<Playlist> userPlaylists = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Subscription> subscriptions;

    @OneToMany(mappedBy = "user")
    private Set<Invoice> invoices;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof User) {
            return ((User) o).getEmail().equals(getEmail());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    public User() {
    }

    public User(long id, String username, String email, String password,
                int isActive, Collection<Role> roles, Set<Playlist> userPlaylists,
                Set<Subscription> subscriptions, Set<Invoice> invoices){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
        this.userPlaylists = userPlaylists;
        this.subscriptions = subscriptions;
        this.invoices = invoices;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Set<Playlist> getUserPlaylists() {
        return userPlaylists;
    }

    public void setUserPlaylists(Set<Playlist> userPlaylists) {
        this.userPlaylists = userPlaylists;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
}
