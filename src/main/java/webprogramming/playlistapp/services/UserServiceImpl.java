package webprogramming.playlistapp.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.PlaylistDto;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.entities.*;
import webprogramming.playlistapp.repositories.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final PlaylistServiceImpl playlistService;
    private final ModelMapper modelMapper;
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
                           @Qualifier("bCryptPasswordEncoder") BCryptPasswordEncoder bCryptPasswordEncoder,
                           @Qualifier("modelMapper") ModelMapper modelMapper) {
        this.invoiceRepository = invoiceRepository;
        this.playlistRepository = playlistRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.roleRepository = roleRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.playlistService = playlistService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createUser(UserDto userDto) {
        Set<Role> roles = new HashSet<>();
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setIsActive(1);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String name) {
        return userRepository.findByEmail(name);
    }

    @Override
    public User findUserByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void subscribe(String email, long playlistID) {
        Subscription sub = new Subscription();
        sub.setUser(findUserByEmail(email));
        sub.setPlaylist(playlistService.findPlaylistById(playlistID).get());
        sub.setDate(LocalDateTime.now());
        sub.setSubFee(playlistService.findPlaylistById(playlistID).get().getSubFee());
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
    public void cancelSubscription(long playlistId, String email) {
        List<Subscription> userSubs = findAllUserSubscriptions(email);
        for (Subscription sub : userSubs) {
            if (sub.getPlaylist().getId() == playlistId) {
                subscriptionRepository.deleteById(sub.getId());
            }
        }
    }

    @Override
    public Subscription findSubscriptionByUserIdAndPlaylist(long userId, long playlistId) {
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
        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto convertUserToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPlaylists(user.getUserPlaylists());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    @Override
    public User convertUserDtoToEntity(UserDto userDto) throws ParseException {
        User user = modelMapper.map(userDto, User.class);
        if(userDto.getId() != null){
            user.setId(userDto.getId());
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setUserPlaylists(userDto.getPlaylists());
        }
        return user;
    }

}

