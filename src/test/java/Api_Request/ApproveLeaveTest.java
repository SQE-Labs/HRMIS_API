//package Api_Request;
//
//import Api_Specification.RestResource;
//import Api_Specification.TokenManager;
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static Api_Specification.ApiConfig.LeaveFetch;
//
//public class ApproveLeaveTest {
//
//    @Test
//    public void approveLeave() {
//        // Get token for DL
//        String token = TokenManager.getToken("dl");
//
//        // Create Query Parameters
//        Map<String, Object> queryParams = new HashMap<>();
//        queryParams.put("leaveId", 3);
//        queryParams.put("Status", "APPROVED");
//
//        // Create Request Body
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("comment", "Approved by DL");
//
//        // Send PUT request
//        Response response = RestResource.put(LeaveFetch, token,queryParams);
//
//        // Validate Response
//        Assert.assertEquals(response.statusCode(), 200, "Invalid status code");
//
//        // Print response for debugging
//        System.out.println("Response Body: " + response.getBody().asString());
//    }
//}
