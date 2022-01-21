package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.ExactText;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }
        @Test
        public void shouldSendFullForm() {
//            установка первой даты
            CardInfoForRegistration infoForReg = DataGenerator.Registration.generateInfo("ru");
            $("[data-test-id='city'] input").setValue(infoForReg.getCity());
            String dateOfMeeting = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
            $("[data-test-id='date'] input").setValue(dateOfMeeting);
            $("[data-test-id='name'] input").setValue(infoForReg.getName());
            $(byName("phone")).setValue(infoForReg.getPhone()); // поиск по имени
            $("[data-test-id='agreement']").click();
            $(withText("Запланировать")).click();
            $("[data-test-id='success-notification']").shouldHave(text("Успешно!"),Duration.ofSeconds(15));
            $(".notification__content").shouldBe(exactText("Встреча успешно запланирована на "
                    + dateOfMeeting), Duration.ofSeconds(15));
            $("[data-test-id='success-notification']").shouldBe(visible).find(".icon-button__text").click();
//             установка новой даты
            String newDateOfMeeting = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
            $("[data-test-id='date'] input").setValue(newDateOfMeeting);
            $(withText("Запланировать")).shouldBe(visible).click();
//             новое всплывающее сообщение
            $("[data-test-id='replan-notification']").shouldBe(visible).find(".button__text").click();
            $("[data-test-id='success-notification']").shouldHave(text("Успешно!"),Duration.ofSeconds(15));
            $(".notification__content").shouldBe(exactText("Встреча успешно запланирована на "
                    + newDateOfMeeting), Duration.ofSeconds(15));

        }

}
