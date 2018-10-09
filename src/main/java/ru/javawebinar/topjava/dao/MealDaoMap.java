package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MealDaoMap implements MealDao {

    private Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();

    public MealDaoMap() {
        if (mealsMap.size() == 0) {
            populateMap();
        }
    }

    private void populateMap() {
        mealsMap.put(0, (new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)));
        mealsMap.put(1, (new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000)));
        mealsMap.put(2, (new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500)));
        mealsMap.put(3, (new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000)));
        mealsMap.put(4, (new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500)));
        mealsMap.put(5, (new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)));
    }

    @Override
    public void add(Meal meal) {
        mealsMap.put(meal.getId(), meal);
    }

    @Override
    public Meal update(Meal meal) {
        mealsMap.replace(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        mealsMap.remove(id);
    }

    @Override
    public Meal getById(int id) {
        return mealsMap.get(id);
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> mealList = new ArrayList<>(mealsMap.values());
        mealList.sort(Comparator.comparing(Meal::getDateTime));
        return mealList;
    }
}
