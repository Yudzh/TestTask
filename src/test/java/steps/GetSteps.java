package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;


public class GetSteps {
    Response response;
    Logger log = LogManager.getLogger();

    @Когда("отправлен запрос на {int} {string} как html")
    public void отправлен_запрос_на_как_html(Integer count, String type) {
        log.info("Посылаем запрос");
        String request = String.format(BaseSimpleGet.GET_REQUEST, "html", type, count);
        response = BaseSimpleGet.getResponce(request);
    }

    @Тогда("проверяем получение {int} {string} как html")
    public void проверяем_получение_как_html(Integer count, String type) {
        log.info("Получаем ответ и проверяем на статус 200");
        BaseSimpleGet.checkStatus200(response);
        String text = BaseSimpleGet.getTextFromResponce(response);
        log.info("Находим кол-во совпадений в HTML");
        Integer elementsCount = BaseSimpleGet.findCountInHTML(text, type);

        log.info("Проверка на совпадение");
        Assert.assertEquals(count, elementsCount);
        Assert.assertTrue(text.startsWith("<html>"));
    }

    @Когда("отправлен запрос на {int} {string} как json")
    public void отправлен_запрос_на_как_json(Integer count, String type) {
        log.info("Посылаем запрос");
        String request = String.format("https://fish-text.ru/get?format=%s&type=%s&number=%d", "json", type, count);
        response = BaseSimpleGet.getResponce(request);
    }

    @Тогда("проверяем получение {int} {string} как json")
    public void проверяем_получение_как_json(Integer count, String type) {
        log.info("Получаем ответ и проверяем на статус 200");
        BaseSimpleGet.checkStatus200(response);

        log.info("Проверка на статус success");
        Assert.assertEquals("success", response.getBody().jsonPath().get("status"));

        String text = response.getBody().jsonPath().get("text");
        log.info("Находим кол-во совпадений в JSON");
        Integer elementsCount = BaseSimpleGet.findCountInJSON(text, type);

        log.info("Проверка на совпадение");
        Assert.assertEquals(count, elementsCount);
    }

    @Когда("отправлено слишком большое {int} {string} в формате html")
    public void отправлено_слишком_большое(Integer count, String type) {
        log.info("Посылаем запрос");
        String request = String.format("https://fish-text.ru/get?format=%s&type=%s&number=%d", "html", type, count);
        response = BaseSimpleGet.getResponce(request);
    }

    @Тогда("получаем {string} в ответе")
    public void получаем_в_ответе(String error) {
        String text = BaseSimpleGet.getTextFromResponce(response);
        Assert.assertTrue(text.contains(error));
    }

    @Когда("отправлено слишком большое {int} {string} в формате json")
    public void отправлено_слишком_большое_в_формате_json(Integer count, String type) {
        String request = String.format("https://fish-text.ru/get?format=%s&type=%s&number=%d", "json", type, count);
        response = BaseSimpleGet.getResponce(request);
    }

    @Тогда("получаем {string} в статусе c {int}")
    public void получаем_в_статусе_c(String error,Integer code) {
        log.info("Проверка на статус success");
        Assert.assertEquals("error", response.getBody().jsonPath().get("status"));
        Assert.assertEquals(error, response.getBody().jsonPath().get("text"));
        Assert.assertEquals(code,response.getBody().jsonPath().get("errorCode"));
    }
}
