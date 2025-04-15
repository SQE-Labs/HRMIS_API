package Api_Response;

import Api_Request.*;
import Api_Specification.ApiConfig;
import Api_Specification.RestResource;
import Api_Specification.TokenManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Api_Request.DLRequestTest.extractedLeaveId;
import static Api_Specification.ApiConfig.*;

public class TestRun {

    @Test(priority = 1)
    public void validEmployeeLogin() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", EMPLOYEE_EMAIL);
        credentials.put("password", EMPLOYEE_PASSWORD);

        Response response = RestResource.post(ApiConfig.LOGIN, credentials);
        Assert.assertEquals(response.statusCode(), 200);
        String token = response.jsonPath().getString("accessToken");
        Assert.assertNotNull(token, "Token should be returned");
        System.out.println("Employee Token: " + token);
    }

    @Test(priority = 2)
    public void validDLLogin() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", DL_EMAIL);
        credentials.put("password", DL_PASSWORD);

        Response response = RestResource.post(ApiConfig.LOGIN, credentials);
        Assert.assertEquals(response.statusCode(), 200);
        String token = response.jsonPath().getString("accessToken");
        Assert.assertNotNull(token, "Token should be returned");
        System.out.println("DL Token: " + token);
    }

    @Test(priority = 3)
    public void validHRLogin() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", HR_EMAIL);
        credentials.put("password", HR_PASSWORD);

        Response response = RestResource.post(ApiConfig.LOGIN, credentials);
        Assert.assertEquals(response.statusCode(), 200);
        String token = response.jsonPath().getString("accessToken");
        Assert.assertNotNull(token, "Token should be returned");
        System.out.println("HR Token: " + token);
    }

    @Test(priority = 4)
    public void invalidLogin() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "wrong@example.com");
        credentials.put("password", "wrongPass");

        Response response = RestResource.post(ApiConfig.LOGIN, credentials);
        Assert.assertEquals(response.statusCode(), 401);
    }

    @Test(priority = 5)
    public void loginWithoutPassword() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", EMPLOYEE_EMAIL);

        Response response = RestResource.post(ApiConfig.LOGIN, credentials);
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Test(priority = 6)
    public void loginWithoutEmail() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("password", EMPLOYEE_PASSWORD);

        Response response = RestResource.post(ApiConfig.LOGIN, credentials);
        Assert.assertEquals(response.statusCode(), 400);
    }

    public Response applyForLeave(String startDate, String endDate, String reason, int approverId) {
        String token = TokenManager.getToken("employee");

        Apply_Leave leaveRequest = Apply_Leave.builder()
                .startDate(startDate)
                .endDate(endDate)
                .reasonOfLeave(reason)
                .approverId(approverId)
                .taskList(Arrays.asList(
                        Task.builder().taskName("task1").hours(3).build(),
                        Task.builder().taskName("task2").hours(4).build()
                ))
                .build();

        return RestResource.post(ApiConfig.LEAVE_APPLY, token, leaveRequest);
    }

    @Test(priority = 7)
    public void applyForLeaveSuccessfully() {
        String startDate = DatePicker.getIncrementedDate(1);
        String endDate = DatePicker.getIncrementedDate(2);

        Response response = applyForLeave(startDate, endDate, "Personal Work", 289);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 8)
    public void applyLeaveWithMissingFields() {
        String token = TokenManager.getToken("employee");

        Map<String, Object> leaveRequest = new HashMap<>();
        leaveRequest.put("startDate", DatePicker.getIncrementedDate(1));

        Response response = RestResource.post(ApiConfig.LEAVE_APPLY, token, leaveRequest);
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Test(priority = 9)
    public void applyLeaveWithInvalidDates() {
        String startDate = DatePicker.getIncrementedDate(5);
        String endDate = DatePicker.getIncrementedDate(3);

        Response response = applyForLeave(startDate, endDate, "Personal Work", 289);
        Assert.assertEquals(response.statusCode(), 400);
    }
    public static int extractedLeaveId; // Store leaveId for later use

    @Test(priority = 10)
    public void fetchPendingDLRequests() {
        String token = TokenManager.getToken("dl");

            DLQueryParams queryParams = DLQueryParams.builder()
                    .status("PENDING")
                    .pageSize(10)
                    .page(1)
                    .build();

            // Send GET request with query parameters
            Response response = RestResource.get(ApiConfig.DL_APPROVER, token, queryParams);

            // Log and validate response
            response.then().log().all();
            Assert.assertEquals(response.statusCode(), 200);


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



    @Test(priority = 11)
    public void fetchLeavesWithInvalidStatus() {
        String token = TokenManager.getToken("dl");

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("status", "INVALID_STATUS");

        Response response = RestResource.get(LeaveFetch, token, queryParams);
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Test(priority = 12, dependsOnMethods = "fetchPendingDLRequests")
    public void approveLeaveSuccessfully() {
        String token = TokenManager.getToken("dl");
        LeaveResponse leaveResponse = LeaveResponse.builder()
                .status("APPROVED")
                .data(List.of(
                        LeaveData.builder()
                                .laveId(169)
                                .build()
                ))
                .build();

        String requestBody = "{ comment: Leave approved successfully.}";

        Response response = RestResource.put(ApiConfig.LEAVE_APPROVER, token, requestBody, "dl", leaveResponse);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 13)
    public void rejectLeaveSuccessfully() {
        String token = TokenManager.getToken("dl");
        int leaveId = 169;

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("leaveId", leaveId);
        queryParams.put("status", "APPRPOVED");

        String requestBody = "{ \"comment\": \"Leave rejected.\" }";

        Response response = RestResource.put(ApiConfig.LEAVE_APPROVER, token, requestBody, "dl", queryParams);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 14)
    public void approveLeaveWithoutAuthorization() {
        LeaveResponse leaveResponse = LeaveResponse.builder()
                .status("APPROVED")
                .data(List.of(
                        LeaveData.builder()
                                .laveId(169)
                                .leaveFinalStatus("APPROVED")
                                .build()
                ))
                .build();



        String requestBody = "{ \"comment\": \"Leave approved successfully.\" }";

        Response response = RestResource.put(ApiConfig.LEAVE_APPROVER, "", requestBody, "dl", leaveResponse);
        Assert.assertEquals(response.statusCode(), 401);
    }

    @Test(priority = 15)
    public void approveLeaveWithInvalidLeaveId() {
        String token = TokenManager.getToken("dl");

        LeaveResponse leaveResponse = LeaveResponse.builder()
                .status("APPROVED")
                .data(List.of(
                        LeaveData.builder()
                                .laveId(160)
                                .leaveFinalStatus("APPROVED")
                                .build()
                ))
                .build();



        String requestBody = "{ \"comment\": \"Leave approved successfully.\" }";

        Response response = RestResource.put(ApiConfig.LEAVE_APPROVER, token, requestBody, "dl", leaveResponse);
        Assert.assertEquals(response.statusCode(), 404);
    }
    @Test(priority = 16)
    public void approveLeaveWithoutComment() {
        String token = TokenManager.getToken("dl");

        LeaveResponse leaveResponse = LeaveResponse.builder()
                .status("APPROVED")
                .data(List.of(
                        LeaveData.builder()
                                .laveId(extractedLeaveId)
                                .build()
                ))
                .build();

        // No comment in request body
        String requestBody = "{}";

        Response response = RestResource.put(ApiConfig.LEAVE_APPROVER, token, requestBody, "dl", leaveResponse);
        Assert.assertEquals(response.statusCode(), 400, "Expected 400 Bad Request due to missing comment");
    }

    @Test(priority = 17)
    public void fetchPendingLeavesForHR() {
        String token = TokenManager.getToken("hr");

        DLQueryParams queryParams = DLQueryParams.builder()
                .status("PENDING")
                .pageSize(10)
                .page(1)
                .build();

        Response response = RestResource.get(LeavesHr, token, queryParams);
        Assert.assertEquals(response.statusCode(), 200);
        response.then().log().all();
    }

    @Test(priority = 18)
    public void fetchLeavesWithInvalidPageSize() {
        String token = TokenManager.getToken("hr");

        DLQueryParams queryParams = DLQueryParams.builder()
                .status("PENDING")
                .pageSize(500)  // invalid page size
                .page(1)
                .build();

        Response response = RestResource.get(LeavesHr, token, queryParams);
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Test(priority = 19)
    public void fetchLeavesWithoutAuthorization() {
        DLQueryParams queryParams = DLQueryParams.builder()
                .status("PENDING")
                .pageSize(10)
                .page(1)
                .build();

        Response response = RestResource.get(ApiConfig.LeavesHr, "", queryParams);
        Assert.assertEquals(response.statusCode(), 401);
    }

}
