package steps;

import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;


public class GetSteps {
    Response response;
    Logger log = LogManager.getLogger();

    @Когда("отправлен запрос на {int} {string} как {string}")
    public void sendRequest(Integer count, String type, String format) {
        log.info("Посылаем запрос");
        String request = String.format(BaseSimpleGet.REQUEST_FILTER, format, type, count);
        response = BaseSimpleGet.getResponce(request);
    }

    @Тогда("проверяем получение {int} {string} как html")
    public void checkAnswerHtmlType(Integer count, String type) {
        BaseSimpleGet.checkStatus200(response);
        String body = BaseSimpleGet.getTextFromResponce(response);
        log.info("Проверка на совпадение");
        Assert.assertEquals(count, BaseSimpleGet.findCountInHTML(body, type));
        Assert.assertTrue(body.startsWith("<html>"));
    }

    @Тогда("проверяем получение {int} {string} как json")
    public void checkAnswerJsonType(Integer count, String type) {
        BaseSimpleGet.checkStatus200(response);
        log.info("Проверка на статус success");
        Assert.assertEquals("success", BaseSimpleGet.getContentOf(response,"status"));

        String body = (String) BaseSimpleGet.getContentOf(response,"text");

        log.info("Проверка на совпадение");
        Assert.assertEquals(count, BaseSimpleGet.findCountInJSON(body, type));
    }

    @Тогда("получаем {string} в ответе")
    public void checkErrorHtml(String error) {
        String body = BaseSimpleGet.getTextFromResponce(response);
        Assert.assertTrue(body.contains(error));
    }

    @Тогда("получаем {string} в статусе c {int}")
    public void checkErrorJson(String error, Integer code) {
        Assert.assertEquals("error", BaseSimpleGet.getContentOf(response,"status"));
        Assert.assertEquals(error, BaseSimpleGet.getContentOf(response,"text"));
        Assert.assertEquals(code,BaseSimpleGet.getContentOf(response,"errorCode"));
    }
}
