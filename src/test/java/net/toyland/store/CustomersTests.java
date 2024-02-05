package net.toyland.store;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import net.toyland.store.persistence.customers.Customer;
import net.toyland.store.persistence.customers.CustomerRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CustomersTests {
    
    @Autowired
    private MockMvc api;
    private CustomerRepository customerRepository;

    public CustomersTests(@Autowired CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
    }

    @Test
    public void returnsTheExistingCustomers() throws Exception {
        customerRepository.saveAll(List.of(
                new Customer("John", "Doe", "123-456-7890", "123 Main St"),
                new Customer("Alice", "Smith", "987-654-3210", "456 Elm St")));

        api.perform(get("/api/v1/customers"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(2)),
                        jsonPath("$[0].firstName", equalTo("John")),
                        jsonPath("$[0].lastName", equalTo("Doe")),
                        jsonPath("$[0].phoneNumber", equalTo("123-456-7890")),
                        jsonPath("$[0].address", equalTo("123 Main St")),
                        jsonPath("$[1].firstName", equalTo("Alice")),
                        jsonPath("$[1].lastName", equalTo("Smith")),
                        jsonPath("$[1].phoneNumber", equalTo("987-654-3210")),
                        jsonPath("$[1].address", equalTo("456 Elm St")));
    }

    @Test
    public void createsCustomer() throws Exception {
        String requestBody = "{\"firstName\":\"Jane\",\"lastName\":\"Johnson\",\"phoneNumber\":\"555-123-4567\",\"address\":\"789 Oak St\"}";

        api.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.firstName", equalTo("Jane")),
                        jsonPath("$.lastName", equalTo("Johnson")),
                        jsonPath("$.phoneNumber", equalTo("555-123-4567")),
                        jsonPath("$.address", equalTo("789 Oak St")));
    }

    @Test
    public void editsCustomerById() throws Exception {
        Customer savedCustomer = customerRepository
                .save(new Customer("Mary", "Williams", "777-888-9999", "111 Pine St"));
        String requestBody = "{\"firstName\":\"Updated Mary\",\"lastName\":\"Williams\",\"phoneNumber\":\"777-888-9999\",\"address\":\"222 Pine St\"}";

        api.perform(put("/api/v1/customers/" + savedCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.firstName", equalTo("Updated Mary")),
                        jsonPath("$.lastName", equalTo("Williams")),
                        jsonPath("$.phoneNumber", equalTo("777-888-9999")),
                        jsonPath("$.address", equalTo("222 Pine St")));
    }

    @Test
    public void deletesCustomerById() throws Exception {
        Customer savedCustomer = customerRepository
                .save(new Customer("Tom", "Johnson", "888-777-6666", "333 Cedar St"));

        api.perform(delete("/api/v1/customers/" + savedCustomer.getId()))
                .andExpect(status().isNoContent());
    }
}
