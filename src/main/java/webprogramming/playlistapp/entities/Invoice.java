package webprogramming.playlistapp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "invoice_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "subscription_id")
    Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
