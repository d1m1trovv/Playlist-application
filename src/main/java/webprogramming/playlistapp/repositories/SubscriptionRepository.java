package webprogramming.playlistapp.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webprogramming.playlistapp.entities.Subscription;

import java.util.List;

@Repository("subscriptionRepository")
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    List<Subscription> findAllByUserId(int id);
    void deleteById(int id);
    Subscription findByUserIdAndPlaylistId(int userId, int subId);
    Subscription findSubscriptionById(int id);

}
