package webprogramming.playlistapp.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.entities.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    public void createUser(UserDto userDto);
    public List<User> findAll();
    public User findUserByUsername(String name);
    public User findUserByEmail(String email);
    public void subscribe(String username, long playlistID);
    public List<Subscription> findAllUserSubscriptions(String email);
    public List<Playlist> findAllUserPlaylists(String email);
    public List<Playlist> findAllNonSubscribedPlaylists(String email);
    public void cancelSubscription(long playlistId, String email);
    public Subscription findSubscriptionByUserIdAndPlaylist(long userId, long playlistId);
    Invoice findInvoiceByUsername(String username);
    List<Role> findAllRoles();
    boolean checkIfUserExists(String email);
    boolean passwordMatches(UserDto userDto);
    void deleteUser(long id);
    Optional<User> findUserById(long id);
    User updateUser(User user);

}
