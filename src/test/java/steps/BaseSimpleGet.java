package steps;

import io.restassured.response.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class BaseSimpleGet {
    public final static String GET_REQUEST = "https://fish-text.ru/get?format=%s&type=%s&number=%d";

    public static Response getResponce(String request){
        return given().get(request);
    }

    public static void checkStatus200(Response response){
        response.then().assertThat().statusCode(200);
    }

    public static String getTextFromResponce(Response response){
        return response.getBody().prettyPrint();
    }
    public static int findCountInJSON(String text, String type) {
        if (type.equals("sentence")) {
            Pattern pattern = Pattern.compile("([^.!?]+[.!?])");
            Matcher matcher = pattern.matcher(text);
            int count = 0;
            while (matcher.find()) {
                count++;
            }
            return count;
        }
        return text.split("\\\\n\\\\n").length;
    }

    public static int findCountInHTML(String text, String type) {

        String patternString = getPattern(type);
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
            return "<p>(?!<h1>|</h1>).+</p>";
        } else if (type.equals("sentence")) {
            return "([^.!?]+[.!?])";
        } else return "<h1>(?!<h1>|</h1>).+</h1>";
    }
}
