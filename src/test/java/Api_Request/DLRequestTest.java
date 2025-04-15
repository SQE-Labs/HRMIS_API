package Api_Request;

import Api_Response.Root;
import Api_Specification.ApiConfig;
import Api_Specification.RestResource;
import Api_Specification.TokenManager;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static Api_Specification.TokenManager.token;

public class DLRequestTest {

    public static int extractedLeaveId; // Store leaveId for later use

    @Test()
    public void fetchPendingDLRequests() {
        // Get the token for DL role
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXNoYWwudGhha3VyQGNhZWxpdXNjb25zdWx0aW5nLmNvbSIsImlhdCI6MTc0MTg1MTY3NywiZXhwIjoxNzQxODczMjc3fQ.sReHeydZcMPc55PZGXkY2aLOChz_ceP_0iVt8U6GU5g";
        System.out.println("Generated Token: " + token);


        // Create POJO object for query parameters
        DLQueryParams queryParams = DLQueryParams.builder()
                .status("PENDING")
                .pageSize(10)
                .page(1)
                .build();

        // Convert POJO to a Map (required by RestAssured)
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("status", queryParams.getStatus());
        queryMap.put("pageSize", queryParams.getPageSize());
        queryMap.put("page", queryParams.getPage());

        // Send GET request with query parameters
        Response response = RestResource.get(ApiConfig.DL_APPROVER, token, queryMap);

        // Extract leaveId using JsonPath
        Root root = response.getBody().as(Root.class);

        // Extract leaveId from POJO
        if (root.data != null && !root.data.isEmpty()) {
            extractedLeaveId = root.data.get(0).laveId; // Directly accessing POJO field
            System.out.println("Extracted Leave ID: " + extractedLeaveId);
        } else {
            Assert.fail("No pending leave requests found in response.");
        }

        System.out.println("Extracted Leave ID: " + extractedLeaveId);

        // Validate Response
        Assert.assertEquals(response.statusCode(), 200, "Invalid Status Code");
    }

}