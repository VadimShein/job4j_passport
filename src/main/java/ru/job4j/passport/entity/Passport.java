package ru.job4j.passport.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

import java.util.Objects;

@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null", groups = {Operation.OnUpdate.class, Operation.OnDelete.class})
    private int id;
    @Min(value = 1000, message = "Series must contain at least four characters")
    private int series;
    @NotBlank(message = "Name must be not empty")
    private String name;
    @NotBlank(message = "Surname must be not empty")
    private String surname;
    @NotBlank(message = "Patronymic must be not empty")
    private String patronymic;
    @NotNull(message = "Date of issue must be non null")
    @Column(name = "date_of_issue")
    private Date dateOfIssue;
    @NotNull(message = "Due date of issue must be non null")
    @Column(name = "due_date")
    private Date dueDate;

    public Passport() { }

    public Passport(int id, int series, String name, String surname, String patronymic, Date dateOfIssue, Date dueDate) {
        this.id = id;
        this.series = series;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.dateOfIssue = dateOfIssue;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return id == passport.id
                && series == passport.series
                && name.equals(passport.name)
                && surname.equals(passport.surname)
                && patronymic.equals(passport.patronymic)
                && dateOfIssue.equals(passport.dateOfIssue)
                && Objects.equals(dueDate, passport.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
