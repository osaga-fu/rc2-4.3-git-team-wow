package net.toyland.store.controllers;

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
