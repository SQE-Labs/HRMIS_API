package Api_Specification;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestResource {
    String Token ;

    public static Response post(String path, String token, Object requestPayload) {
        return given()
                .spec(SpecBuilder.getRequestSpec(token))
                .body(requestPayload)
                .when()
                .post(path)
                .then()
                .log().all()
                .extract()
                .response();
    }
    public static Response post(String path, Object requestPayload) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .body(requestPayload)
                .when()
                .post(path)
                .then()
                .log().all()
                .extract()
                .response();
    }


    public static Response put(String path, String token, String requestBody, String approverType,  Object queryParams) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> queryMap = mapper.convertValue(queryParams, Map.class);

        return given()
                .spec(SpecBuilder.getRequestSpec(token))
                .pathParam("approverType", approverType)  // Adding path parameter dynamically
                .queryParams(queryMap)  // Adding query parameters dynamically
                .body(requestBody)
                .when()
                .put(path + "/{approverType}")  // Using dynamic path parameter
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response get(String path, String token) {
        return given()
                .spec(SpecBuilder.getRequestSpec(token))
                .when()
                .get(path)
                .then()
                .log().all()
                .extract()
                .response();
    }
    public static Response get(String path, String token, Object queryParams) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> queryMap = mapper.convertValue(queryParams, Map.class);

        return given()
                .spec(SpecBuilder.getRequestSpec(token))
                .queryParams(queryMap)
                .when()
                .get(path)
                .then()
                .log().all()
                .extract()
                .response();
    }


    public static Response delete(String path, String token) {
        return given()
                .spec(SpecBuilder.getRequestSpec(token))
                .when()
                .delete(path)
                .then()
                .log().all()
                .extract()
                .response();
    }

}
