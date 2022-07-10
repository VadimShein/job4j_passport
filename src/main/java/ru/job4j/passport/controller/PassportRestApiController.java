package ru.job4j.passport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.passport.entity.Operation;
import ru.job4j.passport.entity.Passport;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/")
public class PassportRestApiController {
    private static final String SAVE_API = "http://localhost:8080/passport/save";
    private static final String UPDATE_API = "http://localhost:8080/passport/update?id=";
    private static final String DELETE_API = "http://localhost:8080/passport/delete?id=";
    private static final String FIND_API = "http://localhost:8080/passport/find";
    private static final String UNAVAILABLE_API = "http://localhost:8080/passport/unavailable";
    private static final String REPLACEABLE_API = "http://localhost:8080/passport/replaceable";

    @Autowired
    private RestTemplate rest;

    @PostMapping("/save")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Passport> save(@Valid @RequestBody Passport passport) {
        HttpEntity<Passport> entity = new HttpEntity<>(passport);
        Passport rsl = rest.postForObject(SAVE_API, entity, Passport.class);
        return new ResponseEntity<>(rsl, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Passport> update(@Valid @RequestParam int id, @RequestBody Passport passport) {
        passport.setId(id);
        HttpEntity<Passport> entity = new HttpEntity<>(passport);
        rest.put(UPDATE_API + id, entity);
        return new ResponseEntity<>(passport, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@Valid @RequestParam int id) {
        rest.delete(DELETE_API + id, id, Passport.class);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findAll() {
        List<Passport> passports = rest.exchange(
                FIND_API, HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }
        ).getBody();
        return new ResponseEntity<>(
                passports, passports == null ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }

    @GetMapping(value = "/find", params = "series")
    public ResponseEntity<Passport> findBySeries(@Valid @RequestParam String series) {
        return rest.exchange(FIND_API + "?series=" + series, HttpMethod.GET, null, Passport.class);
    }

    @GetMapping(value = "/find", params = "id")
    public ResponseEntity<Passport> findById(@Valid @RequestParam String id) {
        return rest.exchange(FIND_API + "?id=" + id, HttpMethod.GET, null, Passport.class);
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> getUnavailable() {
        List<Passport> passports = rest.exchange(
                UNAVAILABLE_API, HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }
        ).getBody();
        return new ResponseEntity<>(
                passports, passports == null ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }

    @GetMapping("replaceable")
    public ResponseEntity<List<Passport>> getReplaceable() {
        List<Passport> passports = rest.exchange(
                REPLACEABLE_API, HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }
        ).getBody();
        return new ResponseEntity<>(
                passports, passports == null ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }
}
