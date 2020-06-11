package webprogramming.playlistapp.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webprogramming.playlistapp.entities.Invoice;

import java.util.List;

@Repository("invoiceRepository")
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Override
    List<Invoice> findAll();
    void deleteById(int id);
    Invoice findBySubscriptionId(int id);
    Invoice findByUserUsername(String username);
}
