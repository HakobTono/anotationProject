package validate;

import annotations.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class CustomerDtoValidate {
    private static final String REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-tttt9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    public static void checkCustomerDto(Object obj) throws IllegalAccessException {
        checkAdulthood(obj);
        checkLength(obj);
        checkMinMax(obj);
        checkEmail(obj);

    }

    private static boolean isAdult(LocalDate date) {
        if (LocalDate.now().getYear() - date.getYear() == 18) {
            if (LocalDate.now().getMonthValue() - date.getMonthValue() >= 0) {
                if (LocalDate.now().getDayOfMonth() - date.getDayOfMonth() >= 0) {
                    return true;
                }
            }
        } else if (LocalDate.now().getYear() - date.getYear() > 18) {
            return true;
        }
        return false;
    }



    private static void checkAdulthood(Object obj) throws IllegalAccessException {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(Adulthood.class)) {
                LocalDate localDate = (LocalDate) declaredField.get(obj);
                if (!isAdult(localDate)) {
                    System.out.println("You are not under the age 18.");
                }
            }
        }
    }



    private static void checkLength(Object obj) throws IllegalAccessException {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(Length.class)) {
                Length annotation = declaredField.getAnnotation(Length.class);
                String name = (String) declaredField.get(obj);
                if (name.length() < annotation.min() || name.length() > annotation.max()) {
                    System.out.println("Check the name length(you must use " + annotation.min() + "-" + annotation.max() + "characters)");
                }
            }
        }
    }



    private static void checkMinMax(Object obj) throws IllegalAccessException {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(Min.class) && declaredField.isAnnotationPresent(Max.class)) {
                int number = (int) declaredField.get(obj);
                if (number < declaredField.getAnnotation(Min.class).value()) {
                    System.out.println("Your number is lower than the minimum discount rate");
                }
                if (number > declaredField.getAnnotation(Max.class).value()) {
                    System.out.println("Your number exceed the maximum discount rate");
                }
            }
        }

    }



    private static void checkEmail(Object obj) throws IllegalAccessException {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(Email.class)) {
                String email = (String) declaredField.get(obj);
                Pattern compile = Pattern.compile(REGEX);
                if (!compile.matcher(email).matches()) {
                    System.out.println("Wrong Email");
                }
            }
        }
    }
}