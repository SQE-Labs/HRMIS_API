package Api_Request;

import Api_Specification.RestResource;
import Api_Specification.TokenManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static Api_Specification.ApiConfig.LeaveFetch;

public class FetchLeaveTest {

    @Test
    public void fetchPendingLeavesForDL() {
        // Get token for DL
        String token = TokenManager.getToken("dl");

        // Create Query Parameters
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("status", "PENDING");
        queryParams.put("pageSize", 10);
        queryParams.put("page", 1);

        // Send GET request
        Response response = RestResource.get(LeaveFetch, token, queryParams);

        // Validate Response
        Assert.assertEquals(response.statusCode(), 200, "Invalid status code");

        // Print response for debugging
        System.out.println("Response Body: " + response.getBody().asString());
    }
}
