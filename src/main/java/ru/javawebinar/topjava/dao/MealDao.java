package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;

public interface MealDao {
    void add(Meal meal);

    Meal update(Meal meal);

    void delete(int id);

    Meal getById(int id);

    List<Meal> getAll();
}