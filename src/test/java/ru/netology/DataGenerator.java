package ru.netology;

import com.github.javafaker.Faker;

import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class DataGenerator {


    @UtilityClass
    public static class Registration {
        public static CardInfoForRegistration generateInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new CardInfoForRegistration(faker.address().cityName(),
                    faker.name().lastName() + " " + faker.name().firstName(),
                    faker.numerify("+79#########"));
        }

    }
}
