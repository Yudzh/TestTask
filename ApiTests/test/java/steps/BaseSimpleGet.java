package steps;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class BaseSimpleGet {
    final static String SENTENCE_PATTERN = "([^.!?]+[.!?])";
    final static String TITLE_PATTERN = "<h1>(?!<h1>|</h1>).+</h1>";
    final static String PARAGRAPH_PATTERN = "<p>(?!<h1>|</h1>).+</p>";

    public final static String REQUEST_FILTER = "https://fish-text.ru/get?format=%s&type=%s&number=%d";
    static Logger logger = LogManager.getLogger();

    public static Response getResponse(String request) {
        return given().get(request);
    }

    public static void checkStatus200(Response response) {
        logger.info("Получаем ответ и проверяем на статус 200");
        response.then().assertThat().statusCode(200);
    }

    public static String getBodyFromResponse(Response response) {
        return response.getBody().prettyPrint();
    }

    public static Integer findCountInJSON(String text, String type) {
        System.out.println(text);
        logger.info("Находим кол-во совпадений в JSON");
        if (type.equals("sentence")) {
            return findCount(SENTENCE_PATTERN, text);
        }
        /**
         * Здесь я использовал сплит, так как это решение мне показалось легче
         */
        else return text.split("\\\\n\\\\n").length;
    }

    public static Integer findCountInHTML(String text, String type) {
        logger.info("Находим кол-во совпадений в HTML");
        return findCount(getPattern(type), text);
    }

    private static Integer findCount(String patternString, String text) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private static String getPattern(String type) {
        if (type.equals("paragraph")) {
            return PARAGRAPH_PATTERN;
        } else if (type.equals("sentence")) {
            return SENTENCE_PATTERN;
        } else return TITLE_PATTERN;
    }

    public static Object getContentOf(Response response, String tag) {
        return response.getBody().jsonPath().get(tag);
    }

    public static boolean isHtml(String body){
        return body.startsWith("<html>");
    }
}
