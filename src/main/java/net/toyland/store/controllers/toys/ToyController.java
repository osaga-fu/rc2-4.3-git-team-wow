package net.toyland.store.controllers.toys;

import org.springframework.web.bind.annotation.RestController;

import net.toyland.store.persistence.toys.Toy;
import net.toyland.store.persistence.toys.ToyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/toys")
public class ToyController {

    private ToyRepository repository;

    public ToyController(@Autowired ToyRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ToyResponse createToy(@RequestBody ToyRequest request) {
        Toy toy = new Toy(request.getName(), request.getBrand(), request.getPrice());
        Toy savedToy = repository.save(toy);
        return new ToyResponse(savedToy.getId(), savedToy.getName(), savedToy.getBrand(), savedToy.getPrice());
    }
}
