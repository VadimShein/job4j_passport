package ru.job4j.passport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.passport.entity.Operation;
import ru.job4j.passport.entity.Passport;
import ru.job4j.passport.service.PassportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passport")
public class PassportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PassportController.class.getSimpleName());
    private final PassportService passportService;
    private final ObjectMapper objectMapper;

    public PassportController(PassportService passportService, ObjectMapper objectMapper) {
        this.passportService = passportService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/save")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Passport> save(@Valid @RequestBody Passport passport) {
        return new ResponseEntity<>(passportService.save(passport), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Passport> update(@Valid @RequestParam int id, @RequestBody Passport passport) {
            passport.setId(id);
            return new ResponseEntity<>(passportService.update(passport), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@Valid @RequestParam int id) {
            Passport passport = new Passport();
            passport.setId(id);
            this.passportService.delete(passport);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findAll() {
        List<Passport> passports = passportService.findAll();
        return new ResponseEntity<>(
                passports, passports.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }

    @GetMapping(value = "/find", params = "series")
    public List<Passport> findBySeries(@Valid @RequestParam int series) {
        return passportService.findBySeries(series);
    }

    @GetMapping(value = "/find", params = "number")
    public List<Passport> findByNumber(@Valid @RequestParam int number) {
        return passportService.findByNumber(number);
    }

    @GetMapping(value = "/find", params = {"series", "number"})
    public ResponseEntity<Passport> findBySeriesAndNumber(@Valid @RequestParam int series, int number) {
       Optional<Passport> dbPassport = passportService.findBySeriesAndNumber(series, number);
        return new ResponseEntity<>(
                dbPassport.orElse(new Passport()),
                dbPassport.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
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
    public List<Passport> unavailable() {
        return  passportService.findUnavailable();
    }

    @GetMapping("/replaceable")
    public List<Passport> replaceable() {
        return passportService.findReplaceable();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
