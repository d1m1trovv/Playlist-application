package webprogramming.playlistapp.services;

import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.entities.*;

import java.util.List;

public interface UserService {

    public User createUser(UserDto userDto);
    public List<User> findAll();
    public User findUserByEmail(String email);
    public void subscribe(String username, int playlistID);
    public List<Subscription> findAllUserSubscriptions(String email);
    public List<Playlist> findAllUserPlaylists(String email);
    public List<Playlist> findAllNonSubscribedPlaylists(String email);
    public void cancelSubscription(int playlistId, String email);
    public Subscription findSubscriptionByUserIdAndPlaylist(int userId, int playlistId);
    Invoice findInvoiceByUsername(String username);
    List<Role> findAllRoles();
    boolean checkIfUserExists(String email);
    boolean passwordMatches(UserDto userDto);

}
