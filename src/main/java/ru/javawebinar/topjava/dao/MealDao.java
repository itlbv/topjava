package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface MealDao {
    void add(LocalDateTime dateTime, String description, int calories);

    void update(int id, LocalDateTime dateTime, String description, int calories);

    void delete(int id);

    Meal getById(int id);

    List<MealWithExceed> getAll(LocalTime startTime, LocalTime endTime, int caloriesPerDay);
}