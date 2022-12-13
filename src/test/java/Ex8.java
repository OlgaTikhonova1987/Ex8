import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import java.lang.Thread;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class Ex8 {
    @Test
    public void Test8() {
        String location = "https://playground.learnqa.ru/ajax/api/longtime_job";
            JsonPath getToken = RestAssured
                    .get(location)
                    .jsonPath();
        int sleepSeconds = Integer.parseInt(getToken.get("seconds").toString())*1000 ;
        String token = getToken.get("token").toString();


        JsonPath responseBefore = RestAssured
                .given()
                .queryParam("token",token)
                .get(location)
                .jsonPath();
        String status = responseBefore.get("status").toString();
        if (status.equals("Job is NOT ready")) {
            System.out.println( "Статус до конца выполнения метода - верный: " + status);
        }
        else {
            System.out.println( "Статус ошибочный");
        }


        try {
            System.out.println("load...");
            Thread.sleep(sleepSeconds);
            System.out.println("...end");
            } catch(InterruptedException e) {
            }


        JsonPath responseAfter = RestAssured
                .given()
                .queryParam("token",token)
                .get(location)
                .jsonPath();
        status = responseAfter.get("status").toString();
        String result = responseAfter.get("result").toString();
        if (status.equals("Job is ready")) {
            System.out.println( "Статус после выполнения метода - верный: " + status + ". Ответ на главный вопрос жизни, вселенной и всего такого: " + result);
        } else  {
            System.out.println( "Статус ошибочный");
        }


    }

}
