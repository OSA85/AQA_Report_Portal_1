package ru.netology;

import com.github.javafaker.Faker;

import io.qameta.allure.Step;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@UtilityClass
public class DataGenerator {


    @UtilityClass
    public static class Registration {
        @Step ("Получаем данные клиента для оформления карты")
        public static CardInfoForRegistration generateInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new CardInfoForRegistration(faker.address().cityName(),
                    faker.name().lastName() + " " + faker.name().firstName(),
                    faker.numerify("+79#########"));
        }
    }
    @Step ("Получаем локальную дату")
    public String date (int quantityOfDays) {
        return LocalDate.now().plusDays(quantityOfDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    }


}
