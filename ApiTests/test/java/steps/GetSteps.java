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
        response = BaseSimpleGet.getResponse(request);
    }

    @Тогда("проверяем получение {int} {string} как html")
    public void checkAnswerHtmlType(Integer count, String type) {
        BaseSimpleGet.checkStatus200(response);
        String body = BaseSimpleGet.getBodyFromResponse(response);
        log.info("Проверка на совпадение");
        Assert.assertEquals("Не получено нужное кол-во " + type,count, BaseSimpleGet.findCountInHTML(body, type));
        Assert.assertTrue("На выходе не получен HTML документ",BaseSimpleGet.isHtml(body));
    }

    @Тогда("проверяем получение {int} {string} как json")
    public void checkAnswerJsonType(Integer count, String type) {
        BaseSimpleGet.checkStatus200(response);
        log.info("Проверка на статус success");
        Assert.assertEquals("Не получен нужный статус","success", BaseSimpleGet.getContentOf(response,"status"));

        String body = (String) BaseSimpleGet.getContentOf(response,"text");

        log.info("Проверка на совпадение");
        Assert.assertEquals("Не получено нужное кол-во " + type,count, BaseSimpleGet.findCountInJSON(body, type));
    }

    @Тогда("получаем {string} в ответе")
    public void checkErrorHtml(String error) {
        String body = BaseSimpleGet.getBodyFromResponse(response);
        Assert.assertTrue("Не получен нужный статус",body.contains(error));
    }

    @Тогда("получаем {string} в статусе c {int}")
    public void checkErrorJson(String error, Integer code) {
        Assert.assertEquals("Не получен нужный статус","error", BaseSimpleGet.getContentOf(response,"status"));
        Assert.assertEquals("Не получен нужный текст",error, BaseSimpleGet.getContentOf(response,"text"));
        Assert.assertEquals("Не получен нужный код ошибки",code,BaseSimpleGet.getContentOf(response,"errorCode"));
    }
}
