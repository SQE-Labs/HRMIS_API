package Api_Request;

import Api_Specification.ApiConfig;
import Api_Specification.RestResource;
import Api_Specification.TokenManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class LeaveApplyTest  {
    @Test
    public void applyForLeave() {
        // Get the token
        String token = TokenManager.getToken("employee");

        // Create Leave Request object
        Apply_Leave leaveRequest = Apply_Leave.builder()
                .endDate("2025-06-01")
                .reasonOfLeave("Personal Work")
                .startDate("2025-02-01")
                .approverId(271)
                .taskList(Arrays.asList(
                        Task.builder().taskName("task1").hours(3).build(),
                        Task.builder().taskName("task2").hours(4).build()
                ))
                .build();

        // Send API Request
        Response response = RestResource.post(ApiConfig.LEAVE_APPLY, token+"4444", leaveRequest);

        // Validate Response
        Assert.assertEquals(response.statusCode(), 200);
        System.out.println("Response Body: " + response.getBody().asString());
    }
}
