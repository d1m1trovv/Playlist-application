package webprogramming.playlistapp.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webprogramming.playlistapp.entities.User;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByUsername(String username);
    @Override
    List<User> findAll();
}