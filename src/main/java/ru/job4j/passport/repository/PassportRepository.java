package ru.job4j.passport.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.passport.entity.Passport;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PassportRepository extends CrudRepository<Passport, Long> {
    List<Passport> findAll();
    Optional<Passport> findById(int id);
    Optional<Passport> findBySeriesAndNumber(int series, int number);
    List<Passport> findBySeries(int series);
    List<Passport> findByNumber(int number);
    List<Passport> findByDueDateIsBetween(Date start, Date finish);
    List<Passport> findByDueDateIsBefore(Date date);

}
