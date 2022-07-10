package ru.job4j.passport.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.passport.entity.Passport;

import java.util.Date;
import java.util.Optional;

public interface PassportRepository extends CrudRepository<Passport, Long> {
    Optional<Passport> findById(int id);
    Optional<Passport> findBySeries(int series);
    Iterable<Passport> findByDueDateIsBetween(Date start, Date finish);
    Iterable<Passport> findByDueDateIsBefore(Date date);
}
