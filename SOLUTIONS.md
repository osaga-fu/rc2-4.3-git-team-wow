Este archivo contiene soluciones al ejercicio. Intentad encontrar soluciones para cada uno de los tests antes de recurrir a estas soluciones.


### Toy Controller:

```
package net.toyland.store.controllers.toys;

import org.springframework.web.bind.annotation.RestController;

import net.toyland.store.persistence.toys.Toy;
import net.toyland.store.persistence.toys.ToyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/toys")
public class ToyController {

    private final ToyRepository toyRepository;

    public ToyController(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    @GetMapping
    public ResponseEntity<List<ToyResponse>> getAllToys() {
        List<Toy> toys = toyRepository.findAll();
        List<ToyResponse> toyResponses = toys.stream()
                .map(this::mapToToyResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(toyResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToyResponse> getToyById(@PathVariable Long id) {
        Optional<Toy> toyOptional = toyRepository.findById(id);
        if (toyOptional.isPresent()) {
            Toy toy = toyOptional.get();
            ToyResponse toyResponse = mapToToyResponse(toy);
            return ResponseEntity.ok(toyResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ToyResponse> createToy(@RequestBody ToyRequest toyRequest) {
        Toy toy = new Toy(toyRequest.getName(), toyRequest.getBrand(), toyRequest.getPrice());
        toyRepository.save(toy);
        ToyResponse toyResponse = mapToToyResponse(toy);
        return ResponseEntity.status(HttpStatus.CREATED).body(toyResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToyResponse> updateToy(@PathVariable Long id, @RequestBody ToyRequest toyRequest) {
        Optional<Toy> toyOptional = toyRepository.findById(id);
        if (toyOptional.isPresent()) {
            Toy toy = toyOptional.get();
            toy.setName(toyRequest.getName());
            toy.setBrand(toyRequest.getBrand());
            toy.setPrice(toyRequest.getPrice());
            toyRepository.save(toy);
            ToyResponse toyResponse = mapToToyResponse(toy);
            return ResponseEntity.ok(toyResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToy(@PathVariable Long id) {
        Optional<Toy> toyOptional = toyRepository.findById(id);
        if (toyOptional.isPresent()) {
            toyRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ToyResponse mapToToyResponse(Toy toy) {
        return new ToyResponse(toy.getId(), toy.getName(), toy.getBrand(), toy.getPrice());
    }

}
```

### CustomerController:

```
package net.toyland.store.controllers.customers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.toyland.store.persistence.customers.Customer;
import net.toyland.store.persistence.customers.CustomerRepository;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponse> customerResponses = customers.stream()
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            CustomerResponse customerResponse = mapToCustomerResponse(customer);
            return ResponseEntity.ok(customerResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = new Customer(customerRequest.getFirstName(), customerRequest.getLastName(),
                customerRequest.getPhoneNumber(), customerRequest.getAddress());
        customerRepository.save(customer);
        CustomerResponse customerResponse = mapToCustomerResponse(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setFirstName(customerRequest.getFirstName());
            customer.setLastName(customerRequest.getLastName());
            customer.setPhoneNumber(customerRequest.getPhoneNumber());
            customer.setAddress(customerRequest.getAddress());
            customerRepository.save(customer);
            CustomerResponse customerResponse = mapToCustomerResponse(customer);
            return ResponseEntity.ok(customerResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CustomerResponse mapToCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFirstName(),
                customer.getLastName(), customer.getPhoneNumber(), customer.getAddress());
    }
}
```