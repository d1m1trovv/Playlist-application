package webprogramming.playlistapp.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webprogramming.playlistapp.entities.Subscription;

import java.util.List;

@Repository("subscriptionRepository")
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByUserId(long id);
    void deleteById(long id);
    Subscription findByUserIdAndPlaylistId(long userId,long subId);
    Subscription findSubscriptionById(long id);

}
