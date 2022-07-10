package ru.job4j.passport.service;

import org.springframework.stereotype.Service;
import ru.job4j.passport.entity.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class PassportService {
    private final PassportRepository passportRepository;

    public PassportService(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    public Passport save(Passport passport) {
        return passportRepository.save(passport);
    }

    public void delete(Passport passport) {
        passportRepository.delete(passport);
    }

    public Iterable<Passport> findAll() {
        return passportRepository.findAll();
    }

    public Optional<Passport> findById(int id) {
        return passportRepository.findById(id);
    }

    public Optional<Passport> findBySeries(int series) {
        return passportRepository.findBySeries(series);
    }

    public Iterable<Passport> findUnavailable() {
        return passportRepository.findByDueDateIsBefore(new Date(System.currentTimeMillis()));
    }

    public Iterable<Passport> findReplaceable() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        return passportRepository.findByDueDateIsBetween(new Date(System.currentTimeMillis()), cal.getTime());
    }
}
