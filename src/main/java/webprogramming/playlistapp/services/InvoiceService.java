package webprogramming.playlistapp.services;

import org.springframework.stereotype.Service;
import webprogramming.playlistapp.entities.Invoice;

import java.util.List;

public interface InvoiceService {

    List<Invoice> findAll();
    Invoice findBySubscriptionId(int subId);
    Invoice createInvoice(int subId);
}
