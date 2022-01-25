package ru.netology;


import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {



    @BeforeEach
    void setUpAll() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }
        @Test
        public void shouldSendFullForm() {
//            установка первой даты
            CardInfoForRegistration infoForReg = DataGenerator.Registration.generateInfo("ru");
            $("[data-test-id='city'] input").setValue(infoForReg.getCity());
            $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
            $("[data-test-id='date'] input").setValue(DataGenerator.date(5));
//           есть еще вариант поместить дату в переменную например
//            String dateOfMeeting = DataGenerator.date(5);
//            а потом подставлять ее там где нам необходимо, так же со второй датой
            $("[data-test-id='name'] input").setValue(infoForReg.getName());
            $(byName("phone")).setValue(infoForReg.getPhone()); // поиск по имени
            $("[data-test-id='agreement']").click();
            $(withText("Запланировать")).click();
            $("[data-test-id='success-notification']").shouldHave(text("Успешно!"),Duration.ofSeconds(15));
            $(".notification__content").shouldBe(exactText("Встреча успешно запланирована на "
                    + DataGenerator.date(5)), Duration.ofSeconds(15));
            $("[data-test-id='success-notification']").shouldBe(visible).find(".icon-button__text").click();
//             установка новой даты
            $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
            $("[data-test-id='date'] input").setValue(DataGenerator.date(10));
            $(withText("Запланировать")).shouldBe(visible).click();
//             новое всплывающее сообщение
            $("[data-test-id='replan-notification']").shouldBe(visible).find(".button__text").click();
            $("[data-test-id='success-notification']").shouldHave(text("Успешно!"),Duration.ofSeconds(15));
            $(".notification__content").shouldBe(exactText("Встреча успешно запланирована на "
                    + DataGenerator.date(10)), Duration.ofSeconds(15));

        }

}
