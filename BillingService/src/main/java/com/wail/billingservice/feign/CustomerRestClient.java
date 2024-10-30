package com.wail.billingservice.feign;

import com.wail.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMERSERVICE")
public interface CustomerRestClient {
    @GetMapping(path = "/customers/{id}")
    Customer getCustomerById(@PathVariable Long id);
}
