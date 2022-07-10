package ru.job4j.passport.service;

import org.springframework.stereotype.Service;
import ru.job4j.passport.entity.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.*;

@Service
public class PassportService {
    private final PassportRepository passportRepository;

    public PassportService(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    public Passport save(Passport passport) {
        Optional<Passport> dbPassport = passportRepository.findBySeriesAndNumber(
                passport.getSeries(), passport.getNumber());
        if (dbPassport.isPresent()) {
            throw new IllegalArgumentException("Passport is already added");
        }
        return passportRepository.save(passport);
    }

    public void delete(Passport passport) {
        Optional<Passport> dbPassport = passportRepository.findById(passport.getId());
        if (dbPassport.isEmpty()) {
            throw new NoSuchElementException("Passport not found");
        }
        passportRepository.delete(passport);
    }

    public Passport update(Passport passport) {
        Optional<Passport> dbPassport = passportRepository.findById(passport.getId());
        if (dbPassport.isEmpty()) {
            throw new NoSuchElementException("Passport not found");
        }
        return passportRepository.save(passport);
    }

    public List<Passport> findAll() {
        return passportRepository.findAll();
    }

    public Optional<Passport> findById(int id) {
        return passportRepository.findById(id);
    }

    public Optional<Passport> findBySeriesAndNumber(int series, int number) {
        return passportRepository.findBySeriesAndNumber(series, number);
    }

    public List<Passport> findBySeries(int series) {
        return passportRepository.findBySeries(series);
    }

    public List<Passport> findByNumber(int number) {
        return passportRepository.findByNumber(number);
    }

    public List<Passport> findUnavailable() {
        return passportRepository.findByDueDateIsBefore(new Date(System.currentTimeMillis()));
    }

    public List<Passport> findReplaceable() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        return passportRepository.findByDueDateIsBetween(new Date(System.currentTimeMillis()), cal.getTime());
    }
}
