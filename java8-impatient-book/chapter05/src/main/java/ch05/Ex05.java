package ch05;

import java.time.LocalDate;
import java.time.Month;

public class Ex05 {
    //Write a program that prints how many days you have been alive.

    public static void main(String[] args) {
        LocalDate birthDate = LocalDate.of(1987, Month.JULY, 23);
        System.out.print(birthDate.plusDays(12000));
    }
}
