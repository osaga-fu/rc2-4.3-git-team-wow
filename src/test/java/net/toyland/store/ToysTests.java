package net.toyland.store;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import net.toyland.store.persistence.toys.Toy;
import net.toyland.store.persistence.toys.ToyRepository;

import java.math.BigDecimal;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class ToysTests {

    @Autowired
    private MockMvc api;
    private ToyRepository toyRepository;

    public ToysTests(@Autowired ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    @BeforeEach
    public void setup() {
        toyRepository.deleteAll();
    }

    @Test
    public void returnsTheExistingToys() throws Exception {
        toyRepository.saveAll(List.of(
                new Toy("Barbie", "Mattel", BigDecimal.valueOf(26.99)),
                new Toy("Barco Pirata", "Playmobil", BigDecimal.valueOf(40.00))));

        api.perform(get("/api/v1/toys"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(2)),
                        jsonPath("$[0].name", equalTo("Barbie")),
                        jsonPath("$[0].brand", equalTo("Mattel")),
                        jsonPath("$[0].price", equalTo(26.99)),
                        jsonPath("$[1].name", equalTo("Barco Pirata")),
                        jsonPath("$[1].brand", equalTo("Playmobil")),
                        jsonPath("$[1].price", equalTo(40.00)));
    }

    @Test
    public void returnsToyById() throws Exception {
        Toy savedToy = toyRepository.save(new Toy("RC Car", "Maisto", BigDecimal.valueOf(29.99)));

        api.perform(get("/api/v1/toys/" + savedToy.getId()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name", equalTo("RC Car")),
                        jsonPath("$.brand", equalTo("Maisto")),
                        jsonPath("$.price", equalTo(29.99)));
    }

    @Test
    public void createsToy() throws Exception {
        String requestBody = "{\"name\":\"LEGO Set\",\"brand\":\"LEGO\",\"price\":49.99}";

        api.perform(post("/api/v1/toys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.name", equalTo("LEGO Set")),
                        jsonPath("$.brand", equalTo("LEGO")),
                        jsonPath("$.price", equalTo(49.99)));
    }

    @Test
    public void editsToyById() throws Exception {
        Toy savedToy = toyRepository.save(new Toy("Dollhouse", "KidKraft", BigDecimal.valueOf(79.99)));
        String requestBody = "{\"name\":\"Updated Dollhouse\",\"brand\":\"KidKraft\",\"price\":89.99}";

        api.perform(put("/api/v1/toys/" + savedToy.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name", equalTo("Updated Dollhouse")),
                        jsonPath("$.brand", equalTo("KidKraft")),
                        jsonPath("$.price", equalTo(89.99)));
    }

    @Test
    public void deletesToyById() throws Exception {
        Toy savedToy = toyRepository.save(new Toy("Board Game", "Hasbro", BigDecimal.valueOf(19.99)));

        api.perform(delete("/api/v1/toys/" + savedToy.getId()))
                .andExpect(status().isNoContent());
    }
}
