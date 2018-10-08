package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMap implements MealDao {

    private static Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private static AtomicInteger countId = new AtomicInteger(0);

    public MealDaoMap() {
        if (mealsMap.size() == 0) {
            populateMap();
        }
    }

    private static void populateMap() {
        mealsMap.put(countId.getAndIncrement(), (new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)));
        mealsMap.put(countId.getAndIncrement(), (new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000)));
        mealsMap.put(countId.getAndIncrement(), (new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500)));
        mealsMap.put(countId.getAndIncrement(), (new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000)));
        mealsMap.put(countId.getAndIncrement(), (new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500)));
        mealsMap.put(countId.getAndIncrement(), (new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)));
    }

    @Override
    public void add(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(countId.getAndIncrement(), dateTime, description, calories);
        mealsMap.put(meal.getId(), meal);
    }

    @Override
    public void update(int id, LocalDateTime dateTime, String description, int calories) {
        delete(id);
        mealsMap.put(id, new Meal(id, dateTime, description, calories));
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
    public List<MealWithExceed> getAll(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<Meal> mealList = new ArrayList<>(mealsMap.values());
        mealList.sort(Comparator.comparing(Meal::getDateTime));
        return MealsUtil.getFilteredWithExceeded(mealList, startTime, endTime, caloriesPerDay);
    }
}
