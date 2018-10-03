package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        mealList.sort(Comparator.comparingInt(o -> o.getDateTime().getDayOfYear()));

        List<UserMeal> mealsOfDayList = new ArrayList<>();
        List<UserMealWithExceed> resultList = new ArrayList<>();

        int dayOfYear = mealList.get(0).getDateTime().getDayOfYear();
        for (UserMeal userMeal : mealList) {
            if (userMeal.getDateTime().getDayOfYear() == dayOfYear) {
                mealsOfDayList.add(userMeal);
            } else {
                checkMealsOfPreviousDay(mealsOfDayList, caloriesPerDay, resultList, startTime, endTime);
                dayOfYear = userMeal.getDateTime().getDayOfYear();
                mealsOfDayList.add(userMeal);
            }

        }
        checkMealsOfPreviousDay(mealsOfDayList, caloriesPerDay, resultList, startTime, endTime);

        return resultList;
    }

    private static void checkMealsOfPreviousDay(List<UserMeal> mealsOfDayList, int caloriesPerDay, List<UserMealWithExceed> resultList, LocalTime startTime, LocalTime endTime) {
        if (isMealsOfDayExceeded(mealsOfDayList, caloriesPerDay)) {
            addMealsOfDayToResultList(mealsOfDayList, resultList, startTime, endTime);
        }
        mealsOfDayList.clear();
    }

    private static boolean isMealsOfDayExceeded(List<UserMeal>mealsOfDayList, int caloriesPerDay) {
        int caloriesOfDay = 0;
        for (UserMeal meal : mealsOfDayList) {
            caloriesOfDay += meal.getCalories();
        }
        return caloriesOfDay > caloriesPerDay;
    }

    private static void addMealsOfDayToResultList(List<UserMeal> mealsOfDayList, List<UserMealWithExceed> resultList, LocalTime startTime, LocalTime endTime) {
        for (UserMeal meal : mealsOfDayList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExceed(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        true));
            }
        }
    }
}
