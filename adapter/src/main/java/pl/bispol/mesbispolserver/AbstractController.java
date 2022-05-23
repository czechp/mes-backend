package pl.bispol.mesbispolserver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public interface AbstractController<C, D> {
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<D> findBy(@RequestParam(name = "limit") Optional<Integer> limit
    );

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    D findById(@PathVariable(name = "id") final long id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable(name = "id") final long id);
}
