package ru.job4j.passport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.passport.entity.Operation;
import ru.job4j.passport.entity.Passport;
import ru.job4j.passport.service.PassportService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/passport")
public class PassportController {
    private final PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping("/save")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Passport> save(@Valid @RequestBody Passport passport) {
        return new ResponseEntity<>(passportService.save(passport), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Passport> update(@Valid @RequestParam int id, @RequestBody Passport passport) {
        if (passportService.findById(id).isPresent()) {
            passport.setId(id);
            return new ResponseEntity<>(passportService.save(passport), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@Valid @RequestParam int id) {
        if (passportService.findById(id).isPresent()) {
            Passport passport = new Passport();
            passport.setId(id);
            this.passportService.delete(passport);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findAll() {
        List<Passport> passports = StreamSupport.stream(
                this.passportService.findAll().spliterator(), false
        ).collect(Collectors.toList());
        return new ResponseEntity<>(
                passports, passports.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }

    @GetMapping(value = "/find", params = "series")
    public ResponseEntity<Passport> findBySeries(@Valid @RequestParam int series) {
        Optional<Passport> passport = passportService.findBySeries(series);
        return new ResponseEntity<>(
                passport.orElse(new Passport()),
                passport.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping(value = "/find", params = "id")
    public ResponseEntity<Passport> findById(@Valid @RequestParam int id) {
        Optional<Passport> passport = passportService.findById(id);
        return new ResponseEntity<>(
                passport.orElse(new Passport()),
                passport.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> unavailable() {
        List<Passport> passports = StreamSupport.stream(
                this.passportService.findUnavailable().spliterator(), false
        ).collect(Collectors.toList());
        return new ResponseEntity<>(
                passports, passports.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }

    @GetMapping("/replaceable")
    public ResponseEntity<List<Passport>> replaceable() {
        List<Passport> passports = StreamSupport.stream(
                this.passportService.findReplaceable().spliterator(), false
        ).collect(Collectors.toList());
        return new ResponseEntity<>(
                passports, passports.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }
}
