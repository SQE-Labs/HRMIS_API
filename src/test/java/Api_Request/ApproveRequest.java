package Api_Request;

import Api_Specification.ApiConfig;
import Api_Specification.RestResource;
import Api_Specification.TokenManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static Api_Request.DLRequestTest.extractedLeaveId;
import static Api_Specification.TokenManager.token;

public class ApproveRequest {
    @Test
    public void updateLeaveStatus() {
        // Get the token for DL role
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXNoYWwudGhha3VyQGNhZWxpdXNjb25zdWx0aW5nLmNvbSIsImlhdCI6MTc0MTg1MTY3NywiZXhwIjoxNzQxODczMjc3fQ.sReHeydZcMPc55PZGXkY2aLOChz_ceP_0iVt8U6GU5g";


        // Define Path Parameter (Approver Type)
        String approverType = "dl";

        // Define Query Parameters using extracted leaveId
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("leaveId", extractedLeaveId); // Using extracted leave ID
        queryParams.put("Status", "APPROVED");

        // Define Request Body
        String requestBody = "{ \"comment\": \"Leave approved successfully.\" }";

        // Send PUT request with Path & Query Parameters
        Response response = RestResource.put(ApiConfig.LEAVE_APPROVER, token, requestBody, approverType, queryParams);
        System.out.println(token);
        // Validate Response Status Code
        Assert.assertEquals(response.statusCode(), 200, "Invalid Status Code");
        System.out.println("Updated Leave Status Response: " + response.getBody().asString());

    }
}


