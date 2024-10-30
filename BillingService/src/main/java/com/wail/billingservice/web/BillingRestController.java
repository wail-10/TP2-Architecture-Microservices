package com.wail.billingservice.web;

import com.wail.billingservice.entities.Bill;
import com.wail.billingservice.feign.CustomerRestClient;
import com.wail.billingservice.feign.ProductRestClient;
import com.wail.billingservice.model.Customer;
import com.wail.billingservice.model.Product;
import com.wail.billingservice.repositories.BillRepository;
import com.wail.billingservice.repositories.ProductItemRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBillById(@PathVariable(name = "id") Long id) {
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi -> {
            Product product = productRestClient.getProductById(pi.getProductID());
            pi.setProduct(product);
        });
        return bill;
    }
}
