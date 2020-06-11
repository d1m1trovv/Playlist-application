package webprogramming.playlistapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import webprogramming.playlistapp.entities.Invoice;
import webprogramming.playlistapp.repositories.InvoiceRepository;
import webprogramming.playlistapp.repositories.SubscriptionRepository;
import webprogramming.playlistapp.repositories.UserRepository;

import java.util.List;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService{


    private final InvoiceRepository invoiceRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public InvoiceServiceImpl(@Qualifier("invoiceRepository") InvoiceRepository invoiceRepository,
                              @Qualifier("subscriptionRepository") SubscriptionRepository subscriptionRepository,
                              @Qualifier("userRepository") UserRepository userRepository){
        this.invoiceRepository = invoiceRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice findBySubscriptionId(int subId) {
        return invoiceRepository.findBySubscriptionId(subId);
    }

    @Override
    public Invoice createInvoice(int subId) {
        Invoice invoice = new Invoice();
        if(subscriptionRepository.findSubscriptionById(subId) != null) {
            invoice.setSubscription(subscriptionRepository.findSubscriptionById(subId));
        }
        invoice.setUser(subscriptionRepository.findSubscriptionById(subId).getUser());
        invoiceRepository.save(invoice);
        return invoice;
    }
}

