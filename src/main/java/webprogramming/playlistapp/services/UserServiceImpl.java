package webprogramming.playlistapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.entities.*;
import webprogramming.playlistapp.repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final PlaylistServiceImpl playlistService;

    private final SubscriptionRepository subscriptionRepository;
    private final PlaylistRepository playlistRepository;
    private final InvoiceRepository invoiceRepository;
    private final RoleRepository roleRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(@Qualifier("subscriptionRepository") SubscriptionRepository subscriptionRepository,
                           @Qualifier("playlistRepository") PlaylistRepository playlistRepository,
                           @Qualifier("invoiceRepository") InvoiceRepository invoiceRepository,
                           @Qualifier("roleRepository") RoleRepository roleRepository,
                           @Qualifier("songRepository") SongRepository songRepository,
                           @Qualifier("userRepository") UserRepository userRepository,
                           @Qualifier("playlistService") PlaylistServiceImpl playlistService,
                           @Qualifier("bCryptPasswordEncoder") BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.invoiceRepository = invoiceRepository;
        this.playlistRepository = playlistRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.roleRepository = roleRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.playlistService = playlistService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setIsActive(1);
        user.setPass(bCryptPasswordEncoder.encode(userDto.getPass()));
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void subscribe(String email, int playlistID) {
        Subscription sub = new Subscription();
        sub.setUser(findUserByEmail(email));
        sub.setPlaylist(playlistService.findById(playlistID));
        sub.setDate(LocalDateTime.now());
        sub.setSubFee(playlistService.findById(playlistID).getSubFee());
        subscriptionRepository.save(sub);
    }

    @Override
    public List<Subscription> findAllUserSubscriptions(String email) {
        return subscriptionRepository.findAllByUserId(findUserByEmail(email).getId());
    }

    @Override
    public List<Playlist> findAllUserPlaylists(String email) {
        List<Subscription> subs = findAllUserSubscriptions(email);
        List<Playlist> allPlaylists = new ArrayList<>();
        for (Subscription sub : subs) {
            allPlaylists.add(sub.getPlaylist());
        }
        return allPlaylists;
    }

    @Override
    public List<Playlist> findAllNonSubscribedPlaylists(String email) {
        List<Playlist> userPlaylists = findAllUserPlaylists(email);
        List<Playlist> allPlaylists = playlistRepository.findAll();
        List<Playlist> nonSubscribed = new ArrayList<>();
        for (Playlist pl : allPlaylists) {
            if (!userPlaylists.contains(pl)) {
                nonSubscribed.add(pl);
            }
        }
        return nonSubscribed;
    }

    @Override
    public void cancelSubscription(int playlistId, String email) {
        List<Subscription> userSubs = findAllUserSubscriptions(email);
        for (Subscription sub : userSubs) {
            if (sub.getPlaylist().getId() == playlistId) {
                subscriptionRepository.deleteById(sub.getId());
            }
        }
    }

    @Override
    public Subscription findSubscriptionByUserIdAndPlaylist(int userId, int playlistId) {
        return subscriptionRepository.findByUserIdAndPlaylistId(userId, playlistId);
    }

    @Override
    public Invoice findInvoiceByUsername(String username) {
        return invoiceRepository.findByUserUsername(username);
    }


    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public boolean checkIfUserExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public boolean passwordMatches(UserDto userDto) {
        return userDto.getPass().equals(userDto.getConfirmPass());
    }
}

