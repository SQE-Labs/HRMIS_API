package Api_Specification;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

import static Api_Specification.ApiConfig.*;


public class TokenManager {
     public static String token ;
    private static Map<String, String> tokens = new HashMap<>();

    public static String getToken(String role) {
        if (!tokens.containsKey(role)) {
            tokens.put(role, generateToken(role));
        }
        return tokens.get(role);
    }

    private static String generateToken(String role) {
        Map<String, String> credentials = new HashMap<>();

        switch (role.toLowerCase()) {
            case "dl":
                credentials.put("email", "vishal.thakur@caeliusconsulting.com");
                credentials.put("password", "Test@123");
                break;
            case "hr":
                credentials.put("email", "emp1@caeliusconsulting.com");
                credentials.put("password", "Test@123");
                break;
            case "employee":
                credentials.put("email", "asset-l3@caeliusconsulting.com");
                credentials.put("password", "Test@123");
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        Response response = RestAssured
                .given().baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post(LOGIN);

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch token for role: " + role);
        }
        token = response.jsonPath().getString("accessToken");

        return response.jsonPath().getString("accessToken");
    }
}
