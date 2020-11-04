package webprogramming.playlistapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webprogramming.playlistapp.entities.Role;
import webprogramming.playlistapp.entities.ERole;

import java.util.List;
import java.util.Optional;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
    List<Role> findAll();

}
